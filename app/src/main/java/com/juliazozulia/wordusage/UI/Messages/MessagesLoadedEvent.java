package com.juliazozulia.wordusage.UI.Messages;


import java.util.ArrayList;

/**
 * Created by Julia on 22.12.2015.
 */
public class MessagesLoadedEvent {

    final ArrayList<MessageItem> list;

    public MessagesLoadedEvent(ArrayList<MessageItem> list) {
        this.list = list;
    }

    public ArrayList<MessageItem> getItems() {
        return list;
    }

}
