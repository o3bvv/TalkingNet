package talkingnet.utils.audio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioMixers {
 
    public static Collection<Mixer> getAvailableMixers() {
        List<Mixer> result = new ArrayList<Mixer>();
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (int i = 0; i < infos.length; i++) {
            try {
                Mixer mixer = AudioSystem.getMixer(infos[i]);
                result.add(mixer);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return result;
    }
}
