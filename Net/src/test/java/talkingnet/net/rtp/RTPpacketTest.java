package talkingnet.net.rtp;

import java.util.Arrays;
import org.junit.Test;
import talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class RTPpacketTest {    

    static final int PAYLOAD_TYPE = 20;
    static final int DATA_LENGTH  = 30;
    
    byte[] dataTx;
    byte[] dataRx;    
    byte[] dataPacket;
    
    RTPpacket packetTx;
    RTPpacket packetRx;
    
    @Test
    public void testRTPpacket() {
        initRawDataTx();
        createPacketTx();
        createPacketRx();
    }
    
    private void initRawDataTx(){
        dataTx = RandomData.getRandomDataFixedLength(DATA_LENGTH);        
        System.out.println("Data Tx:");
        System.out.println(Arrays.toString(dataTx));
    }
    
    private void createPacketTx(){
        packetTx = new RTPpacket(PAYLOAD_TYPE, 100, 200, dataTx, dataTx.length);
        
        System.out.println("Packet Tx:");
        System.out.println("Header:");
        System.out.println(Arrays.toString(packetTx.header));
        
        System.out.println("All data:");
        
        dataPacket = new byte[RTPpacket.HEADER_SIZE+dataTx.length];                
        packetTx.getPacket(dataPacket);        
        System.out.println(Arrays.toString(dataPacket));
    }
    
    private void createPacketRx(){
        packetRx = new RTPpacket(dataPacket, dataPacket.length);
        
        System.out.println("Packet Rx:");
        System.out.println("Header:");
        System.out.println(Arrays.toString(packetRx.header));
        
        System.out.println("Data Tx:");
        System.out.println(Arrays.toString(packetRx.payload));
    }
}
