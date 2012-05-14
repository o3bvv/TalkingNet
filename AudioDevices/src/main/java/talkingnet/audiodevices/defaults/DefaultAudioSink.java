package talkingnet.audiodevices.defaults;

import talkingnet.audiodevices.AudioSink;
import talkingnet.core.io.Pullable;
import talkingnet.utils.audio.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioSink extends AudioSink{

    public DefaultAudioSink(int bufferLength, Pullable src, String title) {
        super(null,
              new DefaultAudioFormat(),
              bufferLength, src, title);
    }    
}
