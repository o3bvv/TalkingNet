package talkingnet.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import talkingnet.core.Element;
import talkingnet.net.udp.channel.UdpPushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpSource extends Element implements UdpPushing {

    private PullingThread thread;
    private int bufferLength;
    private DatagramSocket socket;
    private UdpPushChannel channel_out;
    private boolean doProcessing = false;

    public UdpSource(DatagramSocket socket, UdpPushChannel channel, int bufferLength, String title) {
        super(title);
        this.socket = socket;
        this.channel_out = channel;
        this.bufferLength = bufferLength;
    }

    public void start() {
        if (thread != null) {
            return;
        }

        thread = new PullingThread();
        thread.start();
    }

    public void stop() {
        doProcessing = false;
        thread = null;
    }

    @Override
    public void push_out(DatagramPacket packet) {
        channel_out.write(packet);
    }

    private class PullingThread extends Thread {

        @Override
        public void run() {
            doProcessing = true;
            while (doProcessing) {
                byte[] buffer = new byte[bufferLength];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                    push_out(packet);
                } catch (IOException ex) {
                    System.out.println(title + ": " + ex);
                }
            }
        }
    }
}
