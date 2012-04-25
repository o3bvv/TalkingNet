package talkingnet.core;

import java.util.concurrent.ConcurrentLinkedQueue;
import talkingnet.core.io.Pullable;
import talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class Pool extends Element implements Pushable, Pullable {

    private final ConcurrentLinkedQueue<byte[]> queue =
                        new ConcurrentLinkedQueue<byte[]>();

    public Pool(String title) {
        super(title);
    }

    public void push_in(byte[] data, int size) {
        queue.add(data);
    }

    public int pull_out(byte[] data, int size) {
        byte[] result;
        if (queue.isEmpty()) {
            result = new byte[size];
        } else {
            result = queue.poll();
        }
        System.arraycopy(result, 0, data, 0, size);
        return size;
    }
    
    public void flush(){
        queue.clear();
    }
}
