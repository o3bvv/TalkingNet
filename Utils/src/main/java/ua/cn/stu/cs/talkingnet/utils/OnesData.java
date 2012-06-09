package ua.cn.stu.cs.talkingnet.utils;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class OnesData {

    private static final byte ONE = 1;
    
    public static byte[] getOnesData(int length){
        byte[] data = new byte[length];
        fillDataWithOnes(data);
        return data;
    }
    
    public static void fillDataWithOnes(byte[] data){
        for (int i = 0; i < data.length; i++) {
            data[i] = ONE;
        }
    }
}
