package talkingnet.audiodevices;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.Test;
import talkingnet.audiodevices.defaults.DefaultAudioSink;
import talkingnet.core.Element;
import talkingnet.core.io.Pullable;
import talkingnet.core.io.Pushable;
import talkingnet.core.io.channel.PullChannel;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class EchoTest {
    
    private int bufferLength = (11025*2/10)*3;
    
    private AudioSource source;
    private PushChannel channel_out;
    private Pool pool;
    private PullChannel channel_in;
    private AudioSink sink;

    public EchoTest() {
        pool = new Pool("pool");
        channel_out = new PushChannel(pool);
        source = new FooAudioSource(bufferLength, channel_out, "src");
        channel_in = new PullChannel(pool);
        sink = new DefaultAudioSink(bufferLength, channel_in, "sink");
    }

    @Test
    public void testEcho() throws Exception{
        sink.open();
        source.open();
        sink.start();
        source.start();
        
        Thread.sleep(15000l);
        
        source.close();
        sink.close(); 
    }
    
    private class Pool extends Element implements Pushable, Pullable{

        private final ConcurrentLinkedQueue<byte[]> queue = new ConcurrentLinkedQueue<byte[]>();
        
        public Pool(String title) {
            super(title);
        }

        public void push_in(byte[] data, int size) {
            queue.add(data);
        }

        public void pull_out(byte[] data, int size) {
            byte[] result;
            if (queue.isEmpty()) {
                result = new byte[size];
            } else {
                result = queue.poll();
            }
            System.arraycopy(result, 0, data, 0, size);
        }
    }
}
