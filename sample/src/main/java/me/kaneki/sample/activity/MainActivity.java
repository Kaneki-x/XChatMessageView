package me.kaneki.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import me.kaneki.sample.R;
import me.kaneki.sample.adapter.SampleAdapter;
import me.kaneki.sample.entity.Message;
import me.kaneki.xchatmessageview.base.XChatMessageView;
import me.kaneki.xchatmessageview.listener.OnLoadMoreListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private XChatMessageView<Message> xChatMessageView;
    private Button buttonAdd;
    private SampleAdapter sampleAdapter;

    int i = 0;

    private ArrayList<Message> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        xChatMessageView = (XChatMessageView) findViewById(R.id.xcmv_view);
        buttonAdd = (Button) findViewById(R.id.btn_add);

        sampleAdapter = new SampleAdapter(getApplicationContext(), mDatas);

        xChatMessageView.setMessageAdapter(sampleAdapter);
        xChatMessageView.setIsNeedFooterLoadMore(false);
        xChatMessageView.setIsNeedHeaderLoadMore(false);
        //xChatMessageView.scrollToBottom();

        xChatMessageView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onHeaderLoadMore() {
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
                                    xChatMessageView.setIsNeedHeaderLoadMore(false);
                                    xChatMessageView.addMoreMessageAtFirst(list);
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                }).start();
            }

            @Override
            public void onFooterLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            final ArrayList<Message> list = new ArrayList<>();

                            for (int i = 0; i< 10; i++) {
                                if (i % 2 == 0)
                                    list.add(new Message(0, "next--" + i));
                                else
                                    list.add(new Message(1, "next--" + i));
                            }

                            xChatMessageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    xChatMessageView.setIsNeedFooterLoadMore(false);
                                    xChatMessageView.addMoreMessageAtLast(list);
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
                xChatMessageView.scrollToBottom();

                if (i == 10)
                    xChatMessageView.setIsNeedHeaderLoadMore(true);
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
}