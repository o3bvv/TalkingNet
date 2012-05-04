package talkingnet.core.adder.simple;

import talkingnet.core.adder.simple.SimpleAdder8bit;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import talkingnet.core.Element;
import talkingnet.core.foo.sink.FooSink;
import talkingnet.core.io.Multipushable;
import talkingnet.core.io.Multipushing;
import talkingnet.utils.OnesData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class SimpleAdderTest {   

    private int dataLength = 15;
    private int sourcesCount = 5;
    
    private FooSink sink;
    private SimpleAdder8bit adder;
    private MultiOnesSource source;
    
    {
        sink = new FooSink("sink");
        adder = new SimpleAdder8bit(dataLength, sink, "adder");
        source = new MultiOnesSource(dataLength, adder, "multisource");
    }
    
    @Test
    public void testSimpleAdder() {
        source.generateData(sourcesCount);
    }
    
    private class MultiOnesSource extends Element implements Multipushing{

        private Multipushable sink;
        private int bufferSize;
        
        public MultiOnesSource(int bufferSize, Multipushable sink, String title) {
            super(title);
            this.bufferSize = bufferSize;
            this.sink = sink;
        }

        public void multipush_out(Collection<byte[]> data) {
            sink.multipush_in(data);
        }  
        
        public void generateData(int srcCount){
            Collection<byte[]> dataList = new ArrayList<byte[]>();
            for (int i=0; i<srcCount; i++) {
                byte[] data = OnesData.getOnesData(bufferSize);
                dataList.add(data);
            }
            System.out.printf(
                    "Generated %d bytes length data for %d sources.\n",
                    bufferSize, srcCount);
            multipush_out(dataList);
        }
    }
}
