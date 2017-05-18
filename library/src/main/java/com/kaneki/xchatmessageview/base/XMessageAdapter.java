package com.kaneki.xchatmessageview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaneki.xchatmessageview.R;
import com.kaneki.xchatmessageview.anno.XItemLayoutResResolver;
import com.kaneki.xchatmessageview.holder.XMoreHolder;
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

    private static final int TYPE_LOADING_HEADER = 1000;
    private static final int TYPE_LOADING_FOOTER = 1001;

    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<T> mDatas;
    private int[] mIds;
    private int headerLayoutId;
    private int footerLayoutId;
    private boolean isNeedHeaderLoadMore;
    private boolean isNeedFooterLoadMore;

    public XMessageAdapter (Context context, ArrayList<T> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        this.mIds = XItemLayoutResResolver.resolve(this);
        this.layoutInflater = LayoutInflater.from(context);
        this.headerLayoutId = R.layout.x_default_load;
        this.footerLayoutId = R.layout.x_default_load;
        this.isNeedHeaderLoadMore = true;
        this.isNeedFooterLoadMore = true;
    }

    public abstract int getItemViewType(T t);

    public abstract XViewHolder<T> getViewHolder(View itemView, int viewType);

    public void setHeaderLayoutId(int headerLayoutId) {
        this.headerLayoutId = headerLayoutId;
    }

    public void setFooterLayoutId(int footerLayoutId) {
        this.footerLayoutId = footerLayoutId;
    }

    boolean isNeedHeaderLoadMore() {
        return isNeedHeaderLoadMore;
    }

    void setNeedHeaderLoadMore(boolean needHeaderLoadMore) {
        isNeedHeaderLoadMore = needHeaderLoadMore;
    }

    boolean isNeedFooterLoadMore() {
        return isNeedFooterLoadMore;
    }

    void setNeedFooterLoadMore(boolean needFooterLoadMore) {
        isNeedFooterLoadMore = needFooterLoadMore;
    }

    void addMessageAtLast(T t) {
        mDatas.add(t);
        notifyItemInserted(getItemCount());
    }

    void addMoreMessageAtLast(List<T> tList) {
        mDatas.addAll(tList);
        notifyItemInserted(getItemCount());
    }

    void addMoreMessageAtFirst(List<T> tList) {
        mDatas.addAll(0, tList);
        notifyItemRangeInserted(isNeedHeaderLoadMore ? 1 : 0, tList.size());
        notifyItemRangeChanged(tList.size() + (isNeedHeaderLoadMore ? 1 : 0), getItemCount() - tList.size());
    }

    void removeMessageAtPosition(int pos) {
        int realIndex = isNeedHeaderLoadMore ? pos - 1 : pos;
        mDatas.remove(realIndex);
        notifyItemRemoved(pos);
        // 加入如下代码保证position的位置正确性
        if (realIndex != getItemCount() - 1) {
            notifyItemRangeChanged(realIndex, getItemCount() - realIndex);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isNeedHeaderLoadMore && position == 0)
            return TYPE_LOADING_HEADER;
        else if (isNeedFooterLoadMore && position == getItemCount() - 1)
            return TYPE_LOADING_FOOTER;
        else if (isNeedHeaderLoadMore)
            return getItemViewType(mDatas.get(position - 1));
        else
            return getItemViewType(mDatas.get(position));
    }

    @Override
    @SuppressWarnings("unchecked")
    public XViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING_HEADER) {
            return (XViewHolder<T>) new XMoreHolder(layoutInflater.inflate(headerLayoutId, parent, false)) {
                @Override
                public void bindMoreView(Object object) {

                }
            };
        } else if (viewType == TYPE_LOADING_FOOTER) {
            return (XViewHolder<T>) new XMoreHolder(layoutInflater.inflate(footerLayoutId, parent, false)) {
                @Override
                public void bindMoreView(Object object) {

                }
            };
        } else {
            return getViewHolder(layoutInflater.inflate(mIds[viewType], parent, false), viewType);
        }
    }

    @Override
    public void onBindViewHolder(XViewHolder<T> holder, int position) {
        if (isNeedHeaderLoadMore && position == 0)
            holder.bindView(null);
        else if (isNeedFooterLoadMore && position == getItemCount() - 1)
            holder.bindView(null);
        else if (isNeedHeaderLoadMore)
            holder.bindView(mDatas.get(position - 1));
        else
            holder.bindView(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (isNeedFooterLoadMore && isNeedHeaderLoadMore)
            return mDatas.size() + 2;
        else if (isNeedHeaderLoadMore || isNeedFooterLoadMore)
            return mDatas.size() + 1;
        else
            return mDatas.size();
    }
}
