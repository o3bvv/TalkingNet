package talkingnet.core.io;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Pushable {

    void push_in(byte[] data, int size);
}
