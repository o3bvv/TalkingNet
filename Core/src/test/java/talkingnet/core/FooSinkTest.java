package talkingnet.core;

import org.junit.Test;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooSinkTest {
    
    @Test
    public void testFooSinkTakeUp(){
        byte[] array = {1, 3, 5, 7, 9};
        FooSink sink = new FooSink();
        sink.takeUp(array, array.length);
    }
}
