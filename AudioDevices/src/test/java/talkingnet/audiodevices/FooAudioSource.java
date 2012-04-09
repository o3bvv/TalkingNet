package talkingnet.audiodevices;

import talkingnet.core.io.channel.PushChannel;
import talkingnet.utils.DefaultAudioFormat;
import talkingnet.utils.DefaultMixerHolder;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooAudioSource extends AudioSource {

    public FooAudioSource(int bufferLength, PushChannel channel_out, String title) {
        super(DefaultMixerHolder.getMixer(),
                new DefaultAudioFormat(),
                bufferLength, channel_out, title);
    }
}
