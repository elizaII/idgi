package com.idgi.chat;

import java.util.Date;

/**
 * Created by tove on 2016-08-03.
 */

public class Message {
    private String mText;
    private String mSender;
    private Date mDate;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String mSender) {
        this.mSender = mSender;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getText() {
        return mText;
    }
}
