package talkingnet.codecs.speex;

import java.io.StreamCorruptedException;
import org.xiph.speex.SpeexDecoder;
import talkingnet.codecs.Decompressor;
import talkingnet.core.Element;
import talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class SpeexDecompressor extends Element implements Decompressor {

    private Pushable sink;
    private SpeexDecoder decompressor;
    private int encodedFrameSize;
    private int resultSize;

    public SpeexDecompressor(int encodedFrameSize, int resultSize, String title) {
        super(title);
        this.encodedFrameSize = encodedFrameSize;
        this.resultSize = resultSize;
    }

    public boolean init(final int mode,
            final int sampleRate,
            final int channels,
            final boolean enhanced) {

        decompressor = new SpeexDecoder();
        boolean init = decompressor.init(mode, sampleRate, channels, enhanced);
        return init;
    }

    public byte[] decompress(byte[] data) {
        byte[] result = new byte[resultSize];
        int dstOffset = 0;
        int processed;
        for (int srcOffset = 0; srcOffset < data.length; srcOffset += encodedFrameSize) {
            try {
                decompressor.processData(data, srcOffset, encodedFrameSize);
                processed = decompressor.getProcessedDataByteSize();
                decompressor.getProcessedData(result, dstOffset);
                dstOffset += processed;
            } catch (StreamCorruptedException ex) {
                System.out.println(title + ": " + ex);
            }
        }

        return result;
    }

    public void push_in(byte[] data, int size) {
        byte[] decompressed = decompress(data);
        push_out(decompressed, decompressed.length);
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }

    public SpeexDecoder getDecompressor() {
        return decompressor;
    }

    public void setSink(Pushable sink) {
        this.sink = sink;
    }
}
