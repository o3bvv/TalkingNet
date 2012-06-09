package ua.cn.stu.cs.talkingnet.utils.random;

import ua.cn.stu.cs.talkingnet.utils.random.RandomData;
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
