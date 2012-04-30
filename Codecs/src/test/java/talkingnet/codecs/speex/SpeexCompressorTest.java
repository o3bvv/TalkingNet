package talkingnet.codecs.speex;

import org.junit.Test;
import talkingnet.core.foo.sink.FooSink;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class SpeexCompressorTest {

    private FooSink sink;
    private SpeexCompressor compressor;
    
    private byte[] buffer;
    private int channels = 2;
    private float scale = 0.125f*2;
    private int range = (Byte.MAX_VALUE+1)*(Byte.MAX_VALUE+1) - 1;
    
    {
        sink = new FooSink("sink");
        compressor = new SpeexCompressor("compressor");
        compressor.setSink(sink);
        compressor.init(0, 3, 16000, channels);
    }
    
    @Test
    public void testCompressor() {
        buffer = new byte[compressor.getRawFrameSize()*16];
        fillSine();
        System.out.println("Raw data size: "+buffer.length);
        
        compressor.setBufferSize(buffer.length);
        compressor.push_in(buffer, buffer.length);
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
}
