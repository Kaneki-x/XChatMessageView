package me.kaneki.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.kaneki.sample.R;
import me.kaneki.sample.adapter.SampleAdapter;
import me.kaneki.sample.entity.Message;
import me.kaneki.sample.event.MessageEvent;
import me.kaneki.sample.utils.SharedPreferencesUtils;
import me.kaneki.xchatmessageview.XChatMessageView;
import me.kaneki.xchatmessageview.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PAGE_COUNT = 10;

    private int lastPosition;

    private XChatMessageView<Message> xChatMessageView;
    private SampleAdapter sampleAdapter;
    private Gson gson;

    private Button buttonRecvImg;
    private Button buttonRecvText;
    private Button buttonSendImg;
    private Button buttonSendText;

    private ArrayList<Message> mDatas;
    private ArrayList<Message> localDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        initLocalData();
        initView();
        initChatMessageView();
        addListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferencesUtils.setParam(this, "data", gson.toJson(localDatas));
    }

    private void initLocalData() {
        gson = new Gson();
        mDatas = new ArrayList<>();

        String localStr = (String) SharedPreferencesUtils.getParam(this, "data", "");

        localDatas = gson.fromJson(localStr == null ? "" : localStr , new TypeToken<List<Message>>(){}.getType());

        if (localDatas == null)
            localDatas = new ArrayList<>();

        lastPosition = localDatas.size() - 1;
        mDatas.addAll(getDataFromLocal());
    }

    @SuppressWarnings("unchecked")
    private void initView() {
        xChatMessageView = (XChatMessageView) findViewById(R.id.xcmv_home);
        buttonRecvImg = (Button) findViewById(R.id.btn_recv_img);
        buttonSendImg = (Button) findViewById(R.id.btn_send_img);
        buttonRecvText = (Button) findViewById(R.id.btn_recv_text);
        buttonSendText = (Button) findViewById(R.id.btn_send_text);
    }

    private void initChatMessageView() {
        sampleAdapter = new SampleAdapter(this, mDatas);

        xChatMessageView.setMessageAdapter(sampleAdapter);

        if (mDatas.size() == PAGE_COUNT) {
            xChatMessageView.scrollToBottom();
            xChatMessageView.setIsNeedHeaderLoadMore(true);
            xChatMessageView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onHeaderLoadMore() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //模拟加载
                                Thread.sleep(500);
                                final ArrayList<Message> messageArrayList = getDataFromLocal();
                                xChatMessageView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (messageArrayList.isEmpty())
                                            xChatMessageView.setIsNeedHeaderLoadMore(false);
                                        xChatMessageView.addMoreMessageAtFirst(messageArrayList);
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

                @Override
                public void onFooterLoadMore() {
                }
            });
        }
    }

    private void addListeners() {
        buttonRecvImg.setOnClickListener(this);
        buttonSendImg.setOnClickListener(this);
        buttonRecvText.setOnClickListener(this);
        buttonSendText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Message message;
        switch (v.getId()) {
            case R.id.btn_recv_img:
                message = new Message(true, "", Message.TYPE_IMG);
                break;
            case R.id.btn_send_img:
                message = new Message(false, "", Message.TYPE_IMG);
                break;
            case R.id.btn_send_text:
                message = new Message(false, "send text", Message.TYPE_TEXT);
                break;
            case R.id.btn_recv_text:
                message = new Message(true, "recv text", Message.TYPE_TEXT);
                break;
            default:
                message = new Message(false, "error", Message.TYPE_TEXT);
        }
        localDatas.add(message);
        xChatMessageView.addMessageAtLast(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        xChatMessageView.reomveMessage(event.getItemView());

        localDatas.remove(event.getMessage());
    }

    private ArrayList<Message> getDataFromLocal() {
        if (!localDatas.isEmpty()) {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            for (int i = lastPosition; i >= 0 && lastPosition - i < PAGE_COUNT; i--) {
                messageArrayList.add(0, localDatas.get(i));
            }
            lastPosition = lastPosition - messageArrayList.size();
            return messageArrayList;
        } else {
            return new ArrayList<>();
        }
    }
}