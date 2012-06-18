package ua.cn.stu.cs.talkingnet.audio.vad;

import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.core.io.Pushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class VAD16bit extends Element implements Pushable, Pushing {

    private int holdTime = 2500;
    private int bufferLength;
    private int holdCounter;
    private float threshold = 0;
    private int maxLevel = Short.MAX_VALUE;
    private float sum;
    private short sample;
    private float currentLevel;
    private Pushable sink;

    public VAD16bit(int bufferLength, Pushable sink, String title) {
        super(title);
        this.bufferLength = bufferLength;
        this.sink = sink;
    }

    public void push_in(byte[] data, int size) {
        if (isLevelReached(data)) {
            holdCounter = 0;
        } else {
            if (holdCounter >= holdTime) {
                return;
            }
            holdCounter += bufferLength;
        }

        push_out(data, size);
    }

    private boolean isLevelReached(byte[] data) {
        sum = 0;

        for (int i = 0; i < data.length; i += 2) {
            sample = (short) ((data[i + 1] << Byte.SIZE) + data[i]);
            sum += ((float) Math.abs(sample)) / maxLevel;
        }

        currentLevel = (float) (sum / data.length);
        
        return (1-currentLevel) >= threshold;
    }

    public void push_out(byte[] data, int size) {
        sink.push_in(data, size);
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(int holdTime) {
        this.holdTime = holdTime;
    }
}
