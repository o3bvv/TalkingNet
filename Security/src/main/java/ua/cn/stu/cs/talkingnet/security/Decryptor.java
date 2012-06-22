package ua.cn.stu.cs.talkingnet.security;

import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface Decryptor extends Pushable, Pushing {

    public byte[] decrypt(byte[] data, int offset, int length);
}
