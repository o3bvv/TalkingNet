package talkingnet.audiodevices.defaults;

import talkingnet.audiodevices.AudioSink;
import talkingnet.core.io.channel.PullChannel;
import talkingnet.utils.audio.DefaultAudioFormat;
import talkingnet.utils.audio.DefaultMixerHolder;

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
