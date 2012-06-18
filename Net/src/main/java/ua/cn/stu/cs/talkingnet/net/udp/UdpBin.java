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

    /**
     * @param udpSink Sink received packets push to
     * @param bufferSize Size of data to read
     * @param title Title of the element
     */
    public UdpBin(UdpPushable udpSink, int bufferSize, String title) throws SocketException {
        super(title);
        socket = new DatagramSocket();
        createElements(udpSink, bufferSize);
    }

    /**
     * @param port Local port to bind to
     * @param udpSink Sink received packets push to
     * @param bufferSize Size of data to read
     * @param title Title of the element
     */
    public UdpBin(int port, UdpPushable udpSink, int bufferSize, String title) throws SocketException {
        super(title);
        socket = new DatagramSocket(port);
        createElements(udpSink, bufferSize);
    }

    /**
     * @param remoteAddress Remote address to connect to
     * @param udpSink Sink received packets push to
     * @param bufferSize Size of data to read
     * @param title Title of the element
     */
    public UdpBin(SocketAddress remoteAddress, UdpPushable udpSink, int bufferSize, String title) throws SocketException {
        super(title);
        socket = new DatagramSocket();
        socket.connect(remoteAddress);
        createElements(udpSink, bufferSize);
    }
    
    /**
     * @param remoteAddress Remote address to connect to
     * @param port Local port to bind to
     * @param udpSink Sink received packets push to
     * @param bufferSize Size of data to read
     * @param title Title of the element
     */
    public UdpBin(SocketAddress remoteAddress, int port, UdpPushable udpSink, int bufferLength, String title) throws SocketException {
        super(title);
        socket = new DatagramSocket(port);
        socket.connect(remoteAddress);
        createElements(udpSink, bufferLength);
    }

    /**
     * @param remoteAddress Remote address to connect to
     * @param localAddress Local address to bind to
     * @param udpSink Sink received packets push to
     * @param bufferSize Size of data to read
     * @param title Title of the element
     */
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
