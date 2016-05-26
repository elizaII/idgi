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

public class LoginRequiredDialog extends Dialog {


    public LoginRequiredDialog(final Context context) {
        super(context);

        View contentView = getContentView(context, R.layout.dialog_login_required);
        setContentView(contentView);

        TextView txtLogin = (TextView) contentView.findViewById(R.id.dialog_login_required_login_text_view);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ApplicationBus.post(ActivityType.LOGIN);
                dismiss();
            }
        });

        setStandardLayout();
    }
    private void setStandardLayout() {
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private View getContentView(Context context, int resource) {
        LayoutInflater inflater = LayoutInflater.from(context);

        return inflater.inflate(resource, null);
    }

}
