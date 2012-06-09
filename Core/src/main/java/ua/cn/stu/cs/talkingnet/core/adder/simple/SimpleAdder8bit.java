package ua.cn.stu.cs.talkingnet.core.adder.simple;

import java.util.Collection;
import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.core.io.Multipushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class SimpleAdder8bit extends Element implements Multipushable, Pushing {

    private Pushable sink;
    byte[] buffer;
    private int bufferSize;

    public SimpleAdder8bit(int bufferSize, Pushable sink, String title) {
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
