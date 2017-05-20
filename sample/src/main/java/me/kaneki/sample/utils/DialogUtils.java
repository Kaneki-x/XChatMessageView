package me.kaneki.sample.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author yueqian
 * @Desctription
 * @date 2017/5/20
 * @email yueqian@mogujie.com
 */
public class DialogUtils {

    public static AlertDialog getDeleteDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setTitle("删除");
        normalDialog.setPositiveButton("确定", onClickListener);
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return normalDialog.create();
    }
}
