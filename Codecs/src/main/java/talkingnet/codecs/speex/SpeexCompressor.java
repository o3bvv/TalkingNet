package talkingnet.codecs.speex;

import org.xiph.speex.SpeexEncoder;
import talkingnet.codecs.Compressor;
import talkingnet.core.Element;
import talkingnet.core.io.Pushable;
import talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class SpeexCompressor extends Element implements Compressor, Pushable, Pushing {

    private Pushable sink;
    private SpeexEncoder compressor;
    private int encodedFrameSize;
    private int rawFrameSize;
    private int dataFrameRatio;
    private int resultSize;

    public SpeexCompressor(Pushable sink, String title) {
        super(title);
        this.sink = sink;
        compressor = new SpeexEncoder();
    }

    public boolean init(final int mode,
            final int quality,
            final int sampleRate,
            final int channels) {

        boolean init = compressor.init(mode, quality, sampleRate, channels);

        if (init) {
            calcFrameSizes(channels);
            setBufferSize(rawFrameSize);
        }

        return init;
    }

    public byte[] compress(byte[] data) {

        byte[] result = new byte[resultSize];

        for (int i = 0; i < dataFrameRatio; i++) {
            compressor.processData(data, i * rawFrameSize, rawFrameSize);
            compressor.getProcessedData(result, i * encodedFrameSize);
        }

        return result;
    }

    public void push_in(byte[] data, int size) {
        byte[] compressed = compress(data);
        push_out(compressed, compressed.length);
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }

    private void calcFrameSizes(int channels) {
        calcRawFrameSize(channels);
        calcEncodedFrameSize(channels);
    }

    private void calcEncodedFrameSize(int channels) {
        int encodingFrameSize = compressor.getEncoder().getEncodedFrameSize();
        int channelsKoef = (int) (Math.pow(2, channels) - 1);
        encodedFrameSize = (int) Math.floor((encodingFrameSize / Byte.SIZE) + channelsKoef);
    }

    private void calcRawFrameSize(int channels) {
        rawFrameSize = compressor.getEncoder().getFrameSize() * 2 * channels;
    }

    public void setBufferSize(final int bufferSize) {
        dataFrameRatio = bufferSize / rawFrameSize;
        resultSize = encodedFrameSize * dataFrameRatio;
    }

    public SpeexEncoder getEncoder() {
        return compressor;
    }

    public int getEncodedFrameSize() {
        return encodedFrameSize;
    }

    public int getRawFrameSize() {
        return rawFrameSize;
    }

    public int getResultSize() {
        return resultSize;
    }
}
