package ua.cn.stu.cs.talkingnet.utils.audio;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioFormat extends AudioFormat {

    public final static int SAMPLING_RATE  = 16000;
    public final static int SAMPLE_SIZE    = Byte.SIZE*2;
    public final static int CHANNELS       = 1;
    public final static int FRAME_SIZE     = (SAMPLE_SIZE*CHANNELS)/Byte.SIZE;
    public final static boolean BIG_ENDIAN = true;
    
    public DefaultAudioFormat() {
        super(AudioFormat.Encoding.PCM_SIGNED,
                SAMPLING_RATE,
                SAMPLE_SIZE,
                CHANNELS,
                FRAME_SIZE,
                SAMPLING_RATE, BIG_ENDIAN);
    }    
}
