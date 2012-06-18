package ua.cn.stu.cs.talkingnet.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.net.udp.io.UdpPushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpBin extends Element implements UdpPushable {

    private UdpSink sink;
    private UdpSource source;
    private DatagramSocket socket;

    public UdpBin(UdpPushable udpSink, int bufferLength, String title) throws SocketException {
        super(title);
        socket = new DatagramSocket();
        createElements(udpSink, bufferLength);
    }

    public UdpBin(int port, UdpPushable udpSink, int bufferLength, String title) throws SocketException {
        super(title);
        socket = new DatagramSocket(port);
        createElements(udpSink, bufferLength);
    }

    public UdpBin(SocketAddress localAddress, UdpPushable udpSink, int bufferLength, String title) throws SocketException {
        super(title);
        socket = new DatagramSocket(localAddress);
        createElements(udpSink, bufferLength);
    }

    public UdpBin(SocketAddress address, int port, UdpPushable udpSink, int bufferLength, String title) throws SocketException {
        super(title);
        socket = new DatagramSocket(port);
        socket.connect(address);
        createElements(udpSink, bufferLength);
    }

    public UdpBin(SocketAddress localAddress, SocketAddress remoteAddress, UdpPushable udpSink, int bufferLength, String title) throws SocketException {
        super(title);
        socket = new DatagramSocket(localAddress);
        socket.connect(remoteAddress);
        createElements(udpSink, bufferLength);
    }

    private void createElements(UdpPushable udpSink, int bufferLength) {
        source = new UdpSource(socket, udpSink, bufferLength, title + "_src");
        sink = new UdpSink(socket, title + "_sink");
    }

    @Override
    public void push_in(DatagramPacket packet) {
        sink.push_in(packet);
    }

    @Override
    public void push_in(byte[] data, int size) {
        sink.push_in(data, size);
    }

    public void start() {
        source.start();
    }

    public void stop() {
        source.stop();
    }
    
    public int getUdpPort(){
        return socket.getLocalPort();
    }
}
