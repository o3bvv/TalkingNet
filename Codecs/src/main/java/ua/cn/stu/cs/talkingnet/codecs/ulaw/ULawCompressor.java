package ua.cn.stu.cs.talkingnet.codecs.ulaw;

import ua.cn.stu.cs.talkingnet.codecs.Compressor;
import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 * @see http://read.pudn.com/downloads112/sourcecode/p2p/468700/peers-0.2/src/net/sourceforge/peers/media/AudioUlawEncodeDecode02.java__.htm
 */
public class ULawCompressor extends Element implements Compressor {

    private Pushable sink;
    
    private final short BIAS = 132;
    private final short CLIP = 32635;

    public ULawCompressor(Pushable sink, String title) {
        super(title);
        this.sink = sink;
    }

    public byte[] compress(byte[] data, int offset, int length) {
        byte[] result = new byte[length >> 1];

        int resPos = 0;

        for (int i = offset; i < length; i += 2) {
            short sample = (short) ((short) (data[i] << Byte.SIZE) + data[i + 1]);
            result[resPos] = comsress(sample);
            resPos++;
        }

        return result;
    }

    public byte comsress(short sample) {
        int sign = sample & 0x8000;
        
        if (sign != 0) {
            sample = (short) -sample;
            sign = 0x80;
        }

        if (sample > CLIP) {
            sample = CLIP;
        }

        sample += BIAS;

        int exp;
        short temp = (short) (sample << 1);

        for (exp = 7; exp > 0; exp--) {
            if ((temp & 0x8000) != 0) {
                break;
            }
            temp = (short) (temp << 1);
        }

        temp = (short) (sample >> (exp + 3));
        int mantis = temp & 0x000f;

        byte ulawByte = (byte) (sign | (exp << 4) | mantis);

        return (byte) ~ulawByte;
    }

    public void push_in(byte[] data, int size) {
        byte[] compressed = compress(data, 0, size);
        push_out(compressed, compressed.length);
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }
}
