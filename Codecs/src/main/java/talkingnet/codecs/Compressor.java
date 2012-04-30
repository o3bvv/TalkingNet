package talkingnet.codecs;

import talkingnet.core.io.Pushable;
import talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Compressor extends Pushable, Pushing{

    public byte[] compress(byte[] data);
}
