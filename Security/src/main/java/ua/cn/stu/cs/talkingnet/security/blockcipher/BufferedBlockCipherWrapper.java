package ua.cn.stu.cs.talkingnet.security.blockcipher;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public abstract class BufferedBlockCipherWrapper extends Element {

    protected BufferedBlockCipher cipher;
    protected Pushable sink;

    public BufferedBlockCipherWrapper(
            BufferedBlockCipher cipher, Pushable sink, String title) {
        super(title);
        this.cipher = cipher;
        this.sink = sink;
    }
    
    public void init(CipherParameters params) throws IllegalArgumentException {
        cipher.init(isEncrypting(), params);
    }
    
    protected abstract boolean isEncrypting();
    
    protected byte[] processData(byte[] data, int offset, int length) {
        byte[] out = new byte[length];
        int processedSize = cipher.processBytes(data, offset, length, out, 0);
        try {
            cipher.doFinal(out, processedSize);
        } catch (CryptoException e) {
            // TODO: report exeption
        }
        return out;
    }

    public BufferedBlockCipher getBlockCipher() {
        return cipher;
    }
}
