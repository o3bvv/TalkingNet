package talkingnet.audiodevices.defaults;

import talkingnet.audiodevices.AudioSource;
import talkingnet.core.io.Pushable;
import talkingnet.utils.audio.DefaultAudioFormat;
import talkingnet.utils.audio.DefaultMixerHolder;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioSource extends AudioSource {

    public DefaultAudioSource(int bufferLength, Pushable sink, String title) {
        super(DefaultMixerHolder.getMixer(),
                new DefaultAudioFormat(),
                bufferLength, sink, title);
    }
}
