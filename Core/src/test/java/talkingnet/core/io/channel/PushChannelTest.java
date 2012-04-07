package talkingnet.core.io.channel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import talkingnet.core.FooSink;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class PushChannelTest {

    private FooSink sink = new FooSink();
    private PushChannel channel = new PushChannel(sink);
    private byte[] array = {1, 3, 5, 0, 8, 6};

    @Test
    public void testPushChannel() {
        try {
            channel.write(array);
            channel.write(array);
            channel.write(array);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
