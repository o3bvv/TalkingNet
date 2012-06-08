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
            Mixer mixer = AudioSystem.getMixer(infos[i]);
            result.add(mixer);
        }
        return result;
    }

    public static Collection<String> getAvailableMixersNames() {
        List<String> result = new ArrayList<String>();
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (int i = 0; i < infos.length; i++) {
            result.add(infos[i].getName());
        }
        return result;
    }

    public static Mixer getMixerByNameOrNull(String name) {
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (int i = 0; i < infos.length; i++) {
            Mixer.Info info = infos[i];
            if (info.getName().equals(name)) {
                return AudioSystem.getMixer(info);
            }
        }
        return null;
    }
}
