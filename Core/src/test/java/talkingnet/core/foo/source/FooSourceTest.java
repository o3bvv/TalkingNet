package talkingnet.core.foo.source;

import org.junit.Test;
import talkingnet.core.foo.sink.FooSink;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooSourceTest {
    
    private FooSource source;
    private FooSink sink;
    private PushChannel channel;
    
    {
        sink = new FooSink("sink");
        channel = new PushChannel(sink);
        source = new FooSource(channel, "source");
    }
    
    @Test
    public void testFooSource() throws InterruptedException{
        source.start();
        Thread.sleep(7);
        source.stop();
    }    
}
