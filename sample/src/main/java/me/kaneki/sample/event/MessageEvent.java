package me.kaneki.sample.event;

import android.view.View;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/5/20
 * @email yueqian@mogujie.com
 */
public class MessageEvent {

    private View itemView;

    public MessageEvent(View itemView) {
        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }
}
