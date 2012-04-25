package talkingnet.core.foo.source;

import java.io.IOException;
import talkingnet.core.Element;
import talkingnet.core.io.Pushing;
import talkingnet.core.io.channel.PushChannel;
import talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooSource extends Element implements Pushing{

    private PushChannel channel_out;
    private Thread thread;
    private boolean runThread = false;
    
    public FooSource(PushChannel channel_out, String title) {
        super(title);
        this.channel_out = channel_out;
    }

    public void push_out(byte[] data, int size) {
        try {
            channel_out.write(data, data.length);
        } catch (IOException ex) {
            System.out.println(title+": "+ex);
        }
    }
    
    public void start() {
        if (thread != null) {
            return;
        }

        thread = getNewThread();
        thread.start();
    }
    
    protected Thread getNewThread(){
        return new GeneratingThread();
    }

    public void stop() {
        runThread = false;
        thread = null;
    }
    
    private class GeneratingThread extends Thread {
        @Override
        public void run() {
            runThread = true;
            while (runThread) {                
                byte[] data = RandomData.getRandomData(20);
                push_out(data, data.length);
            }
        }
    }
}
