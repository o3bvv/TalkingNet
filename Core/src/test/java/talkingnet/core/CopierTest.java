package talkingnet.core;

import org.junit.Test;

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
            byte[] data = getRandomData();
            copier.push_in(data, data.length);
        }
    }
    
    private byte[] getRandomData(){
        byte[] data = new byte[getRandom(20)];
        for (int i = 0; i < data.length; i++) {
            data[i] = getRandomPositiveByte();
        }
        return data;
    }
    
    private int getRandom(int edge){
        return (int) Math.ceil(Math.random()*edge);
    }
    
    private byte getRandomPositiveByte(){
        return (byte) Math.ceil(Math.random()*Byte.MAX_VALUE);
    }
}
