package talkingnet.audiodevices.defaults;

import talkingnet.audiodevices.AudioSource;
import talkingnet.core.io.channel.PushChannel;
import talkingnet.utils.DefaultAudioFormat;
import talkingnet.utils.DefaultMixerHolder;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioSource extends AudioSource {

    public DefaultAudioSource(int bufferLength, PushChannel channel_out, String title) {
        super(DefaultMixerHolder.getMixer(),
                new DefaultAudioFormat(),
                bufferLength, channel_out, title);
    }
}
