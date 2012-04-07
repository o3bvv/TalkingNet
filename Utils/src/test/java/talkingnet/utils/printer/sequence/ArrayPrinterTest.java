package talkingnet.utils.printer.sequence;

import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class ArrayPrinterTest {
    
    private final static int ARRAY_LENGTH = 10;
    
    private byte[] array;
    private ArrayPrinter printer;

    public ArrayPrinterTest() {
        initArray();
        printer = new ArrayPrinter();
    }
    
    private void initArray(){
        array = new byte[ARRAY_LENGTH];
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
