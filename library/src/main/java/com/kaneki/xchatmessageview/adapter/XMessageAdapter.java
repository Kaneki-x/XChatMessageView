package com.kaneki.xchatmessageview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaneki.xchatmessageview.holder.XViewHolder;

import java.util.ArrayList;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/1/16
 * @email yueqian@mogujie.com
 */
public abstract class XMessageAdapter<T> extends RecyclerView.Adapter<XViewHolder<T>> {

    private Context context;

    private ArrayList<T> mDatas;
    private int[] mIds;

    public XMessageAdapter (Context context, int[] mIds, ArrayList<T> mDatas) {
        this.context = context;
        this.mIds = mIds;
        this.mDatas = mDatas;
    }

    public abstract int getItemViewType(T t);

    public abstract XViewHolder<T> getViewHolder(View itemView, int viewType);

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(mDatas.get(position));
    }

    @Override
    public XViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context).inflate(mIds[viewType], parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(XViewHolder<T> holder, int position) {
        holder.bindView(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
