package ua.cn.stu.cs.talkingnet.net.udp.io;

import java.net.DatagramPacket;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public interface UdpPushable extends Pushable {

    void push_in(DatagramPacket packet);
}
