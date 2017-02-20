package com.kaneki.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaneki.xchatmessageview.anno.XItemLayoutRes;
import com.kaneki.xchatmessageview.base.XMessageAdapter;
import com.kaneki.xchatmessageview.base.XMessageApaterHeaderWrapper;
import com.kaneki.xchatmessageview.holder.XViewHolder;
import com.kaneki.xchatmessageview.base.XChatMessageView;
import com.kaneki.xchatmessageview.listener.OnLoadMoreListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private XChatMessageView<Message> xChatMessageView;
    private Button buttonAdd;
    private HomeAdapter homeAdapter;

    int i = 0;

    private ArrayList<Message> mDatas;
    private int[] mIds = {R.layout.msg_list_item_to_text, R.layout.msg_list_item_from_text};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        xChatMessageView = (XChatMessageView) findViewById(R.id.xcmv_view);
        buttonAdd = (Button) findViewById(R.id.btn_add);

        homeAdapter = new HomeAdapter(this, mDatas);

        xChatMessageView.setMessageAdapter(new XMessageApaterHeaderWrapper<>(homeAdapter));

        xChatMessageView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            final ArrayList<Message> list = new ArrayList<>();

                            for (int i = 0; i< 10; i++) {
                                if (i % 2 == 0)
                                    list.add(new Message(0, "previous--" + i));
                                else
                                    list.add(new Message(1, "previous--" + i));
                            }

                            xChatMessageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    xChatMessageView.setIsNeedLoadMore(false);
                                    xChatMessageView.addMoreMessageAtFirst(list);
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                }).start();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xChatMessageView.addMessageAtLast(new Message(0, "new " + i++));
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

    @XItemLayoutRes({
            R.layout.msg_list_item_to_text,
            R.layout.msg_list_item_from_text
    })
    class HomeAdapter extends XMessageAdapter<Message> {

        public HomeAdapter(Context context, ArrayList<Message> mDatas) {
            super(context, mDatas);
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

        public SendTextViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_msg_list_item_text_to_content);
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(MainActivity.this);
                    normalDialog.setTitle("删除");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                    xChatMessageView.reomveMessage(itemView);
                                }
                            });
                    normalDialog.setNegativeButton("关闭",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                }
                            });
                    // 显示
                    normalDialog.show();
                    return true;
                }
            });
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