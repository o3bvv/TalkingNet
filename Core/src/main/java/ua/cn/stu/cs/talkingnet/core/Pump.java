package ua.cn.stu.cs.talkingnet.core;

import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushing;
import ua.cn.stu.cs.talkingnet.utils.io.ConcurrentCircularBuffer;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class Pump extends Element implements Pushable, Pushing {

    private Pushable sink;
    
    private final ConcurrentCircularBuffer<byte[]> buffer;
    
    private PumpingThread thread;
    private boolean doProcessing = false;
    private boolean stopForced = false;
    private boolean stopped = false;

    public Pump(int capacity, Pushable sink, String title) {
        super(title);
        this.sink = sink;
        buffer = new ConcurrentCircularBuffer<byte[]>(byte[].class, capacity);
    }

    @Override
    public void push_in(byte[] data, int size) {
        synchronized (buffer){
            buffer.add(data);
            buffer.notifyAll();
        }        
    }

    @Override
    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }

    public void start() {
        if (thread != null) {
            return;
        }

        thread = new PumpingThread();
        thread.start();
        synchronized (buffer){
            buffer.notifyAll();
        }
    }

    public void stopForced() {
        stopForced = true;
        stop();
    }
    
    public void stop() {
        doProcessing = false;
        thread = null;
        synchronized (buffer){
            buffer.notifyAll();
        }
    }

    private class PumpingThread extends Thread {

        @Override
        public void run() {
            stopped = false;
            doProcessing = true;
            stopForced = false;
            while (true) {
                synchronized (buffer) {
                    try {
                        while (buffer.isEmpty() && doProcessing) {
                            buffer.wait();
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                }                                

                if (stopForced==true) {
                    buffer.clear();
                    break;
                } else if(doProcessing==false && buffer.isEmpty()) {
                    break;
                }
                
                byte[] data = buffer.removeAndGetOrGetNull();
                if (data!=null){
                    push_out(data, data.length);
                }
            }
            stopped = true;
        }
    }

    public synchronized boolean isStopped() {
        return stopped;
    }
}
