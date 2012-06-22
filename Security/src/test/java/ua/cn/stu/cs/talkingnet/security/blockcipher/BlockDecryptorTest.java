package ua.cn.stu.cs.talkingnet.security.blockcipher;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class BlockDecryptorTest extends BlockCipherTest {
    
    private BlockDecryptor decryptor;

    public BlockDecryptorTest() {
        createDecryptor();
        initDecryptor();
    }

    private void createDecryptor() {
        preCreate();
        decryptor = new BlockDecryptor(cipher, sink, "GOST28147_decryptor");
    }

    private void initDecryptor() {
        preInit();
        decryptor.init(param);
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
