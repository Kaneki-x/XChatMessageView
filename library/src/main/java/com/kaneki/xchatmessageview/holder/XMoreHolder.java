package com.kaneki.xchatmessageview.holder;

import android.view.View;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/1/19
 * @email yueqian@mogujie.com
 */
public abstract class XMoreHolder extends XViewHolder<Object> {

    public XMoreHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(Object object) {
        bindMoreView(object);
    }

    public abstract void bindMoreView(Object object);
}
