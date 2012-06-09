package ua.cn.stu.cs.talkingnet.net.udp;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.net.udp.io.UdpPushable;
import ua.cn.stu.cs.talkingnet.net.udp.io.UdpPushing;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpCopier extends Element implements UdpPushable, UdpPushing {

    private List<UdpPushable> dstList = new ArrayList<UdpPushable>();
    
    public UdpCopier(String title) {
        super(title);
    }

    public void push_in(DatagramPacket packet) {
        push_out(packet);
    }

    public void push_in(byte[] data, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void push_out(DatagramPacket packet) {
        for (UdpPushable dst : dstList) {
            dst.push_in(packet);
        }
    }
    
    public synchronized void addDestination(UdpPushable dst){
        dstList.add(dst);
    }
    
    public synchronized void removeDestination(UdpPushable dst){
        dstList.remove(dst);
    }
}
