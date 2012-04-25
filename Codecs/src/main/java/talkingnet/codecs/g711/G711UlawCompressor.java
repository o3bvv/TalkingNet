package talkingnet.codecs.g711;

import talkingnet.codecs.Compressor;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 * @see http://code.google.com/p/codecg711/source/browse/trunk/CodecG711/src/org/mobicents/media/server/impl/dsp/audio/g711/ulaw/Encoder.java
 */
public class G711UlawCompressor implements Compressor {

    private final static int cBias = 0x84;
    private static short seg_end[] = new short[]{
        0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF};

    
    @Override
    public byte[] compress(byte[] src) {
        byte[] result = new byte[src.length >> 1];
        process(src, result);
        return result;
    }

    private void process(byte[] src, byte[] dst) {
        int offset = 0;
        short sample;
        for (int i = 0; i < dst.length; i++) {
            sample = (short) (((src[offset++] & 0xFF) | (src[offset++]) << 8));
            dst[i] = linear2ulaw(sample);
        }
    }

    private byte linear2ulaw(short pcm_val) {
        int mask;
        int seg;
        byte uval;

        if (pcm_val < 0) {
            pcm_val = (short) (cBias - pcm_val);
            mask = 0x7F;
        } else {
            pcm_val += cBias;
            mask = 0xFF;
        }

        seg = search(pcm_val, seg_end, 8);

        if (seg >= 8) {
            return (byte) (0x7F ^ mask);
        } else {
            uval = (byte) ((seg << 4) | ((pcm_val >> (seg + 3)) & 0xF));
            return (byte) (uval ^ mask);
        }
    }
    
    private static int search(int val, short[] table, int size) {
        int i;

        for (i = 0; i < size; i++) {
            if (val <= table[i]) {
                return (i);
            }
        }
        return (size);
    }
}
