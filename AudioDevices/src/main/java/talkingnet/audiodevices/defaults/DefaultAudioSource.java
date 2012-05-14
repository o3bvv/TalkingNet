package talkingnet.audiodevices.defaults;

import talkingnet.audiodevices.AudioSource;
import talkingnet.core.io.Pushable;
import talkingnet.utils.audio.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioSource extends AudioSource {

    public DefaultAudioSource(int bufferLength, Pushable sink, String title) {
        super(null,
              new DefaultAudioFormat(),
              bufferLength, sink, title);
    }
}
