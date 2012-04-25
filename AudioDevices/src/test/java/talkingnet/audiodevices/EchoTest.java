package talkingnet.audiodevices;

import org.junit.Test;
import talkingnet.audiodevices.defaults.DefaultAudioSink;
import talkingnet.audiodevices.defaults.DefaultAudioSource;
import talkingnet.core.Pool;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class EchoTest {
    
    private int bufferLength = (11025*2/10);
    
    private AudioSource source;
    private Pool pool;
    private AudioSink sink;

    public EchoTest() {
        pool = new Pool("pool");
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
