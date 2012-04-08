package talkingnet.audiodevices;

import talkingnet.audiodevices.util.FooAudioFormat;
import talkingnet.audiodevices.util.FooMixerHolder;
import talkingnet.core.io.channel.PushChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooAudioSource extends AudioSource {

    public FooAudioSource(int bufferLength, PushChannel channel_out, String title) {
        super(FooMixerHolder.getMixer(),
                new FooAudioFormat(),
                bufferLength, channel_out, title);
    }
}
