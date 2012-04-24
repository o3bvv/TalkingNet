package talkingnet.utils.random;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class RandomNumbers {

    public static int getRandom(int edge) {
        return (int) Math.ceil(Math.random() * edge);
    }

    public static byte getRandomPositiveByte() {
        return (byte) Math.ceil(Math.random() * Byte.MAX_VALUE);
    }
}
