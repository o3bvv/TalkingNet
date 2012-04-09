package talkingnet.utils;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioFormat extends AudioFormat {

    public DefaultAudioFormat() {
        super(AudioFormat.Encoding.PCM_SIGNED,
                11025,
                16, 1, 2,
                11025, true);
    }    
}
