package me.kaneki.sample.adapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import me.kaneki.sample.R;
import me.kaneki.sample.entity.Message;
import me.kaneki.sample.holder.ReceiveImageViewHolder;
import me.kaneki.sample.holder.ReceiveTextViewHolder;
import me.kaneki.sample.holder.SendImageViewHolder;
import me.kaneki.sample.holder.SendTextViewHolder;
import me.kaneki.xchatmessageview.anno.XItemLayoutRes;
import me.kaneki.xchatmessageview.XMessageAdapter;
import me.kaneki.xchatmessageview.holder.XViewHolder;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/5/19
 * @email yueqian@mogujie.com
 */
@XItemLayoutRes({
        R.layout.msg_list_item_to_text,
        R.layout.msg_list_item_from_text,
        R.layout.msg_list_item_to_img,
        R.layout.msg_list_item_from_img
})
public class SampleAdapter extends XMessageAdapter<Message> {

    public SampleAdapter(Context context, ArrayList<Message> mDatas) {
        super(context, mDatas);
    }

    @Override
    public int getItemViewType(Message message) {
        switch (message.getType()) {
            case Message.TYPE_TEXT:
                return message.isFrom() ? 1 : 0;
            case Message.TYPE_IMG:
                return message.isFrom() ? 3 : 2;
            default:
                return -1;
        }
    }

    @Override
    public XViewHolder<Message> getViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case 0:
                return new SendTextViewHolder(itemView);
            case 1:
                return new ReceiveTextViewHolder(itemView);
            case 2:
                return new SendImageViewHolder(itemView);
            case 3:
                return new ReceiveImageViewHolder(itemView);
            default:
                return null;
        }
    }
}


