package ua.cn.stu.cs.talkingnet.core;

import org.junit.Test;
import ua.cn.stu.cs.talkingnet.core.foo.sink.FooSink;
import ua.cn.stu.cs.talkingnet.utils.random.RandomData;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class CopierTest {
    
    private Copier copier = new Copier("copier");
    
    @Test
    public void testCopier() {
        addSinks();
        pushDifferentData();
    }
    
    private void addSinks(){
        for (int i = 0; i < 3; i++) {
            FooSink sink = new FooSink("sink"+i);
            copier.addDestination(sink);
        }
    }
    
    private void pushDifferentData(){
        for (int i = 0; i < 3; i++) {
            byte[] data = RandomData.getRandomData(20);
            copier.push_in(data, data.length);
        }
    }
}
