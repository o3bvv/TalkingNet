package talkingnet.utils;

import org.junit.Test;
import talkingnet.core.foo.sink.FooSink;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class OnesDataTest {
    
    @Test
    public void testOnesData() {
        FooSink sink = new FooSink("sink");
        byte[] data = OnesData.getOnesData(15);
        sink.push_in(data, data.length);
    }
}
