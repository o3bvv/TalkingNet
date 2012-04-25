package talkingnet.audiodevices.defaults;

import talkingnet.audiodevices.AudioSink;
import talkingnet.core.io.Pullable;
import talkingnet.utils.audio.DefaultAudioFormat;
import talkingnet.utils.audio.DefaultMixerHolder;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioSink extends AudioSink{

    public DefaultAudioSink(int bufferLength, Pullable src, String title) {
        super(DefaultMixerHolder.getMixer(),
                new DefaultAudioFormat(),
                bufferLength, src, title);
    }    
}
