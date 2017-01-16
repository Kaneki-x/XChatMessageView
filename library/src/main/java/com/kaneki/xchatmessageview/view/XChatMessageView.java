package com.kaneki.xchatmessageview.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/1/16
 * @email yueqian@mogujie.com
 */
public class XChatMessageView extends ViewGroup {

    private Context context;
    private RecyclerView recyclerView;

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
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        recyclerView.layout(l, t, r, b);
    }

    private void initView() {
        recyclerView = new RecyclerView(context);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setBackgroundColor(Color.parseColor("#f5f5f5"));
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        addView(recyclerView);
    }

    public void setMessageAdapter(RecyclerView.Adapter messageAdapter) {
        recyclerView.setAdapter(messageAdapter);
    }
}
