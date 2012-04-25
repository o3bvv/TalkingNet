package talkingnet.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import talkingnet.net.udp.io.UdpPushable;
import talkingnet.net.udp.io.UdpPushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpSource extends UdpElement implements UdpPushing {

    private PullingThread thread;
    private int bufferLength;
    private UdpPushable sink;
    private boolean doProcessing = false;

    public UdpSource(DatagramSocket socket, UdpPushable sink, int bufferLength, String title) {
        super(socket, title);
        this.socket = socket;
        this.sink = sink;
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
        sink.push_in(packet);
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
