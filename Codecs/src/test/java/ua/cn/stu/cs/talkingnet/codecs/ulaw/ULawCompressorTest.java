package ua.cn.stu.cs.talkingnet.codecs.ulaw;

import org.junit.Test;
import ua.cn.stu.cs.talkingnet.core.foo.sink.FooSink;
import ua.cn.stu.cs.talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class ULawCompressorTest {
        
    private ULawCompressor compresssor;
    private FooSink sink;
    
    {
        sink = new FooSink("sink");
        compresssor = new ULawCompressor(sink, "compressor");
    }
    
    @Test
    public void testULawCompresssor() {
        byte[] src = RandomData.getRandomShortDataFixedLength(50);
        sink.push_in(src, src.length);
        compresssor.push_in(src, src.length);
    }
}
