package com.kaneki.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaneki.xchatmessageview.XMessageAdapter;
import com.kaneki.xchatmessageview.XViewHolder;
import com.kaneki.xchatmessageview.XChatMessageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private XChatMessageView xChatMessageView;
    private Button buttonAdd;
    private HomeAdapter homeAdapter;

    private ArrayList<Message> mDatas;
    private int[] mIds = {R.layout.msg_list_item_to_text, R.layout.msg_list_item_from_text};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        xChatMessageView = (XChatMessageView) findViewById(R.id.xcmv_view);
        buttonAdd = (Button) findViewById(R.id.btn_add);

        homeAdapter = new HomeAdapter(this, mIds, mDatas);

        xChatMessageView.setMessageAdapter(homeAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xChatMessageView.addNewMessage(new Message(0, "new"));
            }
        });
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