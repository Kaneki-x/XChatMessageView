package com.kaneki.xchatmessageview.base;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.kaneki.xchatmessageview.listener.OnLoadMoreListener;

import java.util.List;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/1/16
 * @email yueqian@mogujie.com
 */
public class XChatMessageView<T> extends ViewGroup {

    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private XMessageAdapter messageAdpter;
    private OnLoadMoreListener onLoadMoreListener;

    private boolean isLoadMore = false;
    private int lastPosition = 0;
    private int lastOffset = 0;

    public XChatMessageView(Context context) {
        this(context, null);
    }

    public XChatMessageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XChatMessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        initListener();
    }

    /**
     * 计算控件的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // 设置自定义的控件MyViewGroup的大小
        setMeasuredDimension(measureWidth, measureHeight);
    }

    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸

        switch (widthMode) {
            /**
             * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
             * MeasureSpec.AT_MOST。
             *
             *
             * MeasureSpec.EXACTLY是精确尺寸，
             * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid
             * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
             *
             *
             * MeasureSpec.AT_MOST是最大尺寸，
             * 当控件的layout_width或layout_height指定为WRAP_CONTENT时
             * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可
             * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
             *
             *
             * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，
             * 通过measure方法传入的模式。
             */
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;

        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 记录总高度
        int mTotalHeight = 0;
        // 遍历所有子视图
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();

            childView.layout(l, mTotalHeight, measuredWidth, mTotalHeight
                    + measureHeight);

            mTotalHeight += measureHeight;
        }
    }

    private void initView() {
        recyclerView = new RecyclerView(context);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setBackgroundColor(Color.parseColor("#f5f5f5"));
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        addView(recyclerView);
    }

    private void initListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (messageAdpter.isNeedLoadMore())
                    saveCurrent();
            }
        });
    }

    private void saveCurrent() {
        int pos = linearLayoutManager.findFirstVisibleItemPosition();
        if (pos == 0 && !isLoadMore) {
            //获取headerView高度
            View headerView = linearLayoutManager.getChildAt(0);
            //获取可视的第一个view
            View firstView = linearLayoutManager.getChildAt(1);
            if (headerView != null && firstView != null) {
                //获取与该view的顶部的偏移量
                lastOffset = firstView.getTop() + headerView.getHeight();
                //得到该View的数组位置
                lastPosition = linearLayoutManager.getPosition(headerView);
                isLoadMore = true;
                onLoadMoreListener.onLoadMore();
            }
        } else {
            //获取headerView高度
            View currentView = linearLayoutManager.getChildAt(pos);
            if (currentView != null) {
                //获取与该view的顶部的偏移量
                lastOffset = currentView.getTop();
                //得到该View的数组位置
                lastPosition = linearLayoutManager.getPosition(currentView);
            }
        }
    }

    private void resumeSave(int changeSize) {
        linearLayoutManager.scrollToPositionWithOffset(lastPosition + changeSize, lastOffset);
    }

    /**
     * set message adapter, the adpter should extend XMessageAdapter.
     * @param messageAdapter
     */
    public void setMessageAdapter(XMessageAdapter messageAdapter) {
        this.messageAdpter = messageAdapter;
        recyclerView.setAdapter(messageAdapter);
        recyclerView.scrollToPosition(messageAdpter.getItemCount() - 1);
    }

    /**
     * set message load more listener, it calls when the header is visibile and only
     * calls once when trigger.
     * @param onLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * get message adpater, it may return null if setMessageAdapter method whit null set.
     * @return
     */
    public XMessageAdapter getMessageAdpter() {
        return messageAdpter;
    }

    /**
     * toggle the load more header, it should be call before the datas change.
     * @param isNeedLoadMore
     */
    public void setIsNeedLoadMore(boolean isNeedLoadMore) {
        messageAdpter.setNeedLoadMore(isNeedLoadMore);
    }

    /**
     * return the view's position on the XChatMessageView, the view should come from the XViewHolder.
     * @param view
     * @return
     */
    public int getMessageItemPosition(View view) {
        return linearLayoutManager == null ? -1 : linearLayoutManager.getPosition(view);
    }

    /**
     * add a new message at the last of the XChatMessageView, the message should as same as the T of th
     * XViewHolder or XMessageAdapter.
     * @param t
     */
    @SuppressWarnings("unchecked")
    public void addMessageAtLast(T t) {
        messageAdpter.addMessageAtLast(t);
        recyclerView.scrollToPosition(messageAdpter.getItemCount() - 1);
    }

    @SuppressWarnings("unchecked")
    public void addMoreMessageAtLast(List<T> tList) {
        messageAdpter.addMoreMessageAtLast(tList);
        recyclerView.scrollToPosition(messageAdpter.getItemCount() - 1);
    }

    @SuppressWarnings("unchecked")
    public void addMoreMessageAtFirst(List<T> tList) {
        messageAdpter.addMoreMessageAtFirst(tList);
        if (!messageAdpter.isNeedLoadMore())
            resumeSave(tList.size());
        isLoadMore = false;
    }

    public void reomveMessage(View view) {
        int pos = linearLayoutManager.getPosition(view);
        messageAdpter.removeMessageAtPosition(pos);
    }

    public void scrollToBottom() {
        recyclerView.scrollToPosition(messageAdpter.getItemCount() - 1);
    }

    public void saveCurrentStatus() {
        saveCurrent();
    }

    public void resumeSaveStatus() {
        resumeSave(0);
    }
}
