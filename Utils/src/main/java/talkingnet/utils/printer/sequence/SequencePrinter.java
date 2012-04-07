package talkingnet.utils.printer.sequence;

import java.io.PrintStream;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public abstract class SequencePrinter {
    
    protected char startBorder = '[';
    protected char stopBorder  = ']';
    
    protected PrintStream stream;

    public SequencePrinter() {
        this.stream = System.out;
    }
    
    public SequencePrinter(PrintStream stream) {
        this.stream = stream;
    }
    
    protected void start(){
        stream.print(startBorder);
    }    
    
    protected void stop(){
        stream.print(stopBorder);        
    }
    
    protected void done(){
        stream.flush();
    }
    
    protected void newLine(){
        stream.println();        
    }
}
