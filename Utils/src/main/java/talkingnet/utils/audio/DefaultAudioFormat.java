package talkingnet.utils.audio;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioFormat extends AudioFormat {

    public final static int SAMPLING_RATE = 16000;
    
    public DefaultAudioFormat() {
        super(AudioFormat.Encoding.PCM_SIGNED,
                SAMPLING_RATE,
                16, 1, 2,
                SAMPLING_RATE, true);
    }    
}
