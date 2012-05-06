package talkingnet.net.rtp;

/**
 * @author Alexander Oblovatniy <oblovatniy@gmail.com> <br/>Original:
 * @see
 * http://code.google.com/p/mynpr/source/browse/trunk/mynpr/src/com/webeclubbin/mynpr/RTPpacket.java
 */
public class RTPpacket {

    public static int HEADER_SIZE = 12;
    
    public byte[] header;
    public int payload_size;
    public byte[] payload;
    
    public int Version = 2;
    public int Padding = 0;
    public int Extension = 0;
    public int CC = 0;
    public int Marker = 0;
    public int Ssrc = 0;
    public int PayloadType;
    public int SequenceNumber;
    public int TimeStamp;

    /**
     * RTP packet from raw data
     */
    public RTPpacket(int PType, int Framenb, int Time, byte[] data, int data_length) {
        SequenceNumber = Framenb;
        TimeStamp      = Time;
        PayloadType    = PType;
        payload_size   = data_length;

        createAndFillHeader();
        
        payload = new byte[payload_size];
        
        System.arraycopy(data, 0, payload, 0, data_length);
    }

    private void createAndFillHeader() {
        header = new byte[HEADER_SIZE];

        header[0] = (byte) (Version << 6);
        header[0] = (byte) (header[0] | Padding << 5);
        header[0] = (byte) (header[0] | Extension << 4);
        header[0] = (byte) (header[0] | CC);
        header[1] = (byte) (header[1] | Marker << 7);
        header[1] = (byte) (header[1] | PayloadType);
        header[2] = (byte) (SequenceNumber >> 8);
        header[3] = (byte) (SequenceNumber & 0xFF);
        header[4] = (byte) (TimeStamp >> 24);
        header[5] = (byte) (TimeStamp >> 16);
        header[6] = (byte) (TimeStamp >> 8);
        header[7] = (byte) (TimeStamp & 0xFF);
        header[8] = (byte) (Ssrc >> 24);
        header[9] = (byte) (Ssrc >> 16);
        header[10] = (byte) (Ssrc >> 8);
        header[11] = (byte) (Ssrc & 0xFF);
    }
    
    /**
     * RTP packet from UDP data
     */
    public RTPpacket(byte[] packet, int packet_size) {
        //check if total packet size is lower than the header size
        if (packet_size >= HEADER_SIZE) {
            //get the header bitsream:
            header = new byte[HEADER_SIZE];
            System.arraycopy(packet, 0, header, 0, HEADER_SIZE);

            //get the payload bitstream:
            payload_size = packet_size - HEADER_SIZE;
            payload = new byte[payload_size];
            for (int i = HEADER_SIZE; i < packet_size; i++) {
                payload[i - HEADER_SIZE] = packet[i];
            }

            //interpret the changing fields of the header:
            PayloadType = header[1] & 127;
            SequenceNumber = unsigned_int(header[3]) + 256 * unsigned_int(header[2]);
            TimeStamp = unsigned_int(header[7]) + 256 * unsigned_int(header[6]) + 65536 * unsigned_int(header[5]) + 16777216 * unsigned_int(header[4]);
        }
    }

    //--------------------------
    //getpayload: return the payload bistream of the RTPpacket and its size
    //--------------------------
    public int getpayload(byte[] data) {
        System.arraycopy(payload, 0, data, 0, payload_size);
        return (payload_size);
    }

    //--------------------------
    //getpayload_length: return the length of the payload
    //--------------------------
    public int getpayload_length() {
        return (payload_size);
    }

    //--------------------------
    //getlength: return the total length of the RTP packet
    //--------------------------
    public int getlength() {
        return (payload_size + HEADER_SIZE);
    }

    //--------------------------
    //getpacket: returns the packet bitstream and its length
    //--------------------------
    public int getpacket(byte[] packet) {
        //construct the packet = header + payload
        System.arraycopy(header, 0, packet, 0, HEADER_SIZE);
        System.arraycopy(payload, 0, packet, HEADER_SIZE, payload_size);

        //return total size of the packet
        return (payload_size + HEADER_SIZE);
    }

    //--------------------------
    //gettimestamp
    //--------------------------
    public int gettimestamp() {
        return (TimeStamp);
    }

    //--------------------------
    //getsequencenumber
    //--------------------------
    public int getsequencenumber() {
        return (SequenceNumber);
    }

    //--------------------------
    //getpayloadtype
    //--------------------------
    public int getpayloadtype() {
        return (PayloadType);
    }

    //--------------------------
    //print headers without the SSRC
    //--------------------------
    public void printheader() {
        final String TAG = "printheader ";

        for (int i = 0; i < (HEADER_SIZE - 4); i++) {

            for (int j = 7; j >= 0; j--) {
                if (((1 << j) & header[i]) != 0) {
                    System.out.println(TAG + "1");
                } else {
                    System.out.println(TAG + "0");
                }
            }
        }

    }

    //return the unsigned value of 8-bit integer nb
    private static int unsigned_int(int nb) {

        if (nb >= 0) {
            return (nb);
        } else {
            return (256 + nb);
        }
    }
}