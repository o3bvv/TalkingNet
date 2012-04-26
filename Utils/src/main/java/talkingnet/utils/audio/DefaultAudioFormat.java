package talkingnet.utils.audio;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioFormat extends AudioFormat {

    public final static int SAMPLING_RATE = 16000;
    public final static int FRAME_SIZE    = 2;
    
    public DefaultAudioFormat() {
        super(AudioFormat.Encoding.PCM_SIGNED,
                SAMPLING_RATE,
                Byte.SIZE*FRAME_SIZE, 1, FRAME_SIZE,
                SAMPLING_RATE, true);
    }    
}
