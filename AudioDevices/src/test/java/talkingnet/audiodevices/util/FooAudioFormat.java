package talkingnet.audiodevices.util;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooAudioFormat extends AudioFormat {

    public FooAudioFormat() {
        super(AudioFormat.Encoding.PCM_SIGNED,
                11025,
                16, 1, 2,
                11025, true);
    }
}
