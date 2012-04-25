package talkingnet.codecs.g711;

import org.junit.Test;
import talkingnet.core.foo.sink.FooSink;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class G711UlawCompressorTest {
    
    private FooSink sink;
    private G711UlawCompressor compressor;
    
    {
        sink = new FooSink("sink");
        compressor = new G711UlawCompressor(sink, "compressor");
    }
    
    @Test
    public void testEncode() {
        byte[] src = {1, -2, 4, -8, 16, 32};
        compressor.compress(src);
    }
}
