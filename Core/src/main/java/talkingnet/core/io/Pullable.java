package talkingnet.core.io;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Pullable {
    
    int pull_out(byte[] data, int size);
    int pull_out(byte[] data, int offset, int size);
}
