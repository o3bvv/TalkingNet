package talkingnet.utils.random;

import java.util.Arrays;
import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class RandomDataTest {
    
    @Test
    public void testGetRandomShortDataFixedLength() {
        byte[] data = RandomData.getRandomShortDataFixedLength(10);
        System.out.println(Arrays.toString(data));
    }
}
