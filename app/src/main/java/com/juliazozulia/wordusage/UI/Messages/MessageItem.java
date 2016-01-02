package com.juliazozulia.wordusage.UI.Messages;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Julia on 22.12.2015.
 */

public class MessageItem {

    String text;
    String recipient;
    Date date;
    //add some another stuff late

    public MessageItem(String text, String recipient, Date date) {

        this.text = text;
        this.recipient = recipient;
        this.date = date;
    }

    public MessageItem(String text, Date date) {

        this.text = text;
        this.recipient = "";
        this.date = date;
    }


}