package talkingnet.core.adder;

import java.util.Collection;
import talkingnet.core.Element;
import talkingnet.core.io.Multipushable;
import talkingnet.core.io.Pushable;
import talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class SimpleAdder extends Element implements Multipushable, Pushing {

    private Pushable sink;
    byte[] buffer;
    private int bufferSize;

    public SimpleAdder(int bufferSize, Pushable sink, String title) {
        super(title);
        this.bufferSize = bufferSize;
        this.sink = sink;
    }

    public void multipush_in(Collection<byte[]> data) {
        buffer = new byte[bufferSize];
        multiadd(data);
        push_out(buffer, buffer.length);
    }

    private void multiadd(Collection<byte[]> dataList) {
        for (byte[] data : dataList) {
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] += data[i];
            }
        }
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }
}
