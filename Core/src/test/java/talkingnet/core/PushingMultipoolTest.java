package talkingnet.core;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import talkingnet.core.adder.SimpleAdder;
import talkingnet.core.foo.sink.FooSink;
import talkingnet.core.foo.source.FooSource;
import talkingnet.core.io.Pushable;
import talkingnet.core.io.channel.PushChannel;
import talkingnet.utils.OnesData;
import talkingnet.utils.random.RandomNumbers;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class PushingMultipoolTest {

    private int bufferSize = 30;
    
    private PushingMultipool multipool;
    private SimpleAdder adder;
    private FooSink sink;
    private PushChannel adderToSink;
    
    private List<RandomTimeFixedSizeOnesGenerator> sources;

    {
        sink = new FooSink("sink");
        adderToSink = new PushChannel(sink);
        adder = new SimpleAdder(bufferSize, adderToSink, "adder");
        multipool = new PushingMultipool(bufferSize, adder, "multipool");
        
        sources = new LinkedList<RandomTimeFixedSizeOnesGenerator>();
    }
    
    @Test
    public void testPushingMultipool() throws InterruptedException {
        
        multipool.start();
        
        for (int i = 0; i < 50; i++) {
            Pushable poolSink = multipool.getNewSink();
            PushChannel sourceToPool = new PushChannel(poolSink);
            RandomTimeFixedSizeOnesGenerator source = 
                    new RandomTimeFixedSizeOnesGenerator(sourceToPool, "source"+(i+1));
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

        public RandomTimeFixedSizeOnesGenerator(PushChannel channel_out, String title) {
            super(channel_out, title);
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
