package talkingnet.core.io;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Pushable {

    void push(byte[] data, int size);
}
