package com.kaneki.xchatmessageview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    void addNewMessage(T t) {
        int lastIndex = mDatas.size() - 1;
        mDatas.add(t);
        notifyItemInserted(mDatas.size() - 1);
        notifyItemRangeChanged(lastIndex, mDatas.size() - 1);
    }

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
