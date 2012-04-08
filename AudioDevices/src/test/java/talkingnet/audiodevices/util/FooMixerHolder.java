package talkingnet.audiodevices.util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooMixerHolder {

    private static Mixer mixer;

    public static Mixer getMixer() {
        if (mixer==null){
            createMixer();
        }
        return mixer;
    }
    
    public static void createMixer() {
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(infos[0]);
    }
}
