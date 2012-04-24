package talkingnet.utils.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class DefaultMixerHolder {
    
    private static Mixer.Info info;
    
    public static Mixer getMixer() {        
        return AudioSystem.getMixer(getInfo());
    }
    
    private static Mixer.Info getInfo(){
        if (info==null){
            info = AudioSystem.getMixerInfo()[0];
        }
        return info;
    }
}
