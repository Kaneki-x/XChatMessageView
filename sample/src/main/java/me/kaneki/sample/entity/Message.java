package me.kaneki.sample.entity;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/5/19
 * @email yueqian@mogujie.com
 */
public class Message {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMG = 1;

    private boolean isFrom;
    private String content;
    private int type;

    public Message(boolean isFrom, String content, int type) {
        this.isFrom = isFrom;
        this.content = content;
        this.type = type;
    }

    public boolean isFrom() {
        return isFrom;
    }

    public void setFrom(boolean from) {
        isFrom = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
