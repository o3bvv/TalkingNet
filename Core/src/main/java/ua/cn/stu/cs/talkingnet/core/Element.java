package ua.cn.stu.cs.talkingnet.core;

/**
 *
 * @author Alexander Oblovatniy <oblovatniy@gmail.com>
 */
public abstract class Element {

    protected String title;
    
    protected boolean byPass = false;

    public Element(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isByPass() {
        return byPass;
    }

    public void setByPass(boolean byPass) {
        this.byPass = byPass;
    }
}
