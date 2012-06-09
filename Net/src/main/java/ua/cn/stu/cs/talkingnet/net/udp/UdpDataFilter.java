package ua.cn.stu.cs.talkingnet.net.udp;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushing;
import ua.cn.stu.cs.talkingnet.net.udp.io.UdpPushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpDataFilter extends Element implements UdpPushable, Pushing {

    private Pushable sink;
    private SocketAddress filteringAddress;

    public UdpDataFilter(
            SocketAddress filteringAddress,
            Pushable sink,
            String title) {
        
        super(title);
        this.filteringAddress = filteringAddress;
        this.sink = sink;
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
        sink.push_in(data, size);
    }

    public SocketAddress getFilteringAddress() {
        return filteringAddress;
    }

    public synchronized void setFilteringAddress(SocketAddress filteringAddress) {
        this.filteringAddress = filteringAddress;
    }

}
