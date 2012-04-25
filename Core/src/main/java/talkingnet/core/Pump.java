package talkingnet.core;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import talkingnet.core.io.Pushable;
import talkingnet.core.io.Pushing;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class Pump extends Element implements Pushable, Pushing {

    private PushChannel channel_out;
    private final ConcurrentLinkedQueue<byte[]> queue =
            new ConcurrentLinkedQueue<byte[]>();
    private PumpingThread thread;
    private boolean doProcessing = false;
    private boolean stopForced = false;
    private boolean stopped = false;

    public Pump(PushChannel channel_out, String title) {
        super(title);
        this.channel_out = channel_out;
    }

    @Override
    public void push_in(byte[] data, int size) {        
        queue.add(data);
        synchronized (queue){
            queue.notifyAll();
        }        
    }

    @Override
    public void push_out(byte[] data, int size) {
        try {
            channel_out.write(data, size);
        } catch (IOException ex) {
            System.out.println(title + ": " + ex);
        }
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
                
                byte[] data = queue.poll();
                push_out(data, data.length);
            }
            stopped = true;
        }
    }

    public synchronized boolean isStopped() {
        return stopped;
    }
}