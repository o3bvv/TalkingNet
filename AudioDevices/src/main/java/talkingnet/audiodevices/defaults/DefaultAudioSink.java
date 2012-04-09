package talkingnet.audiodevices.defaults;

import talkingnet.audiodevices.AudioSink;
import talkingnet.core.io.channel.PullChannel;
import talkingnet.utils.DefaultAudioFormat;
import talkingnet.utils.DefaultMixerHolder;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioSink extends AudioSink{

    public DefaultAudioSink(int bufferLength, PullChannel channel_in, String title) {
        super(DefaultMixerHolder.getMixer(),
                new DefaultAudioFormat(),
                bufferLength, channel_in, title);
    }    
}
