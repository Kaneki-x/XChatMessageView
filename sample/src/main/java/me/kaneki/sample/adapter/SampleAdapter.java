package me.kaneki.sample.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import me.kaneki.sample.R;
import me.kaneki.sample.activity.MainActivity;
import me.kaneki.sample.entity.Message;
import me.kaneki.sample.holder.ReceiveTextViewHolder;
import me.kaneki.sample.holder.SendTextViewHolder;
import me.kaneki.xchatmessageview.anno.XItemLayoutRes;
import me.kaneki.xchatmessageview.base.XMessageAdapter;
import me.kaneki.xchatmessageview.holder.XViewHolder;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/5/19
 * @email yueqian@mogujie.com
 */
@XItemLayoutRes({
        R.layout.msg_list_item_to_text,
        R.layout.msg_list_item_from_text
})
public class SampleAdapter extends XMessageAdapter<Message> {

    public SampleAdapter(Context context, ArrayList<Message> mDatas) {
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


