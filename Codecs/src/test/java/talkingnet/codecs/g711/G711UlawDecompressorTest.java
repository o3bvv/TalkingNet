package talkingnet.codecs.g711;

import org.junit.Test;
import talkingnet.core.foo.sink.FooSink;
import talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class G711UlawDecompressorTest {
    
    private G711UlawDecompressor decompressor = new G711UlawDecompressor();
    private G711UlawCompressor compressor = new G711UlawCompressor();
    private FooSink sink = new FooSink("sink");

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
        byte[] compressed = compressor.compress(src);
        sink.push_in(compressed, compressed.length);
        byte[] restored = decompressor.decompress(compressed);
        sink.push_in(restored, restored.length);
    }
}
