package talkingnet.utils.printer.sequence.array;

import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class ByteArrayPrinterTest {
    
    private final static int ARRAY_LENGTH = 10;
    
    private Byte[] array;
    private ByteArrayPrinter printer;

    public ByteArrayPrinterTest() {
        initArray();
        printer = new ByteArrayPrinter();
    }
    
    private void initArray(){
        array = new Byte[ARRAY_LENGTH];
        for (byte i = 0; i < array.length; i++) {
            array[i] = i;
        }
    }
    
    @Test
    public void testPrint() {        
        printer.print(array);
    }
    
    @Test
    public void testPrintLn() {        
        printer.printLn(array);
    }    
}
