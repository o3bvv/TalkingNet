package talkingnet.codecs;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Decompressor {
    
    public byte[] decompress(byte[] data);
}
