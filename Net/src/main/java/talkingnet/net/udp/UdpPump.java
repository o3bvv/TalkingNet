package talkingnet.net.udp;

import java.net.DatagramPacket;
import java.util.concurrent.ConcurrentLinkedQueue;
import talkingnet.core.Element;
import talkingnet.net.udp.io.UdpPushable;
import talkingnet.net.udp.io.UdpPushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpPump extends Element implements UdpPushable, UdpPushing {

    private UdpPushable sink;
    private final ConcurrentLinkedQueue<DatagramPacket> queue =
            new ConcurrentLinkedQueue<DatagramPacket>();
    private PumpingThread thread;
    private boolean doProcessing = false;
    private boolean stopForced = false;
    private boolean stopped = false;

    public UdpPump(UdpPushable sink, String title) {
        super(title);
        this.sink = sink;
    }
    
    public void push_in(DatagramPacket packet) {
        queue.add(packet);
        synchronized (queue){
            queue.notifyAll();
        }  
    }

    public void push_in(byte[] data, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void push_out(DatagramPacket packet) {
        sink.push_in(packet);
    }
    
    public void start() {
        if (thread != null) {
            return;
        }

        thread = new PumpingThread();
        thread.start();
        synchronized (queue){
            queue.notifyAll();
        }
    }

    public void stopForced() {
        stopForced = true;
        stop();
    }
    
    public void stop() {
        doProcessing = false;
        thread = null;
        synchronized (queue){
            queue.notifyAll();
        }
    }

    private class PumpingThread extends Thread {

        @Override
        public void run() {
            stopped = false;
            doProcessing = true;
            stopForced = false;
            while (true) {
                synchronized (queue) {
                    try {
                        while (queue.isEmpty() && doProcessing) {
                            queue.wait();
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                }                                

                if (stopForced==true) {
                    queue.clear();
                    break;
                } else if(doProcessing==false && queue.isEmpty()) {
                    break;
                }
                
                DatagramPacket packet = queue.poll();
                push_out(packet);
            }
            stopped = true;
        }
    }

    public synchronized boolean isStopped() {
        return stopped;
    }
}
