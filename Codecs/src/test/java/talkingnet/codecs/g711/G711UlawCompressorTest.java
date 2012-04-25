package talkingnet.codecs.g711;

import java.util.Arrays;
import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class G711UlawCompressorTest {
    
    private G711UlawCompressor compressor = new G711UlawCompressor();
    
    @Test
    public void testEncode() {
        byte[] src = {1, -2, 4, -8, 16, 32};
        byte[] dst = compressor.compress(src);
        System.out.println(Arrays.toString(dst));
    }
}
