package talkingnet.core;

import java.util.ArrayList;
import java.util.List;
import talkingnet.core.io.Pushable;
import talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class Copier extends Element implements Pushable, Pushing {

    private List<Pushable> dstList = new ArrayList<Pushable>();
    
    public Copier(String title) {
        super(title);
    }

    public void push_in(byte[] data, int size) {
        push_out(data, size);
    }

    public void push_out(byte[] data, int size) {
        for (Pushable dst : dstList) {
            dst.push_in(data, size);
        }
    }
    
    public synchronized void addDestination(Pushable dst){
        dstList.add(dst);
    }
    
    public synchronized void removeDestination(Pushable dst){
        dstList.remove(dst);
    }
}
