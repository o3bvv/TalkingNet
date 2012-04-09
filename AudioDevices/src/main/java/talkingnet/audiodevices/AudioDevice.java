package talkingnet.audiodevices;

import java.io.InputStream;
import javax.sound.sampled.*;
import talkingnet.core.Element;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public abstract class AudioDevice extends Element implements LineListener {

    protected ProcessingThread thread;
    protected int bufferLength;
    protected AudioFormat format;
    protected DataLine line;
    protected InputStream lineStream;
    protected Mixer mixer;
    protected boolean muted = false;
    protected boolean running = false;

    public AudioDevice(
            Mixer mixer, AudioFormat format, int bufferLength, String title) {
        super(title);
        this.mixer = mixer;
        this.format = format;
        this.bufferLength = bufferLength;
    }

    public void open() throws Exception {
        close();
        destroyLine();
        createLine();
        openLine();
    }
    
    private void openLine() throws Exception {
        try {
            bufferLength -= bufferLength % format.getFrameSize();
            doOpenLine();
            System.out.println(title + ": opened line");
            System.out.println(title + ": data buffer length:" + bufferLength + " bytes, "+
                               "internal buffer length:" + line.getBufferSize() + " bytes.");
        } catch (LineUnavailableException ex) {
            running = false;
            throw new Exception("Unable to open " + title + ": " + ex.getMessage());
        }
    }
    
    protected abstract void doOpenLine() throws Exception;
    
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
            line.flush();
            line.stop();
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
    
    protected abstract void doCreateLine() throws Exception;
    
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
    
    protected abstract void startThread();

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

    protected class ProcessingThread extends Thread {

        protected volatile boolean doTerminate = false;
        protected volatile boolean terminated = false;

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
    
    public AudioFormat getFormat() {
        return format;
    }

    public boolean isOpen() {
        return (line != null) && (line.isOpen());
    }

    public boolean isStarted() {
        return (line != null) && running;
    }
    
    public void setMuted(boolean muted) {
        this.muted = muted;
        if (thread!=null){
            thread.notifyAll();
        }
    }
}
