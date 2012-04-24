package talkingnet.utils.random;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class RandomData {
    public static byte[] getRandomData(int lengthEdge){
        byte[] data = new byte[RandomNumbers.getRandom(lengthEdge)];
        for (int i = 0; i < data.length; i++) {
            data[i] = RandomNumbers.getRandomPositiveByte();
        }
        return data;
    }
}
