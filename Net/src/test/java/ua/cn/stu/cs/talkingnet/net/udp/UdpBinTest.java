package ua.cn.stu.cs.talkingnet.net.udp;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import org.junit.Test;
import ua.cn.stu.cs.talkingnet.net.udp.io.UdpPushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpBinTest {

    private int masterPort = 1234;
    private int slavePort  = 4321;
    
    private UdpBin masterBin;
    private UdpBin slaveBin;
    
    private byte[] buffer = {1, 2, 4, 8, 16, 32};

    @Test
    public void testUdpBin() throws Exception {
        init();
        
        slaveBin.start();
        masterBin.start();

        masterBin.push_in(buffer, buffer.length);
        
        Thread.sleep(100l);

        slaveBin.stop();
        masterBin.stop();
    }
    
    private void init() throws SocketException{
        initMasterBin();
        initSlaveBin();
    }
    
    private void initMasterBin() throws SocketException {
        UdpPushable fooSink = new UdpPushable() {

            public void push_in(DatagramPacket packet) {
                push_in(packet.getData(), packet.getLength());
            }

            public void push_in(byte[] data, int size) {
                System.out.println("Master income : "+Arrays.toString(data));
            }
        };
        
        SocketAddress peerAddress = new InetSocketAddress("127.0.0.1", slavePort);
        masterBin = new UdpBin(
                peerAddress, masterPort, fooSink, buffer.length, "masterBin");
    }

    private void initSlaveBin() throws SocketException {
        UdpPushable fooSink = new UdpPushable() {

            public void push_in(DatagramPacket packet) {
                slaveBin.push_in(packet);
            }

            public void push_in(byte[] data, int size) {
                slaveBin.push_in(data, size);
            }
        };
        
        SocketAddress peerAddress = new InetSocketAddress("127.0.0.1", masterPort);
        slaveBin = new UdpBin(
                peerAddress, slavePort, fooSink, buffer.length, "slaveBin");
    }
}
