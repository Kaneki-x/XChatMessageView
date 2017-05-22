package me.kaneki.sample.holder;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import me.kaneki.sample.R;
import me.kaneki.sample.entity.Message;
import me.kaneki.sample.event.MessageEvent;
import me.kaneki.sample.utils.DialogUtils;
import me.kaneki.xchatmessageview.holder.XViewHolder;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/5/19
 * @email yueqian@mogujie.com
 */
public class ReceiveTextViewHolder extends XViewHolder<Message> {
    private TextView textView;
    private View itemView;

    public ReceiveTextViewHolder(final View itemView) {
        super(itemView);
        this.itemView = itemView;
        textView = (TextView) itemView.findViewById(R.id.tv_msg_list_item_text_from_content);

    }

    @Override
    public void bindView(final Message message) {
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogUtils.getDeleteDialog(itemView.getContext(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new MessageEvent(message, itemView));
                    }
                }).show();
                return true;
            }
        });
        textView.setText(message.getContent());
    }
}
