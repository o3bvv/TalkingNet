package talkingnet.examples.simpleconference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.List;
import talkingnet.codecs.Compressor;
import talkingnet.codecs.speex.SpeexCompressor;
import talkingnet.codecs.speex.SpeexDecompressor;
import talkingnet.core.Copier;
import talkingnet.core.Pump;
import talkingnet.core.PushingMultipool;
import talkingnet.core.adder.SimpleAdder;
import talkingnet.core.io.Pushable;
import talkingnet.net.udp.*;
import talkingnet.utils.audio.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class Server {

    private int bufferLengthInMillis = 48;
    private int bufferLength;
    
    private List<InetSocketAddress> clientsAddresses;
    
    private UdpBin udpBin;
    private UdpPump udpSinkPump;
    private UdpCopier udpPacketsCopier;
    private PushingMultipool multipool;
    private SimpleAdder adder;
    private Pump adderPump;
    private Compressor compressor;
    private Copier compressedDataCopier;
    
    private int mode = 0;
    private int quality = 3;
    private int channels = 1;
    private int sampleRate = 16000;    
    private boolean enhance = false;

    public Server(SocketAddress localAddress) {
        
        getClientsAddresses();

        if (clientsAddresses.isEmpty()) {
            System.out.println("No client's addresses were specified!");
            return;
        } else {
            System.out.printf(
                    "%d client(s) are known now.\n",
                    clientsAddresses.size());
        }

        udpPacketsCopier = new UdpCopier("udp_packets_copier");
        
        compressedDataCopier = new Copier("compressed_data_copier");
        
        compressor = new SpeexCompressor("compressor");
        ((SpeexCompressor)compressor).init(mode, quality, sampleRate, channels);
        ((SpeexCompressor)compressor).setSink(compressedDataCopier);
        
        bufferLength = DefaultAudioFormat.SAMPLING_RATE * DefaultAudioFormat.FRAME_SIZE;
        bufferLength /= (1000/bufferLengthInMillis);
        bufferLength -= bufferLength % ((SpeexCompressor)compressor).getRawFrameSize();
        ((SpeexCompressor)compressor).setBufferSize(bufferLength);
        
        int udpDataLength = ((SpeexCompressor)compressor).getResultSize();
        udpBin = new UdpBin(localAddress, udpPacketsCopier, udpDataLength, "udpBin");
        udpSinkPump = new UdpPump(udpBin, "udpSinkPump");
        
        adderPump = new Pump(compressor, "adderPump");
        adder = new SimpleAdder(bufferLength, adderPump, "adder");
        multipool = new PushingMultipool(bufferLength, adder, "multipool");
        
        createClientDependentElements();
        runPipeLine();
    }
    
    private void createClientDependentElements(){
        UdpDataWrapper wrapper;
        UdpDataFilter filter;
        
        for (InetSocketAddress address : clientsAddresses) {
            String addressStr = "_"+address.getHostName()+"_"+address.getPort();
            
            wrapper = new UdpDataWrapper(
                    address, udpSinkPump, "wrapper"+addressStr);
            compressedDataCopier.addDestination(wrapper);
            
            Pushable sink = multipool.getNewSink();            

            SpeexDecompressor decompressor = new SpeexDecompressor(
                    ((SpeexCompressor)compressor).getEncodedFrameSize(),
                    bufferLength,
                    "decompressor"+addressStr);            
            ((SpeexDecompressor)decompressor).init(mode, sampleRate, channels, enhance);
            ((SpeexDecompressor)decompressor).setSink(sink);
            
            Pump pump = new Pump(decompressor, "pump"+addressStr);            
            filter = new UdpDataFilter(address, pump, "filter"+addressStr);
            
            udpPacketsCopier.addDestination(filter);
            
            pump.start();
        }
    }
    
    private void runPipeLine(){
        udpBin.start();
        udpSinkPump.start();
        multipool.start();
        adderPump.start();
    }

    private void getClientsAddresses() {
        clientsAddresses = new LinkedList<InetSocketAddress>();

        System.out.println("Enter client's addresses in following format: ");
        System.out.println("\tremote_address remote_port");
        System.out.println("Enter empty string when you have done.");

        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);

        String line;

        while (true) {
            try {
                line = in.readLine();
            } catch (IOException ex) {
                System.out.println(ex);
                break;
            }

            if (line.isEmpty()) {
                break;
            }

            String[] tokens = line.split(" ");
            
            if (tokens.length<2){
                System.out.println("Not enought data.");
                continue;
            }
            
            try {
                int port = Integer.parseInt(tokens[1]);
                InetSocketAddress address = new InetSocketAddress(tokens[0], port);
                clientsAddresses.add(address);
                System.out.printf(
                        "Address was added [%s:%d]. List size: %d.\n",
                        address.getHostString(),
                        address.getPort(),
                        clientsAddresses.size());
            } catch (Exception e) {
                System.out.println("Invalid address format!");
                continue;
            }
        }
    }
    private static boolean argsValidationFail = false;

    public static void main(String[] args) {
        validateArgs(args);
        if (argsValidationFail) {
            return;
        }

        int port = Integer.parseInt(args[1]);
        SocketAddress localAddress = new InetSocketAddress(args[0], port);

        new Server(localAddress);
    }

    private static void validateArgs(String[] args) {

        if (args.length < 2) {
            System.out.println("Not enough parameters! You must provide following:");
            System.out.println("\tlocal_address local_port");
            argsValidationFail = true;
            return;
        }

        String tmp = args[0];

        try {
            InetAddress.getByName(tmp);
        } catch (Exception e) {
            System.out.println("'local_address' value is invalid: " + tmp);
            argsValidationFail = true;
        }

        tmp = args[1];

        try {
            Integer.parseInt(tmp);
        } catch (Exception e) {
            System.out.println("'local_port' value is invalid: " + tmp);
            argsValidationFail = true;
        }
    }
}
