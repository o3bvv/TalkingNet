package talkingnet.utils.printer.sequence;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class ArrayPrinter extends SequencePrinter {
    
    public void print(byte[] array){
        printUnflushed(array);
        done();
    }
    
    public void printLn(byte[] array){
        printUnflushed(array);
        newLine();
        done();
    }
    
    protected void printUnflushed(byte[] array){
        start();        
        printBody(array);        
        stop();
    }
    
    protected void printBody(byte[] array){
        stream.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            stream.printf(", %d", array[i]);
        }
    }
}
