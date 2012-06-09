package ua.cn.stu.cs.talkingnet.audiodevices.defaults;

import ua.cn.stu.cs.talkingnet.audiodevices.AudioSource;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.utils.audio.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultAudioSource extends AudioSource {

    public DefaultAudioSource(int bufferLength, Pushable sink, String title) {
        super(null,
              new DefaultAudioFormat(),
              bufferLength, sink, title);
    }
}
