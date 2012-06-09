package ua.cn.stu.cs.talkingnet.core;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import ua.cn.stu.cs.talkingnet.core.adder.simple.SimpleAdder8bit;
import ua.cn.stu.cs.talkingnet.core.foo.sink.FooSink;
import ua.cn.stu.cs.talkingnet.core.foo.source.FooSource;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.utils.OnesData;
import ua.cn.stu.cs.talkingnet.utils.random.RandomNumbers;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class PushingMultipoolTest {

    private int bufferSize = 30;
    
    private PushingMultipool multipool;
    private SimpleAdder8bit adder;
    private FooSink sink;
    
    private List<RandomTimeFixedSizeOnesGenerator> sources;

    {
        sink = new FooSink("sink");
        adder = new SimpleAdder8bit(bufferSize, sink, "adder");
        multipool = new PushingMultipool(bufferSize, adder, "multipool");
        
        sources = new LinkedList<RandomTimeFixedSizeOnesGenerator>();
    }
    
    @Test
    public void testPushingMultipool() throws InterruptedException {
        
        multipool.start();
        
        for (int i = 0; i < 50; i++) {
            Pushable poolSink = multipool.getNewSink();
            RandomTimeFixedSizeOnesGenerator source = 
                    new RandomTimeFixedSizeOnesGenerator(poolSink, "source"+(i+1));
            sources.add(source);
            source.start();
        }
        
        Thread.sleep(50);
        
        for (RandomTimeFixedSizeOnesGenerator source : sources) {
            source.stop();
        }
        
        multipool.stop();
    }

    private class RandomTimeFixedSizeOnesGenerator extends FooSource {

        public RandomTimeFixedSizeOnesGenerator(Pushable sink, String title) {
            super(sink, title);
        }

        @Override
        protected Thread getNewThread() {
            return new GeneratingThread();
        }

        private class GeneratingThread extends Thread {

            @Override
            public void run() {
                runThread = true;
                while (runThread) {
                    byte[] data = OnesData.getOnesData(bufferSize);
                    push_out(data, data.length);
                    
                    try {
                        Thread.sleep(RandomNumbers.getRandom(10));
                    } catch (InterruptedException ex) {
                        System.out.println(title+": "+ex);
                    }
                }
            }
        }
    }
}
