package com.kaneki.xchatmessageview.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kaneki.xchatmessageview.R;
import com.kaneki.xchatmessageview.holder.XHeaderHolder;
import com.kaneki.xchatmessageview.holder.XViewHolder;

import java.util.List;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/2/8
 * @email yueqian@mogujie.com
 */
public class XMessageApaterHeaderWrapper<T> extends RecyclerView.Adapter<XViewHolder<T>> {

    private static final int TYPE_LOADING_HEADER = 1000;

    private XMessageAdapter xMessageAdapter;
    private int headerLayoutId;
    private XHeaderHolder xHeaderHolder;
    private boolean isNeedLoadMore;

    void setNeedLoadMore(boolean needLoadMore) {
        isNeedLoadMore = needLoadMore;
    }

    boolean isNeedLoadMore() {
        return isNeedLoadMore;
    }

    @SuppressWarnings("unchecked")
    void addMessageAtLast(T t) {
       xMessageAdapter.addMessageAtLast(t);
    }

    @SuppressWarnings("unchecked")
    void addMoreMessageAtLast(List<T> tList) {
        xMessageAdapter.addMoreMessageAtLast(tList);
    }

    @SuppressWarnings("unchecked")
    void addMoreMessageAtFirst(List<T> tList) {
       xMessageAdapter.addMoreMessageAtFirst(tList, isNeedLoadMore);
    }

    void removeMessageAtPosition(int position) {
        xMessageAdapter.removeMessageAtPosition(position, isNeedLoadMore);
    }

    public XMessageApaterHeaderWrapper(XMessageAdapter<T> xMessageAdapter) {
        this.xMessageAdapter = xMessageAdapter;
        this.headerLayoutId = R.layout.x_default_header;
        this.isNeedLoadMore = true;
    }

    public XMessageApaterHeaderWrapper(XMessageAdapter<T> xMessageAdapter, int headerLayoutId, XHeaderHolder xHeaderHolder) {
        this.xMessageAdapter = xMessageAdapter;
        this.headerLayoutId = headerLayoutId;
        this.xHeaderHolder = xHeaderHolder;
        this.isNeedLoadMore = true;
    }

    @Override
    public int getItemViewType(int position) {
        return isNeedLoadMore ? (position == 0 ? TYPE_LOADING_HEADER : xMessageAdapter.getItemViewType(position - 1)) : xMessageAdapter.getItemViewType(position);
    }

    @SuppressWarnings("unchecked")
    @Override
    public XViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == TYPE_LOADING_HEADER ?
                (xHeaderHolder == null ? new XHeaderHolder(LayoutInflater.from(xMessageAdapter.getContext()).inflate(headerLayoutId, parent, false)) {
                    @Override
                    public void bindHeaderView(Object object) {

                    }
                } : xHeaderHolder) : xMessageAdapter.onCreateViewHolder(parent, viewType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(XViewHolder<T> holder, int position) {
        if (isNeedLoadMore) {
            if (position == 0)
                xMessageAdapter.bindHeaderViewByHeader((XHeaderHolder) holder, position);
            else
                xMessageAdapter.bindViewByHeader(holder, position - 1);
        } else
            xMessageAdapter.bindViewByHeader(holder, position);
    }

    @Override
    public int getItemCount() {
        return isNeedLoadMore ? xMessageAdapter.getItemCount() + 1 : xMessageAdapter.getItemCount();
    }
}
