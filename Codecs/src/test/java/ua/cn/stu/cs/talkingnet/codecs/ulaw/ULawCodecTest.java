package ua.cn.stu.cs.talkingnet.codecs.ulaw;

import org.junit.Test;
import ua.cn.stu.cs.talkingnet.core.foo.sink.FooSink;
import ua.cn.stu.cs.talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class ULawCodecTest {

    private ULawCompressor compresssor;
    private ULawDecompressor decompresssor;
    private FooSink sink;
    
    {
        sink = new FooSink("sink");
        decompresssor = new ULawDecompressor(sink, "decompressor");
        compresssor = new ULawCompressor(decompresssor, "compressor");
    }

    @Test
    public void testULawCodec() {
        byte[] src = RandomData.getRandomShortDataFixedLength(50);
        sink.push_in(src, src.length);
        compresssor.push_in(src, src.length);
    }
}
