package ua.cn.stu.cs.talkingnet.core;

import ua.cn.stu.cs.talkingnet.core.io.Pullable;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.utils.io.ConcurrentCircularBuffer;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class Pool extends Element implements Pushable, Pullable {

    private final ConcurrentCircularBuffer<byte[]> buffer;
    
    public Pool(int capacity, String title) {
        super(title);
        buffer = new ConcurrentCircularBuffer<byte[]>(byte[].class, capacity);
    }

    public void push_in(byte[] data, int size) {
        buffer.add(data);
    }

    public int pull_out(byte[] data, int size) {
        return pull_out(data, 0, size);
    }

    public int pull_out(byte[] data, int offset, int size) {
        byte[] result = buffer.removeAndGetOrGetNull();
        
        if (result==null) {
            result = new byte[size];
        }
        
        System.arraycopy(result, 0, data, offset, size);
        return size;
    }
}
