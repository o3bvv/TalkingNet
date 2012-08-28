package ua.cn.stu.cs.talkingnet.security.blockcipher;

import org.bouncycastle.crypto.BufferedBlockCipher;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.security.Encryptor;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class BlockEncryptor extends BufferedBlockCipherWrapper
        implements Encryptor {

    public BlockEncryptor(
            BufferedBlockCipher cipher,
            Pushable sink, String title) {
        super(cipher, sink, title);
    }

    public byte[] encrypt(byte[] data, int offset, int length) {
        return processData(data, offset, length);
    }

    public void push_in(byte[] data, int size) {
        if (byPass==true){
            push_out(data, size);
        } else {
            byte[] encrypted = encrypt(data, 0, size);
            push_out(encrypted, encrypted.length);
        }
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }

    @Override
    protected boolean isEncrypting() {
        return true;
    }
}
