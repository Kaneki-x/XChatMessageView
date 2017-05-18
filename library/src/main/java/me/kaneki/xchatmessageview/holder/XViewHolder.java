package me.kaneki.xchatmessageview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/1/16
 * @email yueqian@mogujie.com
 */
public abstract class XViewHolder<T> extends RecyclerView.ViewHolder {

    public XViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(T t);
}
