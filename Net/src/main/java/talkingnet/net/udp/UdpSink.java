package talkingnet.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import talkingnet.core.Element;
import talkingnet.net.udp.channel.UdpPushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpSink extends Element implements UdpPushable {

    private DatagramSocket socket;
    
    public UdpSink(DatagramSocket socket, String title) {
        super(title);
    }

    /**
     * If socket is connected to some peer.
     */
    @Override
    public void push_in(byte[] data, int size) {
        DatagramPacket packet = new DatagramPacket(data, size);
        push_in(packet);
    }
    
    /**
     * If socket is not connected to any peer.
     */
    @Override
    public void push_in(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException ex) {
            System.out.println(title+": "+ex);
        }
    }
}