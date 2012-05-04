package talkingnet.audiodevices;

import org.junit.Test;
import talkingnet.audiodevices.defaults.DefaultAudioSink;
import talkingnet.audiodevices.defaults.DefaultAudioSource;
import talkingnet.codecs.ulaw.ULawCompressor;
import talkingnet.codecs.ulaw.ULawDecompressor;
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
    private ULawCompressor compresssor;
    private Pool pool;
    private ULawDecompressor decompresssor;
    private AudioSink sink;

    public ULawCompressedEchoTest() {
        
        bufferLength = DefaultAudioFormat.SAMPLING_RATE * DefaultAudioFormat.FRAME_SIZE;
        bufferLength /= (1000/bufferLengthInMillis);
        
        pool = new Pool(5, "pool");
        
        decompresssor = new ULawDecompressor(pool, "decompressor");
        compresssor = new ULawCompressor(decompresssor, "compressor");
        
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
