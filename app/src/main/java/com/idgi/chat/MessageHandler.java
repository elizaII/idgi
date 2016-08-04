package com.idgi.chat;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by tove on 2016-08-03.
 */

public class MessageHandler {
    private static final String TAG = MessageHandler.class.getSimpleName();

    private static final Firebase sFirebaseClient = new Firebase("https://scorching-torch-4835.firebaseio.com");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");

    private static final String TABLE_NAME = "chat";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER = "sender";

    public interface MessagesListener {
        void onMessageAdded(Message message);
    }

    public static FirebaseMessagesListener addMessagesListener(final MessagesListener msgListener) {
        FirebaseMessagesListener fbMsgListener = new FirebaseMessagesListener(msgListener);
        sFirebaseClient.child(TABLE_NAME).addChildEventListener(fbMsgListener);
        return fbMsgListener;
    }

    public static void saveMessage(Message message) {
        Date date = message.getDate();
        String dateString = sDateFormat.format(date);

        HashMap<String, String> messageData = new HashMap<>();
        messageData.put(COLUMN_TEXT, message.getText());
        messageData.put(COLUMN_SENDER, "Tove");

        sFirebaseClient.child(TABLE_NAME).child(dateString).setValue(messageData);
    }

    public static void stop(FirebaseMessagesListener listener) {
        sFirebaseClient.removeEventListener(listener);
    }

    public static class FirebaseMessagesListener implements ChildEventListener {
        private MessagesListener msgListener;

        FirebaseMessagesListener(MessagesListener listener) {
            msgListener = listener;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String, String> messageData = (HashMap) dataSnapshot.getValue();

            Message message = new Message();
            message.setText(messageData.get(COLUMN_TEXT));
            message.setSender(messageData.get(COLUMN_SENDER));
            try {
                message.setDate(sDateFormat.parse(dataSnapshot.getKey()));
            }
            catch (Exception e) {
                Log.d(TAG, "Couldn't parse data: " + e.getLocalizedMessage());
            }

            msgListener.onMessageAdded(message);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {}

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

        @Override
        public void onCancelled(FirebaseError firebaseError) {}
    }
}
