package ua.cn.stu.cs.talkingnet.examples.simpleconference;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import ua.cn.stu.cs.talkingnet.audio.devices.defaults.DefaultAudioSink;
import ua.cn.stu.cs.talkingnet.audio.devices.defaults.DefaultAudioSource;
import ua.cn.stu.cs.talkingnet.codecs.ulaw.ULawCompressor;
import ua.cn.stu.cs.talkingnet.codecs.ulaw.ULawDecompressor;
import ua.cn.stu.cs.talkingnet.core.Pool;
import ua.cn.stu.cs.talkingnet.core.Pump;
import ua.cn.stu.cs.talkingnet.core.vad.VAD16bit;
import ua.cn.stu.cs.talkingnet.net.udp.UdpBin;
import ua.cn.stu.cs.talkingnet.net.udp.UdpDataFilter;
import ua.cn.stu.cs.talkingnet.utils.audio.DefaultAudioFormat;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class Client {

    private int bufferLengthInMillis = 20;
    private int bufferLength;
    private DefaultAudioSource src;
    private Pump pump;
    private VAD16bit vad;
    private ULawCompressor compressor;
    private UdpBin udpBin;
    private UdpDataFilter filter;
    private ULawDecompressor decompressor;
    private Pool pool;
    private DefaultAudioSink sink;

    public Client(SocketAddress localAddress, SocketAddress remoteAddress, float threshold) throws SocketException {

        bufferLength = DefaultAudioFormat.SAMPLING_RATE * DefaultAudioFormat.FRAME_SIZE;
        bufferLength /= (1000 / bufferLengthInMillis);
        bufferLength -= bufferLength % 2;

        pool = new Pool(2, "pool");
        sink = new DefaultAudioSink(bufferLength, pool, "audioSink");
        //decompressor = new ULawDecompressor(pool, "decompressor");
        //filter = new UdpDataFilter(remoteAddress, decompressor, "filter");
        filter = new UdpDataFilter(remoteAddress, pool, "filter");

        int udpDataLength = bufferLength;// >> 1;
        udpBin = new UdpBin(localAddress, remoteAddress, filter, udpDataLength, "udpBin");

        //compressor = new ULawCompressor(udpBin, "compressor");
        //vad = new VAD16bit(bufferLengthInMillis, compressor, "vad");
        vad = new VAD16bit(bufferLengthInMillis, udpBin, "vad");
        pump = new Pump(2, vad, "pump");
        src = new DefaultAudioSource(bufferLength, pump, "audioSrc");

        vad.setThreshold(threshold);

        runPipeLine();
    }

    private void runPipeLine() {
        try {
            sink.open();
            src.open();

            pump.start();
            sink.start();

            udpBin.start();

            src.start();
        } catch (Exception ex) {
            System.out.println(ex);
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

        port = Integer.parseInt(args[3]);
        SocketAddress remoteAddress = new InetSocketAddress(args[2], port);

        float threshold = 0.81f;

        if (args.length > 4) {
            try {
                threshold = Float.parseFloat(args[4]);
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }
        try {
            new Client(localAddress, remoteAddress, threshold);
        } catch (SocketException ex) {
            System.out.println(ex);
        }
    }

    private static void validateArgs(String[] args) {

        if (args.length < 4) {
            System.out.println("Not enough parameters! You must provide following:");
            System.out.println("\tlocal_address local_port remote address remote_port");
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

        tmp = args[2];

        try {
            InetAddress.getByName(tmp);
        } catch (Exception e) {
            System.out.println("'remote_address' value is invalid: " + tmp);
            argsValidationFail = true;
        }

        tmp = args[3];

        try {
            Integer.parseInt(tmp);
        } catch (Exception e) {
            System.out.println("'remote_port' value is invalid: " + tmp);
            argsValidationFail = true;
        }
    }
}
