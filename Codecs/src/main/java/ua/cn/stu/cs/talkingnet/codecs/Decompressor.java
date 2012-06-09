package ua.cn.stu.cs.talkingnet.codecs;

import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Decompressor extends Pushable, Pushing{
    
    public byte[] decompress(byte[] data);
}
