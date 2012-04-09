package talkingnet.audiodevices;

import talkingnet.core.io.channel.PullChannel;
import talkingnet.utils.DefaultAudioFormat;
import talkingnet.utils.DefaultMixerHolder;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooAudioSink extends AudioSink{

    public FooAudioSink(int bufferLength, PullChannel channel_in, String title) {
        super(DefaultMixerHolder.getMixer(),
                new DefaultAudioFormat(),
                bufferLength, channel_in, title);
    }    
}
