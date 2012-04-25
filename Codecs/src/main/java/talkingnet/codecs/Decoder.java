package talkingnet.codecs;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Decoder {
    
    public byte[] decode(byte[] data);
}
