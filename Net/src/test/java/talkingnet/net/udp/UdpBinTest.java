package talkingnet.net.udp;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import org.junit.Test;
import talkingnet.net.udp.channel.UdpPushChannel;
import talkingnet.net.udp.channel.UdpPushable;

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

    public UdpBinTest() {
        initMasterBin();
        initSlaveBin();
    }

    private void initMasterBin() {
        UdpPushable fooSink = new UdpPushable() {

            public void push_in(DatagramPacket packet) {
                push_in(packet.getData(), packet.getLength());
            }

            public void push_in(byte[] data, int size) {
                System.out.println("Master income : "+Arrays.toString(data));
            }
        };
        
        SocketAddress peerAddress = new InetSocketAddress("127.0.0.1", slavePort);
        UdpPushChannel channel = new UdpPushChannel(fooSink);
        masterBin = new UdpBin(
                peerAddress, masterPort, channel, buffer.length, "masterBin");
    }

    private void initSlaveBin() {
        UdpPushable fooSink = new UdpPushable() {

            public void push_in(DatagramPacket packet) {
                slaveBin.push_in(packet);
            }

            public void push_in(byte[] data, int size) {
                slaveBin.push_in(data, size);
            }
        };
        
        SocketAddress peerAddress = new InetSocketAddress("127.0.0.1", masterPort);
        UdpPushChannel channel = new UdpPushChannel(fooSink);
        slaveBin = new UdpBin(
                peerAddress, slavePort, channel, buffer.length, "slaveBin");
    }

    @Test
    public void testUdpBin() throws InterruptedException {
        slaveBin.start();
        masterBin.start();

        masterBin.push_in(buffer, buffer.length);
        
        Thread.sleep(100l);

        slaveBin.stop();
        masterBin.stop();
    }
}
