package talkingnet.core.io;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Sink {

    void takeUp(byte[] data, int size);
}
