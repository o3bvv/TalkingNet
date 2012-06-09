package ua.cn.stu.cs.talkingnet.core.foo.source;

import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushing;
import ua.cn.stu.cs.talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooSource extends Element implements Pushing{

    private Pushable sink;
    private Thread thread;
    protected boolean runThread = false;
    
    public FooSource(Pushable sink, String title) {
        super(title);
        this.sink = sink;
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
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
