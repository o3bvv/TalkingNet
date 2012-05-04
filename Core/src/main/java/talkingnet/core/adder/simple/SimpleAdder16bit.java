package talkingnet.core.adder.simple;

import java.util.Collection;
import talkingnet.core.Element;
import talkingnet.core.io.Multipushable;
import talkingnet.core.io.Pushable;
import talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class SimpleAdder16bit extends Element implements Multipushable, Pushing {

    private Pushable sink;
    byte[] buffer;
    private int bufferSize;

    public SimpleAdder16bit(int bufferSize, Pushable sink, String title) {
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
        short x1, x2, y;
        for (byte[] data : dataList) {
            for (int i = 0; i < buffer.length; i+=2) {
                x1 = (short) ((data[i]   << Byte.SIZE) + data[i+1]);
                x2 = (short) ((buffer[i] << Byte.SIZE) + buffer[i+1]);
                
                y  = (short) (x1 + x2);
                
                buffer[i]   = (byte) (y >> Byte.SIZE);
                buffer[i+1] = (byte) (y & 0xFF);
            }
        }
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }
}
