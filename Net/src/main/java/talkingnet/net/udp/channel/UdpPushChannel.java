package talkingnet.net.udp.channel;

import java.net.DatagramPacket;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpPushChannel extends PushChannel {
    
    public UdpPushChannel(UdpPushable sink) {
        super(sink);
    }
    
    public void write(DatagramPacket packet) {
        ((UdpPushable) sink).push_in(packet);
    }    
}
