package ua.cn.stu.cs.talkingnet.core;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public abstract class Element {

    protected String title;

    public Element(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
