package talkingnet.net.udp;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import org.junit.Test;
import talkingnet.core.Element;
import talkingnet.core.foo.sink.FooSink;
import talkingnet.core.io.channel.PushChannel;
import talkingnet.net.udp.channel.UdpPushChannel;
import talkingnet.net.udp.io.UdpPushing;
import talkingnet.utils.random.RandomData;
import talkingnet.utils.random.RandomNumbers;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class UdpDataFilterTest {

    private UdpDataFilter filter;
    private FooSink sink;
    private PushChannel filterToSink;
    private UdpPushChannel srcToFilter;
    private RandomAddressAndDataUdpSource source;
    private InetSocketAddress[] addresses;

    @Test
    public void testUdpDataFilter() throws InterruptedException {
        init();
        source.start();
        Thread.sleep(15);
        source.stop();
    }

    private void init() {
        initAddresses();
        initChain();
    }

    private void initAddresses() {
        addresses = new InetSocketAddress[10];
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = new InetSocketAddress(
                    "127.0.0." + (i+1),
                    RandomNumbers.getRandom(9999));
        }
    }

    private void initChain() {
        sink = new FooSink("sink");
        filterToSink = new PushChannel(sink);
        
        initFilter();
        
        srcToFilter = new UdpPushChannel(filter);
        source = new RandomAddressAndDataUdpSource(srcToFilter, "src");
    }
    
    private void initFilter(){
        InetSocketAddress address = getRandomAddress();
        filter = new UdpDataFilter(address, filterToSink, "filter");
        System.out.printf("! Filtering address [%s:%d].\n\n",
                            address.getHostString(), address.getPort());
    }

    private InetSocketAddress getRandomAddress() {
        return addresses[RandomNumbers.getRandom(addresses.length)];
    }

    private class RandomAddressAndDataUdpSource extends Element implements UdpPushing {

        private UdpPushChannel channel_out;
        private GeneratingThread thread;
        private boolean runThread = false;

        public RandomAddressAndDataUdpSource(UdpPushChannel channel_out, String title) {
            super(title);
            this.channel_out = channel_out;
        }

        public void push_out(DatagramPacket packet) {
            channel_out.write(packet);
        }

        public void start() {
            if (thread != null) {
                return;
            }

            thread = new GeneratingThread();
            thread.start();
        }

        public void stop() {
            runThread = false;
            thread = null;
        }

        private class GeneratingThread extends Thread {

            @Override
            public void run() {
                runThread = true;
                while (runThread) {
                    generateAndPushPacketsForAllAddresses();                    
                }
            }

            private void generateAndPushPacketsForAllAddresses() {
                for (int i = 0; i < addresses.length; i++) {
                    if (runThread == false) {
                        return;
                    }
                    
                    InetSocketAddress address = getRandomAddress();
                    byte[] data = RandomData.getRandomData(20);
                    
                    System.out.printf(
                            "Packet with address [%s:%d] generated.\n", 
                            address.getHostString(), address.getPort());
                    
                    try {
                        DatagramPacket packet = new DatagramPacket(data, data.length, address);
                        push_out(packet);
                    } catch (SocketException ex) {
                        System.out.println(title + ": " + ex);
                    }
                }
            }
        }
    }
}
