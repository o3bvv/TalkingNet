package talkingnet.codecs.g711;

import org.junit.Test;
import talkingnet.core.foo.sink.FooSink;
import talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class G711UlawDecompressorTest {
    
    private G711UlawDecompressor decompressor;
    private G711UlawCompressor compressor;
    private FooSink sink;

    {
        sink = new FooSink("sink");
        decompressor = new G711UlawDecompressor(sink, "decompressor");
        compressor = new G711UlawCompressor(decompressor, "compressor");
    }
    
    @Test
    public void testG711UlawDecompressor() {
        for (int i = 1; i < 4; i++) {
            System.out.printf("Test #%d:\n", i);
            testCodec();
        }
    }
    
    private void testCodec(){
        byte[] src = RandomData.getRandomDataEvenLength(40);        
        sink.push_in(src, src.length);        
        compressor.push_in(src, src.length);
    }
}
