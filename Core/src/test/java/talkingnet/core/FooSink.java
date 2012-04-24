package talkingnet.core;

import java.util.Arrays;
import talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooSink extends Element implements Pushable{

    public FooSink(String title) {
        super(title);
    }

    public void push_in(byte[] data, int length) {
        System.out.printf("%s: Taking up data. Size: %d.\n", title, length);
        System.out.printf("%s: Data: ", title);
        System.out.println(Arrays.toString(data));
    }
}
