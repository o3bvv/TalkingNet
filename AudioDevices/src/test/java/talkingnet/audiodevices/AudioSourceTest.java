package talkingnet.audiodevices;

import org.junit.Test;
import talkingnet.audiodevices.defaults.DefaultAudioSource;
import talkingnet.core.io.Pushable;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioSourceTest {
    
    private AudioSource source;    
    private PushChannel channel;
    private Pushable sink;
    
    private int bufferLength = 1024*16;
    
    public AudioSourceTest() {
        sink = new Pushable() {
            public void push_in(byte[] data, int size) {
                System.out.println("Income bytes: "+size);
            }
        };
        
        channel = new PushChannel(sink);
        
        source = new DefaultAudioSource(bufferLength, channel, "audioSrc");
    }
    
    @Test
    public void testSource() throws Exception {
        source.open();
        source.start();
        
        Thread.sleep(5000l);
        
        source.close();        
    }
}
