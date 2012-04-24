package talkingnet.net.udp;

import talkingnet.net.udp.io.UdpPushable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import talkingnet.core.Element;
import talkingnet.net.udp.channel.UdpPushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpBin extends Element implements UdpPushable {

    private UdpSink sink;
    private UdpSource source;
    private DatagramSocket socket;
    
    public UdpBin(UdpPushChannel channel, int bufferLength, String title) {
        super(title);
        try {
            socket = new DatagramSocket();
            createElements(channel, bufferLength);
        } catch (SocketException ex) {
            System.out.println(title+":"+ex);
        }
    }
    
    public UdpBin(int port, UdpPushChannel channel, int bufferLength, String title) {
        super(title);
        try {
            socket = new DatagramSocket(port);
            createElements(channel, bufferLength);
        } catch (SocketException ex) {
            System.out.println(title+":"+ex);
        }
    }
    
    public UdpBin(SocketAddress address, UdpPushChannel channel, int bufferLength, String title) {
        super(title);
        try {
            socket = new DatagramSocket();
            socket.connect(address);
            createElements(channel, bufferLength);
        } catch (SocketException ex) {
            System.out.println(title+":"+ex);
        }
    }

    public UdpBin(SocketAddress address, int port, UdpPushChannel channel, int bufferLength, String title) {
        super(title);
        try {
            socket = new DatagramSocket(port);
            socket.connect(address);
            createElements(channel, bufferLength);
        } catch (SocketException ex) {
            System.out.println(title+":"+ex);
        }
    }
    
    private void createElements(UdpPushChannel channel, int bufferLength){
        source = new UdpSource(socket, channel, bufferLength, title+"_src");
        sink = new UdpSink(socket, title+"_sink");
    }
    
    @Override
    public void push_in(DatagramPacket packet) {
        sink.push_in(packet);
    }

    @Override
    public void push_in(byte[] data, int size) {
        sink.push_in(data, size);
    }
    
    public void start(){
        source.start();
    }
    
    public void stop(){
        source.stop();
    }
}
