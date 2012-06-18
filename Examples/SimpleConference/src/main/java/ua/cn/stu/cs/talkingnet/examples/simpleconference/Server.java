package ua.cn.stu.cs.talkingnet.examples.simpleconference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import ua.cn.stu.cs.talkingnet.codecs.ulaw.ULawCompressor;
import ua.cn.stu.cs.talkingnet.core.Copier;
import ua.cn.stu.cs.talkingnet.core.Pump;
import ua.cn.stu.cs.talkingnet.core.PushingMultipool;
import ua.cn.stu.cs.talkingnet.core.adder.simple.SimpleAdder16bit;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;
import ua.cn.stu.cs.talkingnet.net.udp.*;
import ua.cn.stu.cs.talkingnet.utils.audio.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class Server {

    private int bufferLengthInMillis = 20;
    private int bufferLength;
    
    private List<InetSocketAddress> clientsAddresses;
    
    private UdpBin udpBin;
    private UdpPump udpSinkPump;
    private UdpCopier udpPacketsCopier;
    private PushingMultipool multipool;
    private SimpleAdder16bit adder;
    private Pump adderPump;
    private ULawCompressor compressor;
    private Copier compressedDataCopier;
    

    public Server(SocketAddress localAddress) throws SocketException {
        
        getClientsAddresses();

        if (clientsAddresses.isEmpty()) {
            System.out.println("No client's addresses were specified!");
            return;
        } else {
            System.out.printf(
                    "%d client(s) are known now.\n",
                    clientsAddresses.size());
        }

        bufferLength = DefaultAudioFormat.SAMPLING_RATE * DefaultAudioFormat.FRAME_SIZE;
        bufferLength /= (1000/bufferLengthInMillis);
        bufferLength -= bufferLength % 2;
        
        udpPacketsCopier = new UdpCopier("udp_packets_copier");
        
        compressedDataCopier = new Copier("compressed_data_copier");       
        //compressor = new ULawCompressor(compressedDataCopier, "compressor");        
        
        int udpDataLength = bufferLength;// >> 1;
        udpBin = new UdpBin(localAddress, udpPacketsCopier, udpDataLength, "udpBin");
        udpSinkPump = new UdpPump(udpBin, "udpSinkPump");
        
        //adderPump = new Pump(1, compressor, "adderPump");
        adderPump = new Pump(2, compressedDataCopier, "adderPump");
        adder = new SimpleAdder16bit(bufferLength, adderPump, "adder");
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

            //ULawDecompressor decompressor =
            //        new ULawDecompressor(sink, "decompressor"+addressStr);
            
            //Pump pump = new Pump(1, decompressor, "pump"+addressStr);
            Pump pump = new Pump(2, sink, "pump"+addressStr);
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
        
        try {
            new Server(localAddress);
        } catch (SocketException ex) {
            System.out.println(ex);
        }
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
