package com.idgi.android.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.android.activity.CourseActivity;
import com.idgi.android.dialog.LoginRequiredDialog;
import com.idgi.chat.Message;
import com.idgi.chat.MessageHandler;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.Date;

public class CourseChatFragment extends Fragment implements MessageHandler.MessagesListener {

    public static final String TAG = CourseChatFragment.class.getSimpleName();
    public static final String USER_EXTRA = "USER";

    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;
    private ListView mListView;

    private EditText mInputField;
    private Button mSendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_chat, container, false);

        // Creates a login dialog if user is not logged in
        if (SessionData.getLoggedInUser() == null) {
            Dialog dialog = new LoginRequiredDialog(getContext());
            dialog.show();
            dialog.getWindow().setGravity(Gravity.CENTER);

            return view;
        }

        // Connects CourseChatFragment to MessageHandler
        MessageHandler.addMessagesListener(this);

        mListView = (ListView) view.findViewById(R.id.activity_chat_list_view);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);

        mInputField = (EditText) view.findViewById(R.id.activity_chat_edit_text_write_message);
        mSendButton = (Button) view.findViewById(R.id.chat_btn_send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = mInputField.getText().toString();

                String sender = SessionData.getLoggedInUser().getName();

                Message message = new Message();
                message.setSender(sender);
                message.setReceiver(SessionData.getCurrentCourse().getName());
                message.setText(messageText);
                message.setDate(new Date());

                mInputField.setText("");

                MessageHandler.saveMessage(message);
            }
        });

        return view;
    }

    @Override
    public void onMessageAdded(Message message) {
        if (SessionData.getCurrentCourse().getName().equals(message.getReceiver())) {
            mMessages.add(message);
            mListView.requestLayout();
            mListView.smoothScrollToPosition(mMessages.size()-1);
        }
    }

    private class MessagesAdapter extends ArrayAdapter<Message> {

        public MessagesAdapter(ArrayList<Message> messages) {
            super(CourseChatFragment.this.getContext(), R.layout.activity_chat_message_item, R.id.msg, messages);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            Message message = getItem(position);

            TextView msgView = (TextView) convertView.findViewById(R.id.msg);
            msgView.setText(message.getText());

            TextView nameView = (TextView) convertView.findViewById(R.id.sender);
            nameView.setText(message.getSender());

            Date date = message.getDate();

            TextView dateView = (TextView) convertView.findViewById(R.id.date);

            String hour = (String) android.text.format.DateFormat.format("hh", date);
            String minute = (String) android.text.format.DateFormat.format("mm", date);
            String second = (String) android.text.format.DateFormat.format("ss", date);

            String day = (String) android.text.format.DateFormat.format("dd", date);
            String month = (String) android.text.format.DateFormat.format("MM", date);
            String year = (String) android.text.format.DateFormat.format("yyyy", date);

            dateView.setText("Skickades klockan " + hour+ ":" + minute + ":" + second + " den " + day + "/" + month + " " + year + " ");

            return convertView;
        }
    }
}
