package talkingnet.audiodevices;

import javax.sound.sampled.*;
import talkingnet.core.io.Pullable;
import talkingnet.core.io.Pulling;
import talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioSink extends AudioDevice implements Pulling{
    
    protected Pullable source;
    
    public AudioSink(
            Mixer mixer, AudioFormat format, int bufferLength,
            Pullable source, String title) {
        super(mixer, format, bufferLength,title);
        this.source = source;
        internalBufferScale = 1;
    }
    
    @Override
    protected void startThread() {
        if (thread != null && (thread.isTerminating() || source == null)) {
            thread.terminate();
            thread = null;
        }

        if ((thread == null) && (source != null)) {
            thread = new PushingThread();
            thread.start();
        }
    }
    
    @Override
    protected void doCreateLine() throws Exception {
        System.out.println(title + ": creating SourceDataLine");
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        if (mixer != null) {
            line = (SourceDataLine) mixer.getLine(info);
        } else {
            line = AudioSystem.getSourceDataLine(format);
        }        
    }
    
    @Override
    protected void doOpenLine() throws Exception {
        System.out.println(title + ": opening TargetDataLine and creating TargetDataLineAIS");
        SourceDataLine sdl = (SourceDataLine) line;
        sdl.open(format, bufferLength*internalBufferScale);
    }
    
    @Override
    public int pull_in(byte[] data, int size) {
        return source.pull_out(data, size);
    }
    
    private class PushingThread extends ProcessingThread implements Pushing {

        @Override
        public void run(){
            byte[] buffer = new byte[bufferLength];
            int read;
            while (!doTerminate) {
                read = pull_in(buffer, buffer.length);
                
                if (read > 0) {
                    synchronized (AudioSink.this) {
                        push_out(buffer, buffer.length);
                    }
                } else {
                    try {
                        synchronized (this) {
                            this.wait(40);
                        }
                    } catch (InterruptedException ex) {
                        System.out.println(ex);
                    }
                }
            }
            terminated = true;
        }
        
        @Override
        public void push_out(byte[] data, int size) {
            ((SourceDataLine) line).write(data, 0, size);
        }        
    }        
}
