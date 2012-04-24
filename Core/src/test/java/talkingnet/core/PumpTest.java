package talkingnet.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import talkingnet.core.foo.sink.FooSink;
import talkingnet.core.foo.source.FooSource;
import talkingnet.core.io.channel.PushChannel;
import talkingnet.utils.random.RandomNumbers;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class PumpTest {

    private Pump pump;
    private FooSource source;
    private RandomProcessingTimeSink sink;
    private PushChannel srcToPump;
    private PushChannel pumpToSink;

    {
        sink = new RandomProcessingTimeSink("sink");
        pumpToSink = new PushChannel(sink);
        pump = new Pump(pumpToSink, "pump");
        srcToPump = new PushChannel(pump);
        source = new FooSource(srcToPump, "src");
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
