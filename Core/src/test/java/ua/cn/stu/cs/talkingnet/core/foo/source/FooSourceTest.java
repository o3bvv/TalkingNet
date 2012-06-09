package ua.cn.stu.cs.talkingnet.core.foo.source;

import org.junit.Test;
import ua.cn.stu.cs.talkingnet.core.foo.sink.FooSink;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooSourceTest {
    
    private FooSource source;
    private FooSink sink;
    
    {
        sink = new FooSink("sink");
        source = new FooSource(sink, "source");
    }
    
    @Test
    public void testFooSource() throws InterruptedException{
        source.start();
        Thread.sleep(7);
        source.stop();
    }    
}
