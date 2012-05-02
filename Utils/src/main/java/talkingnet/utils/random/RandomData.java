package talkingnet.utils.random;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class RandomData {

    public static byte[] getRandomData(int lengthEdge) {
        int length = RandomNumbers.getRandom(lengthEdge);
        return getRandomDataFixedLength(length);
    }

    public static byte[] getRandomDataEvenLength(int lengthEdge) {
        int length = RandomNumbers.getRandom(lengthEdge);
        length -= length % 2;
        return getRandomDataFixedLength(length);
    }
    
    public static byte[] getRandomDataFixedLength(int length) {
        byte[] data = new byte[length];
        for (int i = 0; i < data.length; i++) {
            data[i] = RandomNumbers.getRandomPositiveByte();
        }
        return data;
    }
    
    public static byte[] getRandomShortDataFixedLength(int shortCount) {
        byte[] result = new byte[shortCount << 1];
        int resPos = 0;
        for (int i = 0; i < shortCount; i++) {
            short sample = RandomNumbers.getRandomPositiveShort();
            result[resPos++] = (byte) (sample >> Byte.SIZE);
            result[resPos++] = (byte) (sample & 0xFF);
        }
        return result;
    }
}
