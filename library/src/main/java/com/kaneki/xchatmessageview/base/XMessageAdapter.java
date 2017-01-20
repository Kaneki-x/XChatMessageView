package com.kaneki.xchatmessageview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaneki.xchatmessageview.R;
import com.kaneki.xchatmessageview.holder.XHeaderHolder;
import com.kaneki.xchatmessageview.holder.XViewHolder;

import java.util.ArrayList;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/1/16
 * @email yueqian@mogujie.com
 */
public abstract class XMessageAdapter<T> extends RecyclerView.Adapter<XViewHolder<T>> {

    private static final int TYPE_LOADING_HEADER = 1000;

    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<T> mDatas;
    private int[] mIds;
    private int headerLayoutId;

    public XMessageAdapter (Context context, int[] mIds, ArrayList<T> mDatas) {
        this.context = context;
        this.mIds = mIds;
        this.mDatas = mDatas;
        this.layoutInflater = LayoutInflater.from(context);
        this.headerLayoutId = R.layout.x_default_header;
    }

    public abstract int getItemViewType(T t);

    public abstract XViewHolder<T> getViewHolder(View itemView, int viewType);

    public void setHeaderLayoutId(int headerLayoutId) {
        this.headerLayoutId = headerLayoutId;
    }

    void addMessageAtLast(T t) {
        int lastIndex = mDatas.size() - 1;
        mDatas.add(t);
        notifyItemInserted(mDatas.size() - 1);
        notifyItemRangeChanged(lastIndex, mDatas.size() - 1);
    }

    void addMessageAtPosition(int pos, T t) {
        mDatas.add(pos, t);
        notifyItemInserted(pos);
        // 加入如下代码保证position的位置正确性
        if (pos != mDatas.size() - 1) {
            notifyItemRangeChanged(pos, mDatas.size() - pos);
        }
    }

    void removeMessageAtPosition(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
        // 加入如下代码保证position的位置正确性
        if (pos != mDatas.size() - 1) {
            notifyItemRangeChanged(pos, mDatas.size() - pos);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_LOADING_HEADER;
        return getItemViewType(mDatas.get(position - 1));
    }

    @Override
    @SuppressWarnings("unchecked")
    public XViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING_HEADER)
            return (XViewHolder<T>) new XHeaderHolder(layoutInflater.inflate(headerLayoutId, parent, false));
        return getViewHolder(layoutInflater.inflate(mIds[viewType], parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(XViewHolder<T> holder, int position) {
        if (position == 0)
            holder.bindView(null);
        else
            holder.bindView(mDatas.get(position - 1));
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

}
