package ua.cn.stu.cs.talkingnet.utils.random;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class RandomNumbers {

    public static int getRandom(int edge) {
        return (int) Math.floor(Math.random() * edge);
    }

    public static byte getRandomPositiveByte() {
        return (byte) Math.floor(Math.random() * Byte.MAX_VALUE);
    }
    
    public static short getRandomPositiveShort() {
        return (short) Math.floor(Math.random() * Short.MAX_VALUE);
    }
}
