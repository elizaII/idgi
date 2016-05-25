package com.idgi.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.android.ActivityType;
import com.idgi.event.ApplicationBus;

/**
 * Created by tove on 2016-05-25.
 */
public class LoginRequiredDialog {
    public static Dialog createLoginRequiredDialog(final Context context) {
        final Dialog dialog = new Dialog(context);

        View contentView = getContentView(context, R.layout.dialog_login_required);
        dialog.setContentView(contentView);

        TextView txtLogin = (TextView) contentView.findViewById(R.id.dialog_login_required_login_text_view);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ApplicationBus.post(ActivityType.LOGIN);
                dialog.dismiss();
            }
        });

        setStandardLayout(dialog);

        return dialog;
    }
    private static void setStandardLayout(Dialog dialog) {
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private static View getContentView(Context context, int resource) {
        LayoutInflater inflater = LayoutInflater.from(context);

        return inflater.inflate(resource, null);
    }

}
