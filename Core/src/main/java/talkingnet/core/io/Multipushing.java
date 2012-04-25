package talkingnet.core.io;

import java.util.Collection;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Multipushing {
    
    void multipush_out(Collection<byte[]> data);
}
