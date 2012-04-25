package talkingnet.codecs.g711;

import talkingnet.codecs.Decompressor;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 * @see http://code.google.com/p/codecg711/source/browse/trunk/CodecG711/src/org/mobicents/media/server/impl/dsp/audio/g711/ulaw/Decoder.java
 */
public class G711UlawDecompressor implements Decompressor {

    private final static int cBias = 0x84;
    private int QUANT_MASK = 0xF;
    private final static int SEG_SHIFT = 4;
    private final static int SEG_MASK = 0x70;
    private final static int SIGN_BIT = 0x80;
    
    @Override
    public byte[] decompress(byte[] src) {
        byte[] result = new byte[src.length << 1];
        process(src, result);
        return result;
    }

    private void process(byte[] src, byte[] dst) {
        int j = 0;
        for (int i = 0; i < src.length; i++) {
            short s = ulaw2linear(src[i]);
            dst[j++] = (byte) s;
            dst[j++] = (byte) (s >> 8);
        }
    }
    
    private short ulaw2linear(byte u_val) {
        int t;

        u_val = (byte) ~u_val;

        t = ((u_val & QUANT_MASK) << 3) + cBias;
        t <<= (u_val & SEG_MASK) >> SEG_SHIFT;

        boolean s = (u_val & SIGN_BIT) == SIGN_BIT;
        return (short) (s ? (cBias - t) : (t - cBias));
    }
}
