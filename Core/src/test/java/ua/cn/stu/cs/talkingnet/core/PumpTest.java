package ua.cn.stu.cs.talkingnet.core;

import org.junit.Test;
import ua.cn.stu.cs.talkingnet.core.foo.sink.FooSink;
import ua.cn.stu.cs.talkingnet.core.foo.source.FooSource;
import ua.cn.stu.cs.talkingnet.utils.random.RandomNumbers;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class PumpTest {

    private Pump pump;
    private FooSource source;
    private RandomProcessingTimeSink sink;

    {
        sink = new RandomProcessingTimeSink("sink");
        pump = new Pump(100, sink, "pump");
        source = new FooSource(pump, "src");
    }
    
    @Test
    public void testSomeMethod() throws InterruptedException {
        pump.start();
        source.start();
        Thread.sleep(6);
        source.stop();
        pump.stop();
        while(pump.isStopped()==false){}
    }

    private class RandomProcessingTimeSink extends FooSink {

        public RandomProcessingTimeSink(String title) {
            super(title);
        }

        @Override
        public void push_in(byte[] data, int length) {
            super.push_in(data, length);
            try {
                Thread.sleep(RandomNumbers.getRandom(30));
            } catch (InterruptedException ex) {
                System.out.println(title + ": " + ex);
            }
        }
    }
}
