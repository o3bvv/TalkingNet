package ua.cn.stu.cs.talkingnet.security.blockcipher;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithSBox;
import org.bouncycastle.util.encoders.Hex;
import ua.cn.stu.cs.talkingnet.core.foo.sink.FooSink;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public abstract class BlockCipherTest {
    
    protected static final int GOST28147_KEY_LENGTH = 32;
    protected static String KEY = "0123456789abcdef";
    
    protected BufferedBlockCipher cipher;
    protected CipherParameters param;
    protected Pushable sink;

    protected void preCreate(){
        cipher = new BufferedBlockCipher(new GOST28147Engine());
        sink = new FooSink("fooSink");
    }
    
    protected void preInit(){
        byte[] key = generateKey(Hex.decode(KEY));
        param = new ParametersWithSBox(
                    new KeyParameter(key), GOST28147Engine.getSBox("E-A"));
    }
    
    protected byte[] generateKey(byte[] startkey) {
        byte[] newKey = new byte[GOST28147_KEY_LENGTH];

        GOST3411Digest digest = new GOST3411Digest();

        digest.update(startkey, 0, startkey.length);
        digest.doFinal(newKey, 0);

        return newKey;
    }
}
