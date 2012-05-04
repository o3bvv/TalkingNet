package talkingnet.utils.io;

import java.util.Arrays;
import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class ConcurrentCircularBufferTest {

    private final static int DATA_SIZE = 5;
    private final static int BUFFER_CAPACITY = 5;
    
    private int dataValue;
    
    ConcurrentCircularBuffer<byte[]> buffer =
            new ConcurrentCircularBuffer<byte[]>(byte[].class, BUFFER_CAPACITY);

    @Test
    public void testConcurrentCircularBuffer() {
        fillAndPrint(3);
        clearAndPrint();
        fillAndPrint(1);
        
        System.out.println("Data obtained:");
        System.out.println(Arrays.toString(buffer.removeAndGetOrGetNull()));
        
        System.out.println("Data obtained:");
        System.out.println(Arrays.toString(buffer.removeAndGetOrGetNull()));
        
        fillAndPrint(0.7f);
        
        System.out.println("Data obtained:");
        System.out.println(Arrays.toString(buffer.removeAndGetOrGetNull()));
        
        clearAndPrint();
    }

    private void fillAndPrint(float times) {
        System.out.println("... Filling buffer");
        for (int i = 0; i < Math.ceil(BUFFER_CAPACITY*times); i++) {
            byte[] data = new byte[DATA_SIZE];

            for (int j = 0; j < data.length; j++) {
                data[j] = (byte) dataValue;
            }

            buffer.add(data);
            dataValue++;
            printBuffer();
        }
    }
    
    private void clearAndPrint(){
        System.out.println("... Clearing buffer");
        byte[] data = buffer.removeAndGetOrGetNull();
        while (data != null){
            System.out.println("Data obtained:");
            System.out.println(Arrays.toString(data));
            printBuffer();
            data = buffer.removeAndGetOrGetNull();
        }
    }

    private void printBuffer() {
        System.out.println("Buffer value:");

        byte[][] snapshot = buffer.snapshot();
        
        for (int i = 0; i < snapshot.length; i++) {
            System.out.println(Arrays.toString(snapshot[i]));
        }
    }
}
