package talkingnet.core.io.channel;

import java.io.IOException;
import java.io.InputStream;
import talkingnet.core.io.Pullable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class PullChannel extends InputStream {

    protected Pullable source;

    public PullChannel(Pullable source) {
        this.source = source;
    }

    @Override
    public int read() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
    
    @Override
    public int read(byte[] data) throws IOException {
        return read(data, 0, data.length);
    }
    
    public int read(byte[] data, int length) throws IOException {
        return read(data, 0, length);
    }
    
    @Override
    public int read(byte[] buffer, int offset, int length) {
        source.pull_out(buffer, length);
        return buffer.length;
    }    
}
