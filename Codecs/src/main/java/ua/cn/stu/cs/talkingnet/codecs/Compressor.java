package ua.cn.stu.cs.talkingnet.codecs;

import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Compressor extends Pushable, Pushing{

    public byte[] compress(byte[] data);
}
