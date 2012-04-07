package talkingnet.utils.printer.sequence;

import java.io.PrintStream;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public abstract class SequencePrinter<T> {
    
    protected char startBorder = '[';
    protected char stopBorder  = ']';
    
    protected PrintStream stream;

    public SequencePrinter() {
        this.stream = System.out;
    }
    
    public SequencePrinter(PrintStream stream) {
        this.stream = stream;
    }
    
    public void print(T sequence){
        printUnflushed(sequence);
        done();
    }
    
    public void printLn(T sequence){
        printUnflushed(sequence);
        newLine();
        done();
    }
    
    protected void printUnflushed(T sequence){
        start();        
        printBody(sequence);        
        stop();
    }
    
    protected abstract void printBody(T sequence);
    
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
