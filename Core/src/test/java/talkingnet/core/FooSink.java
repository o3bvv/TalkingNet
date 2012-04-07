package talkingnet.core;

import java.util.Arrays;
import talkingnet.core.io.Sink;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooSink implements Sink{

    public void takeUp(byte[] data, int length) {
        System.out.printf("Taking up data. Size: %d.\n", length);
        System.out.printf("Data: ");
        System.out.println(Arrays.toString(data));
    }
}
