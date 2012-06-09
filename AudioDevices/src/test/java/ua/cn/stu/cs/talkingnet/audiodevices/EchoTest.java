package ua.cn.stu.cs.talkingnet.audiodevices;

import org.junit.Test;
import ua.cn.stu.cs.talkingnet.audiodevices.defaults.DefaultAudioSink;
import ua.cn.stu.cs.talkingnet.audiodevices.defaults.DefaultAudioSource;
import ua.cn.stu.cs.talkingnet.core.Pool;
import ua.cn.stu.cs.talkingnet.utils.audio.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class EchoTest {
    
    private int bufferLengthInMillis = 48;
    private int bufferLength;
    
    private AudioSource source;
    private Pool pool;
    private AudioSink sink;

    public EchoTest() {
        
        bufferLength = DefaultAudioFormat.SAMPLING_RATE * DefaultAudioFormat.FRAME_SIZE;
        bufferLength /= (1000/bufferLengthInMillis);
        
        pool = new Pool(5, "pool");
        source = new DefaultAudioSource(bufferLength, pool, "src");
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
