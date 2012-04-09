package talkingnet.audiodevices;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;
import talkingnet.core.io.Pulling;
import talkingnet.core.io.Pushing;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioSource extends AudioDevice implements Pushing {

    protected PushChannel channel_out;

    public AudioSource(
            Mixer mixer, AudioFormat format, int bufferLength,
            PushChannel channel_out, String title) {
        super(mixer, format, bufferLength, title);
        this.channel_out = channel_out;
    }

    @Override
    public void push_out(byte[] data, int size) {
        try {
            channel_out.write(data, size);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    protected void startThread() {
        if (thread != null && (thread.isTerminating() || channel_out == null)) {
            thread.terminate();
            thread = null;
        }

        if ((thread == null) && (channel_out != null)) {
            thread = new PullingThread();
            thread.start();
        }
    }
    
    @Override
    protected void doCreateLine() throws Exception {
        System.out.println(title+": creating TargetDataLine");
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (mixer != null) {
            line = (TargetDataLine) mixer.getLine(info);
        } else {
            line = AudioSystem.getTargetDataLine(format);
        }
    }

    @Override
    protected void doOpenLine() throws Exception {
        System.out.println(title+": opening TargetDataLine and creating TargetDataLineAIS");
        TargetDataLine tdl = (TargetDataLine) line;
        tdl.open(format, bufferLength*5);
        lineStream = new PullingStream(tdl);
    }

    private class PullingThread extends ProcessingThread implements Pulling {
        
        @Override
        public void run() {
            int read;
            while (!doTerminate) {
                byte[] buffer = new byte[bufferLength];
                read = pull_in(buffer, buffer.length);
                
                if (read > 0) {
                    synchronized (AudioSource.this) {
                        push_out(buffer, buffer.length);
                    }
                } else {
                    try {
                        synchronized (this) {
                            this.wait(20);
                        }
                    } catch (InterruptedException ex) {
                        System.out.println(ex);
                    }
                }
            }
            terminated = true;
        }

        @Override
        public int pull_in(byte[] data, int size) {
            try {
                return tryRead(data, size);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            return -1;
        }

        private int tryRead(byte[] data, int size) throws IOException {
            return lineStream.read(data, 0, size);            			
        }
    }
    
    private class PullingStream extends InputStream {

        private TargetDataLine line;

        PullingStream(TargetDataLine line) {
            this.line = line;
        }

        @Override
        public int available() throws IOException {
            return line.available();
        }

        @Override
        public int read() throws IOException {
            throw new IOException("Illegal call to PullingStream.read()!");
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (line == null) {
                return -1;
            }
            try {
                return line.read(b, off, len);
            } catch (IllegalArgumentException e) {
                throw new IOException(e.getMessage());
            }
        }

        @Override
        public void close() throws IOException {
            if (line.isActive()) {
                line.flush();
                line.stop();
            }
            line.close();
        }

        @Override
        public int read(byte[] b) throws IOException {
            return read(b, 0, b.length);
        }

        @Override
        public long skip(long n) throws IOException {
            return 0;
        }

        @Override
        public void mark(int readlimit) {
        }

        @Override
        public void reset() throws IOException {
        }

        @Override
        public boolean markSupported() {
            return false;
        }
    }
}
