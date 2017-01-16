package com.kaneki.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kaneki.xchatmessageview.adapter.XMessageAdapter;
import com.kaneki.xchatmessageview.holder.XViewHolder;
import com.kaneki.xchatmessageview.view.XChatMessageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private XChatMessageView xChatMessageView;
    private ArrayList<Message> mDatas;
    private int[] mIds = {R.layout.msg_list_item_to_text, R.layout.msg_list_item_from_text};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        xChatMessageView = (XChatMessageView) findViewById(R.id.xcmv_view);
        xChatMessageView.setMessageAdapter(new HomeAdapter(this, mIds, mDatas));

    }

    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            if (i % 2 == 0)
                mDatas.add(new Message(0, (char) i + ""));
            else
                mDatas.add(new Message(1, (char) i + ""));
        }
    }

    class Message {
        int type;
        String content;

        public Message(int type, String content) {
            this.type = type;
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public String getContent() {
            return content;
        }
    }

    class HomeAdapter extends XMessageAdapter<Message> {

        public HomeAdapter(Context context, int[] mIds, ArrayList<Message> mDatas) {
            super(context, mIds, mDatas);
        }

        @Override
        public int getItemViewType(Message message) {
            return message.getType();
        }

        @Override
        public XViewHolder<Message> getViewHolder(View itemView, int viewType) {
            if (viewType == 0)
                return new SendTextViewHolder(itemView);
            else if (viewType == 1)
                return new ReceiveTextViewHolder(itemView);
            else
                return null;
        }
    }

    class SendTextViewHolder extends XViewHolder<Message> {
        TextView textView;

        public SendTextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_msg_list_item_text_to_content);
        }

        @Override
        public void bindView(Message message) {
            textView.setText(message.getContent());
        }
    }

    class ReceiveTextViewHolder extends XViewHolder<Message> {
        TextView textView;


        public ReceiveTextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_msg_list_item_text_from_content);
        }

        @Override
        public void bindView(Message message) {
            textView.setText(message.getContent());
        }
    }
}
