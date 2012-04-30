package talkingnet.codecs.speex;

import org.junit.Test;
import talkingnet.core.foo.sink.FooSink;
import talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class SpeexDecompressorTest {    
    
    private SpeexDecompressor decompressor;
    private SpeexCompressor compressor;
    private FooSink sink;
    
    private int mode = 0;
    private int quality = 3;
    private int channels = 1;
    private int sampleRate = 16000;    
    private boolean enhance = false;
    
    private int bufferLength;
    
    {
        sink = new FooSink("sink");
        compressor = new SpeexCompressor("compressor");
        compressor.init(mode, quality, sampleRate, channels);
        
        bufferLength = compressor.getRawFrameSize() * 4;
        compressor.setBufferSize(bufferLength);
        
        decompressor = new SpeexDecompressor(
                compressor.getEncodedFrameSize(),
                bufferLength,
                "decompressor");
        
        decompressor.setSink(sink);        
        
        compressor.setSink(decompressor);
        decompressor.init(mode, sampleRate, channels, enhance);        
    }
    
    @Test
    public void testSpeexDecompressor() {
        for (int i = 1; i < 4; i++) {
            System.out.printf("Test #%d:\n", i);
            testCodec();
        }
    }
    
    private void testCodec(){
        byte[] src = RandomData.getRandomDataFixedLength(bufferLength);
        sink.push_in(src, src.length);        
        compressor.push_in(src, src.length);
    }
}
