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
public class ULawCompressedEchoTest {
    
    private int bufferLengthInMillis = 20;
    private int bufferLength;
    
    private AudioSource source;
    private ULawCompresssor compresssor;
    private Pool pool;
    private ULawDecompresssor decompresssor;
    private AudioSink sink;

    public ULawCompressedEchoTest() {
        
        bufferLength = DefaultAudioFormat.SAMPLING_RATE * DefaultAudioFormat.FRAME_SIZE;
        bufferLength /= (1000/bufferLengthInMillis);
        
        pool = new Pool(5, "pool");
        
        decompresssor = new ULawDecompresssor(pool, "decompressor");
        compresssor = new ULawCompresssor(decompresssor, "compressor");
        
        source = new DefaultAudioSource(bufferLength, compresssor, "src");        
        sink = new DefaultAudioSink(bufferLength, pool, "sink");
    }

    @Test
    public void testEcho() throws Exception{
        sink.open();
        source.open();
        sink.start();
        source.start();
        
        Thread.sleep(25000l);
        
        source.close();
        sink.close(); 
    }
}
