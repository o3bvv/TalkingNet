package ua.cn.stu.cs.talkingnet.utils;

import ua.cn.stu.cs.talkingnet.utils.OnesData;
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
