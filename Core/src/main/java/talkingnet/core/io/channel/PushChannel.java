package talkingnet.core.io.channel;

import java.io.IOException;
import java.io.OutputStream;
import talkingnet.core.io.Sink;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class PushChannel extends OutputStream {

    protected Sink sink;

    public PushChannel(Sink sink) {
        this.sink = sink;
    }

    @Override
    public void write(int i) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void write(byte[] data) throws IOException {
        write(data, 0, data.length);
    }
    
    public void write(byte[] data, int length) throws IOException {
        write(data, 0, length);
    }
    
    @Override
    public void write(byte[] data, int offset, int length) throws IOException {
        sink.takeUp(data, length);
    }
}
