package talkingnet.codec.g711;

import java.util.Arrays;
import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class G711UlawEncoderTest {
    
    private G711UlawEncoder encoder = new G711UlawEncoder();
    
    @Test
    public void testEncode() {
        byte[] src = {1, -2, 4, -8, 16, 32};
        byte[] dst = encoder.encode(src);
        System.out.println(Arrays.toString(dst));
    }
}
