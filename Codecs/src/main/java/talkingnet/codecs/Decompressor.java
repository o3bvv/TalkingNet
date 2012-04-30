package talkingnet.codecs;

import talkingnet.core.io.Pushable;
import talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Decompressor extends Pushable, Pushing{
    
    public byte[] decompress(byte[] data);
}
