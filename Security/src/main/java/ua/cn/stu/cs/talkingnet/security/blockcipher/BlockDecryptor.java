package ua.cn.stu.cs.talkingnet.security.blockcipher;

import org.bouncycastle.crypto.BufferedBlockCipher;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.security.Decryptor;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class BlockDecryptor extends BufferedBlockCipherWrapper
        implements Decryptor {

    public BlockDecryptor(
            BufferedBlockCipher cipher,
            Pushable sink, String title) {
        super(cipher, sink, title);
    }

    public byte[] decrypt(byte[] data, int offset, int length) {
        return processData(data, offset, length);
    }

    public void push_in(byte[] data, int size) {
        if (byPass==true){
            push_out(data, size);
        } else {
            byte[] decrypted = decrypt(data, 0, size);
            push_out(decrypted, decrypted.length);
        }
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }

    @Override
    protected boolean isEncrypting() {
        return false;
    }
}
