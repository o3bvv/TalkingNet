package talkingnet.net.udp;

import java.net.DatagramPacket;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface UdpPushing {

    public void push_out(DatagramPacket packet);
}
