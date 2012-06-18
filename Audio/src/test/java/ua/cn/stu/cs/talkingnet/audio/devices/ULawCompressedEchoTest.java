package ua.cn.stu.cs.talkingnet.audio.devices;

import ua.cn.stu.cs.talkingnet.audio.devices.AudioSink;
import ua.cn.stu.cs.talkingnet.audio.devices.AudioSource;
import org.junit.Test;
import ua.cn.stu.cs.talkingnet.audio.devices.defaults.DefaultAudioSink;
import ua.cn.stu.cs.talkingnet.audio.devices.defaults.DefaultAudioSource;
import ua.cn.stu.cs.talkingnet.codecs.ulaw.ULawCompressor;
import ua.cn.stu.cs.talkingnet.codecs.ulaw.ULawDecompressor;
import ua.cn.stu.cs.talkingnet.core.Pool;
import ua.cn.stu.cs.talkingnet.utils.audio.DefaultAudioFormat;

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
