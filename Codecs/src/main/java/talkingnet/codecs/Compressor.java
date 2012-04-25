package talkingnet.codecs;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Compressor {

    public byte[] compress(byte[] data);
}
