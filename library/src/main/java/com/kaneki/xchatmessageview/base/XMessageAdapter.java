package com.kaneki.xchatmessageview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaneki.xchatmessageview.anno.XItemLayoutResResolver;
import com.kaneki.xchatmessageview.holder.XHeaderHolder;
import com.kaneki.xchatmessageview.holder.XViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/1/16
 * @email yueqian@mogujie.com
 */
public abstract class XMessageAdapter<T> extends RecyclerView.Adapter<XViewHolder<T>> {

    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<T> mDatas;
    private int[] mIds;

    public XMessageAdapter (Context context, ArrayList<T> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        this.mIds = XItemLayoutResResolver.resolve(this);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public abstract int getItemViewType(T t);

    public abstract XViewHolder<T> getViewHolder(View itemView, int viewType);

    void addMessageAtLast(T t) {
        mDatas.add(t);
        notifyItemInserted(getItemCount());
    }

    void addMoreMessageAtLast(List<T> tList) {
        mDatas.addAll(tList);
        notifyItemInserted(getItemCount());
    }

    void addMoreMessageAtFirst(List<T> tList, boolean isNeedLoadMore) {
        mDatas.addAll(0, tList);
        notifyItemRangeInserted(isNeedLoadMore ? 1 : 0, tList.size());
        notifyItemRangeChanged(tList.size() + (isNeedLoadMore ? 1 : 0), getItemCount() - tList.size());
    }

    void removeMessageAtPosition(int position, boolean isNeedLoadMore) {
        int realIndex = isNeedLoadMore ? position - 1 : position;
        mDatas.remove(realIndex);
        notifyItemRemoved(position);
        // 加入如下代码保证position的位置正确性
        if (realIndex != getItemCount() - 1) {
            notifyItemRangeChanged(realIndex, getItemCount() - realIndex);
        }
    }

    Context getContext() {
        return context;
    }

    void bindViewByHeader(XViewHolder<T> holder, int position) {
        holder.bindView(mDatas.get(position));
    }

    void bindHeaderViewByHeader(XHeaderHolder holder, int position) {
        holder.bindView(mDatas.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(mDatas.get(position));
    }

    @Override
    @SuppressWarnings("unchecked")
    public XViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(layoutInflater.inflate(mIds[viewType], parent, false), viewType);
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
