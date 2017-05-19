package me.kaneki.sample.holder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
public class SendTextViewHolder extends XViewHolder<Message> {
    TextView textView;

    public SendTextViewHolder(final View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.tv_msg_list_item_text_to_content);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(itemView.getContext());
                normalDialog.setTitle("删除");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                                //xChatMessageView.reomveMessage(itemView);
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
