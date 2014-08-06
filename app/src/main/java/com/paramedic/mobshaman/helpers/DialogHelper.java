package com.paramedic.mobshaman.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.interfaces.AlertListener;

/**
 * Created by maxo on 31/07/14.
 */
public class DialogHelper {

    public static void getConfirmDialog(Context mContext,String title, String msg, String positiveBtnCaption, String negativeBtnCaption, boolean isCancelable, final AlertListener target) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(title).setMessage(msg).setCancelable(false).setPositiveButton(positiveBtnCaption, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                target.PositiveMethod(dialog, id);
            }
        }).setNegativeButton(negativeBtnCaption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                target.NegativeMethod(dialog, id);
            }
        });

        AlertDialog alert = builder.create();
        alert.setIcon(R.drawable.ic_action_about);
        alert.setCancelable(isCancelable);
        alert.show();
        if (isCancelable) {
            alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    target.NegativeMethod(null, 0);
                }
            });
        }
    }

}
