package ua.cn.stu.cs.talkingnet.core.foo.sink;

import java.sql.Timestamp;
import java.util.Arrays;
import ua.cn.stu.cs.talkingnet.core.Element;
import ua.cn.stu.cs.talkingnet.core.io.Pushable;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public class FooSink extends Element implements Pushable{

    public FooSink(String title) {
        super(title);
    }

    public void push_in(byte[] data, int length) {
        System.out.printf("[%s] %s\n", 
                new Timestamp(System.currentTimeMillis()), title);
        System.out.printf("\tTaking up data. Size: %d.\n", length);
        System.out.printf("\tData: ");
        System.out.println(Arrays.toString(data));
    }
}
