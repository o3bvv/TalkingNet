package talkingnet.utils.audio;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioFormat extends AudioFormat {

    public DefaultAudioFormat() {
        super(AudioFormat.Encoding.PCM_SIGNED,
                16000,
                16, 1, 2,
                16000, true);
    }    
}
