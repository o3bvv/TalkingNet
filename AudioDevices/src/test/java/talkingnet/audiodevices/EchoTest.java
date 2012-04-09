package talkingnet.audiodevices;

import org.junit.Test;
import talkingnet.audiodevices.defaults.DefaultAudioSink;
import talkingnet.audiodevices.defaults.DefaultAudioSource;
import talkingnet.core.Pool;
import talkingnet.core.io.channel.PullChannel;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class EchoTest {
    
    private int bufferLength = (11025*2/10);
    
    private AudioSource source;
    private PushChannel channel_out;
    private Pool pool;
    private PullChannel channel_in;
    private AudioSink sink;

    public EchoTest() {
        pool = new Pool("pool");
        channel_out = new PushChannel(pool);
        source = new DefaultAudioSource(bufferLength, channel_out, "src");
        channel_in = new PullChannel(pool);
        sink = new DefaultAudioSink(bufferLength, channel_in, "sink");
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
