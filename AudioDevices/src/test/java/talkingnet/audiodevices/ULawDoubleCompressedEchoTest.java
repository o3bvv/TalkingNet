package talkingnet.audiodevices;

import org.junit.Test;
import talkingnet.audiodevices.defaults.DefaultAudioSink;
import talkingnet.audiodevices.defaults.DefaultAudioSource;
import talkingnet.codecs.ulaw.ULawCompresssor;
import talkingnet.codecs.ulaw.ULawDecompresssor;
import talkingnet.core.Pool;
import talkingnet.utils.audio.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class ULawDoubleCompressedEchoTest {

    private int bufferLengthInMillis = 64;
    private int bufferLength;
    
    private AudioSource source;
    private ULawCompresssor compresssor1;
    private ULawDecompresssor decompresssor1;
    private ULawCompresssor compresssor2;
    private ULawDecompresssor decompresssor2;
    private Pool pool;    
    private AudioSink sink;

    public ULawDoubleCompressedEchoTest() {

        bufferLength = DefaultAudioFormat.SAMPLING_RATE * DefaultAudioFormat.FRAME_SIZE;
        bufferLength /= (1000 / bufferLengthInMillis);

        pool = new Pool("pool");

        decompresssor2 = new ULawDecompresssor(pool, "decompressor2");
        compresssor2 = new ULawCompresssor(decompresssor2, "compressor2");
        
        decompresssor1 = new ULawDecompresssor(compresssor2, "decompressor1");
        compresssor1 = new ULawCompresssor(decompresssor1, "compressor1");

        source = new DefaultAudioSource(bufferLength, compresssor1, "src");
        sink = new DefaultAudioSink(bufferLength, pool, "sink");
    }

    @Test
    public void testEcho() throws Exception {
        sink.open();
        source.open();
        sink.start();
        source.start();

        Thread.sleep(25000l);

        source.close();
        sink.close();
    }
}
