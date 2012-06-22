package ua.cn.stu.cs.talkingnet.codecs.ulaw;

import ua.cn.stu.cs.talkingnet.codecs.Decompressor;
import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 * @see http://read.pudn.com/downloads112/sourcecode/p2p/468700/peers-0.2/src/net/sourceforge/peers/media/AudioUlawEncodeDecode02.java__.htm
 */
public class ULawDecompressor extends Element implements Decompressor {
    private Pushable sink;

    public ULawDecompressor(Pushable sink, String title) {
        super(title);
        this.sink = sink;
    }

    public byte[] decompress(byte[] data, int offset, int length) {
        byte[] result = new byte[length << 1];
        int resPos = 0;
        
        for (int i = offset; i < length; i++) {
            short sample = decompress(data[i]);
            result[resPos++] = (byte) (sample >> Byte.SIZE);
            result[resPos++] = (byte) (sample & 0xFF);
        }
        
        return result;
    }
    
    public short decompress(byte ulawByte) {  
        ulawByte = (byte) (~ulawByte);  
        int sign = ulawByte & 0x80;  
        int exp = (ulawByte & 0x70) >> 4;  
        int mantis = ulawByte & 0xf;  
        int rawValue = (mantis << (12 - 8 + (exp - 1))) + (132 << exp) - 132;  
        return (short) ((sign != 0) ? -rawValue : rawValue);  
    }

    public void push_in(byte[] data, int size) {
        byte[] decompressed = decompress(data, 0, size);
        push_out(decompressed, decompressed.length);
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, data.length);
    }
}
