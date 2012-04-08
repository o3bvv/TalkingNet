package talkingnet.audiodevices;

import java.io.IOException;
import javax.sound.sampled.*;
import talkingnet.core.Element;
import talkingnet.core.io.Pulling;
import talkingnet.core.io.Pushing;
import talkingnet.core.io.channel.PullChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioSink extends Element implements Pulling, LineListener {

    private PushingThread thread;
    protected Mixer mixer;
    protected AudioFormat format;
    protected int bufferLength;
    protected DataLine line;
    protected PullChannel channel_in;
    protected boolean running = false;
    protected boolean muted = false;
    
    public AudioSink(
            Mixer mixer, AudioFormat format, int bufferLength,
            PullChannel channel_in, String title) {
        super(title);
        this.mixer = mixer;
        this.format = format;
        this.bufferLength = bufferLength;
        this.channel_in = channel_in;
    }
    
    public void start() throws Exception {
        startThread();
        
        if (line == null) {
            throw new Exception(title + ": cannot start");
        }

        line.flush();
        line.start();
        running = true;
        
        if (thread != null) {
            synchronized (thread) {
                thread.notifyAll();
            }
        }
    }
    
    private void startThread() {
        if (thread != null && (thread.isTerminating() || channel_in == null)) {
            thread.terminate();
            thread = null;
        }

        if ((thread == null) && (channel_in != null)) {
            thread = new PushingThread();
            thread.start();
        }
    }
    
    public void open() throws Exception {
        close();
        destroyLine();
        createLine();
        openLine();
    }
    
    public void close() {
        closeLine();
        destroyLine();
    }
    
    private void closeLine() {
        if (thread != null){
            thread.terminate();
            thread.waitFor();
        }
        if (line != null) {
            line.drain();
            line.close();
            running = false;
            System.out.println(title + ": line closed.");
        }
    }
    
    private void destroyLine() {
        if (line != null) {
            line.removeLineListener(this);
        }
        line = null;
    }
    
    private void createLine() throws Exception {
        try {
            line = null;
            doCreateLine();
            line.addLineListener(this);
            System.out.println("Got line for " + title + ": " + line.getClass());
        } catch (LineUnavailableException ex) {
            throw new Exception("Unable to open " + title + ": " + ex.getMessage());
        }
    }
    
    protected void doCreateLine() throws Exception {
        System.out.println(title + ": creating SourceDataLine");
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        if (mixer != null) {
            line = (SourceDataLine) mixer.getLine(info);
        } else {
            line = AudioSystem.getSourceDataLine(format);
        }        
    }
    
    private void openLine() throws Exception {
        try {
            bufferLength -= bufferLength % format.getFrameSize();
            doOpenLine();
            System.out.println(title + ": opened line");
            bufferLength = line.getBufferSize();
            System.out.println(title + ": buffer length:" + bufferLength + " bytes.");
        } catch (LineUnavailableException ex) {
            running = false;
            throw new Exception("Unable to open " + title + ": " + ex.getMessage());
        }
    }
    
    protected void doOpenLine() throws Exception {
        System.out.println(title + ": opening TargetDataLine and creating TargetDataLineAIS");
        SourceDataLine sdl = (SourceDataLine) line;
        sdl.open(format, bufferLength);
    }
    
    @Override
    public void pull_in(byte[] data, int size) {
        try {
            channel_in.read(data, size);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType().equals(LineEvent.Type.STOP)) {
            System.out.println(title + ": STOP event");
        } else if (event.getType().equals(LineEvent.Type.START)) {
            System.out.println(title + ": START event");
        } else if (event.getType().equals(LineEvent.Type.OPEN)) {
            System.out.println(title + ": OPEN event");
        } else if (event.getType().equals(LineEvent.Type.CLOSE)) {
            System.out.println(title + ": CLOSE event");
        }
    }
    
    private class PushingThread extends Thread implements Pushing {

        private volatile boolean doTerminate = false;
        private volatile boolean terminated = false;
        
        @Override
        public void run(){
            byte[] buffer = new byte[bufferLength];
            while (!doTerminate) {
                pull_in(buffer, buffer.length);
                push_out(buffer, buffer.length);
            }
            terminated = true;
        }
        
        @Override
        public void push_out(byte[] data, int size) {
            ((SourceDataLine) line).write(data, 0, size);
        }
        
        public synchronized void terminate() {
            doTerminate = true;
            this.notifyAll();
        }

        public synchronized boolean isTerminating() {
            return doTerminate && (terminated == false);
        }

        public synchronized void waitFor() {
            if (terminated == false) {
                try {
                    this.join();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }
    
    public void setMuted(boolean muted) {
        this.muted = muted;
        thread.notifyAll();
    }
    
    public boolean isStarted() {
        return (line != null) && running;
    }

    public boolean isOpen() {
        return (line != null) && (line.isOpen());
    }

    public AudioFormat getFormat() {
        return format;
    }
}
