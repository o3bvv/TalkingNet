package talkingnet.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import talkingnet.core.Element;
import talkingnet.core.io.Pushing;
import talkingnet.core.io.channel.PushChannel;
import talkingnet.net.udp.io.UdpPushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpDataFilter extends Element implements UdpPushable, Pushing {

    private PushChannel channel_out;
    private SocketAddress filteringAddress;

    public UdpDataFilter(
            SocketAddress filteringAddress,
            PushChannel channel_out,
            String title) {
        
        super(title);
        this.filteringAddress = filteringAddress;
        this.channel_out = channel_out;
    }

    public void push_in(DatagramPacket packet) {
        if (packet.getSocketAddress().equals(filteringAddress)){
            push_out(packet.getData(), packet.getLength());
        }
    }

    public void push_in(byte[] data, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void push_out(byte[] data, int size) {
        try {
            channel_out.write(data, size);
        } catch (IOException ex) {
            System.out.println(title+": "+ex);
        }
    }

    public SocketAddress getFilteringAddress() {
        return filteringAddress;
    }

    public synchronized void setFilteringAddress(SocketAddress filteringAddress) {
        this.filteringAddress = filteringAddress;
    }

}
