package talkingnet.audiodevices;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import org.junit.Test;
import talkingnet.audiodevices.util.FooAudioFormat;
import talkingnet.core.io.Pushable;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioSourceTest {
    
    private AudioSource source;    
    private AudioFormat format;
    private Mixer mixer;
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
        
        format = new FooAudioFormat();
        
        initDefaultMixer();
        
        source = new AudioSource(
                mixer, format, bufferLength, channel, "audioSrc");
    }

    private void initDefaultMixer(){
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(infos[0]);
    }
    
    @Test
    public void testSource() throws Exception {
        source.open();
        source.start();
        
        Thread.sleep(5000l);
        
        source.close();        
    }
}
