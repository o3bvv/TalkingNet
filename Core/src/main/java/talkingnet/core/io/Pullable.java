package talkingnet.core.io;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Pullable {
    
    void pull_out(byte[] data, int size);
}
