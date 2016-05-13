package com.idgi.activities.extras;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.Widgets.CommentLayout;
import com.idgi.core.Comment;
import com.idgi.session.SessionData;

import java.util.ArrayList;

public final class DialogFactory {

	public enum Type {
		REPLY_TO_COMMENT, LOGIN_REQUIRED
	}

	private DialogFactory() throws InstantiationException {
		throw new InstantiationException("You can not instantiate this class.");
	}

	public static Dialog createLoginRequiredDialog(final Context context) {
		Dialog dialog = new Dialog(context);

		View contentView = getContentView(context, R.layout.dialog_login_required);
		dialog.setContentView(contentView);

		TextView txtLogin = (TextView) contentView.findViewById(R.id.dialog_login_required_login_text_view);

		txtLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DrawerActivity activity = (DrawerActivity) context;
				activity.startActivity(ActivityType.LOGIN);
			}
		});

		setStandardLayout(dialog);

		return dialog;
	}

	public static Dialog createCommentReplyDialog(final Context context, final CommentLayout parentLayout) {
		final Dialog dialog = new Dialog(context);

		View contentView = getContentView(context, R.layout.dialog_reply_to_comment);
		dialog.setContentView(contentView);

		final Comment parentComment = parentLayout.getComment();

		Button btnReply = (Button) contentView.findViewById(R.id.dialog_reply_to_comment_button_send_reply);
		final EditText txtReply = (EditText) contentView.findViewById(R.id.dialog_reply_to_comment_text_reply);
		btnReply.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String message = txtReply.getText().toString();
				if (message.length() > 0)
					parentComment.addReply(message, SessionData.getLoggedInUser());
				//parentLayout

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
