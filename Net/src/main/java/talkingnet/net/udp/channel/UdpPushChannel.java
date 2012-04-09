package talkingnet.net.udp.channel;

import java.net.DatagramPacket;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpPushChannel extends PushChannel {

    public enum Mode {

        PACKETS, BUFFERS
    }

    private Mode mode = Mode.PACKETS;
    
    public UdpPushChannel(UdpPushable sink) {
        super(sink);
    }

    public UdpPushChannel(UdpPushable sink, Mode mode) {
        super(sink);
        this.mode = mode;
    }
    
    public void write(DatagramPacket packet) {
        if (mode.equals(Mode.PACKETS)){
            ((UdpPushable) sink).push_in(packet);
        } else {
            sink.push_in(packet.getData(), packet.getLength());
        }
    }
}
