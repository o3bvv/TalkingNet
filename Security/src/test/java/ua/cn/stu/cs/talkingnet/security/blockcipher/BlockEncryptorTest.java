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
public class BlockEncryptorTest  extends BlockCipherTest {
   
    private BlockEncryptor encryptor;
    
    public BlockEncryptorTest() {
        createEncryptor();
        initEncryptor();
    }
    
    private void createEncryptor(){
        preCreate();
        encryptor = new BlockEncryptor(cipher, sink, "GOST28147_encryptor");
    }

    private void initEncryptor(){
        byte[] key = generateKey(Hex.decode(KEY));
        preInit();
        encryptor.init(param);
    }

    @Test
    public void testEncryption() {
        byte[] in =
                Hex.decode("4e6f77206973207468652074696d6520666f7220616c6c20");
        byte[] estimatedOut =
                Hex.decode("8ad3c8f56b27ff1fbd46409359bdc796bc350e71aac5f5c0");
        
        System.out.println("Estimated out:");
        sink.push_in(estimatedOut, estimatedOut.length);
        
        System.out.println("Real out:");
        encryptor.push_in(in, in.length);        
    }
}
