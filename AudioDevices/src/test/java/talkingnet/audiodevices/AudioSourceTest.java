package talkingnet.audiodevices;

import org.junit.Test;
import talkingnet.audiodevices.defaults.DefaultAudioSource;
import talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioSourceTest {
    
    private AudioSource source;
    private Pushable sink;
    
    private int bufferLength = 1024*16;
    
    public AudioSourceTest() {
        sink = new Pushable() {
            public void push_in(byte[] data, int size) {
                System.out.println("Income bytes: "+size);
            }
        };
        
        source = new DefaultAudioSource(bufferLength, sink, "audioSrc");
    }
    
    @Test
    public void testSource() throws Exception {
        source.open();
        source.start();
        
        Thread.sleep(5000l);
        
        source.close();        
    }
}
