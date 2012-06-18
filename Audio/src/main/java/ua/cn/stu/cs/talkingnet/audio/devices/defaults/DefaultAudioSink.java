package ua.cn.stu.cs.talkingnet.audio.devices.defaults;

import ua.cn.stu.cs.talkingnet.audio.devices.AudioSink;
import ua.cn.stu.cs.talkingnet.core.io.Pullable;
import ua.cn.stu.cs.talkingnet.utils.audio.DefaultAudioFormat;

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
