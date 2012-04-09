package talkingnet.audiodevices;

import talkingnet.audiodevices.util.FooMixerHolder;
import talkingnet.core.io.channel.PullChannel;
import talkingnet.utils.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooAudioSink extends AudioSink{

    public FooAudioSink(int bufferLength, PullChannel channel_in, String title) {
        super(FooMixerHolder.getMixer(),
                new DefaultAudioFormat(),
                bufferLength, channel_in, title);
    }    
}
