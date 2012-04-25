package talkingnet.net.udp;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.SocketException;
import talkingnet.core.Element;
import talkingnet.core.io.Pushable;
import talkingnet.net.udp.channel.UdpPushChannel;
import talkingnet.net.udp.io.UdpPushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpDataWrapper extends Element implements Pushable, UdpPushing{

    private UdpPushChannel channel_out;
    private SocketAddress dstAddress;
    
    public UdpDataWrapper(
            SocketAddress dstAddress, UdpPushChannel channel_out, String title) {
        
        super(title);
        this.dstAddress  = dstAddress;
        this.channel_out = channel_out;
    }

    public void push_in(byte[] data, int size) {
        try {
            DatagramPacket packet = new DatagramPacket(data, size, dstAddress);
            push_out(packet);
        } catch (SocketException ex) {
            System.out.println(title+": "+ex);
        }
    }

    public void push_out(DatagramPacket packet) {
        channel_out.write(packet);
    }    
}
