package me.kaneki.sample.holder;

import android.view.View;
import android.widget.TextView;

import me.kaneki.sample.R;
import me.kaneki.sample.entity.Message;
import me.kaneki.xchatmessageview.holder.XViewHolder;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/5/19
 * @email yueqian@mogujie.com
 */
public class ReceiveTextViewHolder extends XViewHolder<Message> {
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
