package me.kaneki.sample.event;

import android.view.View;

import me.kaneki.sample.entity.Message;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/5/20
 * @email yueqian@mogujie.com
 */
public class MessageEvent {

    private View itemView;
    private Message message;

    public MessageEvent(Message message, View itemView) {
        this.message = message;
        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
