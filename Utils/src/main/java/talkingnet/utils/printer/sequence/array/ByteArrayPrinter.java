package talkingnet.utils.printer.sequence.array;

import talkingnet.utils.printer.sequence.SequencePrinter;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class ByteArrayPrinter extends ArrayPrinter<Byte> {
    
    @Override
    protected void printBody(Byte[] array){
        stream.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            stream.printf(", %d", array[i]);
        }
    }
}
