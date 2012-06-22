package ua.cn.stu.cs.talkingnet.security.blockcipher;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithSBox;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import ua.cn.stu.cs.talkingnet.core.foo.sink.FooSink;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class BlockDecryptorTest {

    static final int GOST28147_KEY_LENGTH = 32;
    private static String KEY = "0123456789abcdef";
    
    private BlockDecryptor decryptor;
    private Pushable sink;

    public BlockDecryptorTest() {
        createDecryptor();
        initDecryptor();
    }

    private void createDecryptor() {
        BufferedBlockCipher cipher =
                new BufferedBlockCipher(new GOST28147Engine());
        sink = new FooSink("fooSink");
        decryptor = new BlockDecryptor(cipher, sink, "GOST28147_decryptor");
    }

    private void initDecryptor() {
        byte[] key = generateKey(Hex.decode(KEY));
        CipherParameters param =
                new ParametersWithSBox(
                new KeyParameter(key), GOST28147Engine.getSBox("E-A"));
        decryptor.init(param);
    }

    private byte[] generateKey(byte[] startkey) {
        byte[] newKey = new byte[GOST28147_KEY_LENGTH];

        GOST3411Digest digest = new GOST3411Digest();

        digest.update(startkey, 0, startkey.length);
        digest.doFinal(newKey, 0);

        return newKey;
    }

    @Test
    public void testEncryption() {
        byte[] in =
                Hex.decode("8ad3c8f56b27ff1fbd46409359bdc796bc350e71aac5f5c0");
        byte[] estimatedOut =
                Hex.decode("4e6f77206973207468652074696d6520666f7220616c6c20");

        System.out.println("Estimated out:");
        sink.push_in(estimatedOut, estimatedOut.length);

        System.out.println("Real out:");
        decryptor.push_in(in, in.length);
    }
}
