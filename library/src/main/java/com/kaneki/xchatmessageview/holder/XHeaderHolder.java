package com.kaneki.xchatmessageview.holder;

import android.view.View;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/1/19
 * @email yueqian@mogujie.com
 */
public abstract class XHeaderHolder extends XViewHolder<Object> {

    public XHeaderHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(Object object) {
        bindHeaderView(object);
    }

    public abstract void bindHeaderView(Object object);
}
