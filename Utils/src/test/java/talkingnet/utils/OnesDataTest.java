package talkingnet.utils;

import java.util.Arrays;
import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class OnesDataTest {
    
    @Test
    public void testOnesData() {        
        byte[] data = OnesData.getOnesData(15);
        System.out.println("Ones data: "+Arrays.toString(data));
    }
}
