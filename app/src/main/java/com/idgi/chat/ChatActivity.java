package com.idgi.chat;

import android.content.Context;
import android.content.pm.PackageInstaller;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.android.activity.DrawerActivity;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.Date;

import static com.idgi.chat.MessageHandler.*;

/**
 * Created by tove on 2016-08-03.
 */

public class ChatActivity extends DrawerActivity implements View.OnClickListener, MessagesListener {

    public static final String TAG = ChatActivity.class.getSimpleName();
    public static final String USER_EXTRA = "USER";

    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;
    private ListView mListView;

    private EditText mInputField;
    private Button mSendButton;

    private String mRecipient;
    private Date mLastMessageDate = new Date();
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mRecipient = "Jon";

        MessageHandler.addMessagesListener(this);

        mListView = (ListView)findViewById(R.id.activity_chat_list_view);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);

        mInputField = (EditText) findViewById(R.id.activity_chat_edit_text_write_message);
        mSendButton = (Button)findViewById(R.id.chat_btn_send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = mInputField.getText().toString();

                String sender = SessionData.getLoggedInUser().getName();

                Message message = new Message();
                message.setSender(sender);
                message.setText(messageText);
                message.setDate(new Date());

                mInputField.setText("");

                MessageHandler.saveMessage(message);

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
    }

    private class MessagesAdapter extends ArrayAdapter<Message> {

        public MessagesAdapter(ArrayList<Message> messages) {
            super(ChatActivity.this, R.layout.activity_chat_message_item, R.id.msg, messages);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            Message message = getItem(position);
            TextView nameView = (TextView) convertView.findViewById(R.id.msg);
            nameView.setText(message.getText());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)nameView.getLayoutParams();
            int sdk = Build.VERSION.SDK_INT;

//            nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_left_gray));

            layoutParams.gravity = Gravity.LEFT;

            nameView.setLayoutParams(layoutParams);

            return convertView;
        }
    }
}
