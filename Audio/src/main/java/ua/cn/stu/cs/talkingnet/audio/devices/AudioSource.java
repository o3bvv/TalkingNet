package ua.cn.stu.cs.talkingnet.audio.devices;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;
import ua.cn.stu.cs.talkingnet.core.io.Pulling;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioSource extends AudioDevice implements Pushing {

    protected Pushable sink;

    public AudioSource(
            Mixer mixer, AudioFormat format, int bufferLength,
            Pushable sink, String title) {
        super(mixer, format, bufferLength, title);
        this.sink = sink;
    }

    @Override
    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }

    @Override
    protected void startThread() {
        if (thread != null && (thread.isTerminating() || sink == null)) {
            thread.terminate();
            thread = null;
        }

        if ((thread == null) && (sink != null)) {
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
        tdl.open(format, bufferLength*internalBufferScale);
        lineStream = new PullingStream(tdl);
    }

    private class PullingThread extends ProcessingThread implements Pulling {
        
        @Override
        public void run() {
            int read;
            int offset = 0;
            while (!doTerminate) {
                byte[] buffer = new byte[bufferLength];
                offset = 0;
                
                while(offset<bufferLength){
                    
                    read = pull_in(buffer, offset, buffer.length-offset);
                    offset += read;
                }
                
                synchronized (AudioSource.this) {
                    push_out(buffer, buffer.length);
                }
            }
            terminated = true;
        }

        @Override
        public int pull_in(byte[] data, int size) {
            return pull_in(data, 0, size);
        }

        @Override
        public int pull_in(byte[] data, int offset, int size) {
            try {
                return tryRead(data, offset, size);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            return -1;
        }
        
        private int tryRead(byte[] data, int offset, int size) throws IOException {
            return lineStream.read(data, offset, size);            			
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
