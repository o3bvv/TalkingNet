package talkingnet.codecs.ulaw;

import org.junit.Test;
import talkingnet.core.foo.sink.FooSink;
import talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class ULawCompresssorTest {
        
    private ULawCompresssor compresssor;
    private FooSink sink;
    
    {
        sink = new FooSink("sink");
        compresssor = new ULawCompresssor(sink, "compressor");
    }
    
    @Test
    public void testULawCompresssor() {
        byte[] src = RandomData.getRandomShortDataFixedLength(50);
        sink.push_in(src, src.length);
        compresssor.push_in(src, src.length);
    }
}
