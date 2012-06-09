package ua.cn.stu.cs.talkingnet.audiodevices;

import javax.sound.sampled.AudioFormat;
import org.junit.Test;
import ua.cn.stu.cs.talkingnet.core.io.Pullable;
import ua.cn.stu.cs.talkingnet.utils.audio.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioSinkTest {
    
    private Pullable source;
    private AudioSink sink;
    
    private AudioFormat format;
    
    private int bufferLength = 1000;
    
    public AudioSinkTest() {        
        source = new SineGenerator();        
        format = new DefaultAudioFormat();   
        sink = new AudioSink(null, format, bufferLength, source, "audioSink");        
    }

    @Test
    public void testAudioSink() throws Exception {
        sink.open();        
        sink.start();
        
        Thread.sleep(3000l);
        
        sink.close(); 
    }

    private class SineGenerator implements Pullable {

        byte[] buffer;
        float scale = 0.125f*2;
        int range = (Byte.MAX_VALUE+1)*(Byte.MAX_VALUE+1) - 1;

        public SineGenerator() {
            buffer = new byte[bufferLength];
            fillSine();
        }
        
        private void fillSine(){
            int sampleNumber = 0;
            for (int i = 0; i < buffer.length; i += 2) {
                int sample = (int) (Math.sin(sampleNumber*scale) * range);
                buffer[i+1]   = (byte) (sample & 0xFF);
                buffer[i] = (byte) ((sample >> Byte.SIZE) & 0xFF);
                sampleNumber++;
            }
        }
        
        public int pull_out(byte[] data, int size) {
            System.arraycopy(buffer, 0, data, 0, size);
            return size;
        }

        public int pull_out(byte[] data, int offset, int size) {
            System.arraycopy(buffer, 0, data, offset, size);
            return size;
        }
    }
}
