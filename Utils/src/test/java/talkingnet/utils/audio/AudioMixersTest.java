package talkingnet.utils.audio;

import java.util.Collection;
import javax.sound.sampled.Mixer;
import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class AudioMixersTest {

    @Test
    public void testGetAvailableMixers() {
        Collection<Mixer> mixers = AudioMixers.getAvailableMixers();
        System.out.println("Available mixers:");
        for (Mixer mixer : mixers) {
            printMixerInfo(mixer.getMixerInfo());
        }
    }

    private void printMixerInfo(Mixer.Info info) {
        System.out.println(
                "\tName:" + info.getName());
        System.out.println(
                "\tDescription:" + info.getDescription());
        System.out.println(
                "\tVendor:" + info.getVendor());
        System.out.println(
                "\tVersion:" + info.getVersion());
    }
}
