package talkingnet.audiodevices;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Mixer;
import talkingnet.audiodevices.util.FooAudioFormat;
import talkingnet.audiodevices.util.FooMixerHolder;
import talkingnet.core.io.channel.PullChannel;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooAudioSink extends AudioSink{

    public FooAudioSink(int bufferLength, PullChannel channel_in, String title) {
        super(
                FooMixerHolder.getMixer(),
                new FooAudioFormat(),
                bufferLength, channel_in, title);
    }    
}
