package talkingnet.core.io;

import java.util.Collection;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Multipushable {

    void multipush_in(Collection<byte[]>  data);
}
