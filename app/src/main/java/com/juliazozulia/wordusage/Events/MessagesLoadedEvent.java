package com.juliazozulia.wordusage.Events;


import com.juliazozulia.wordusage.Model.MessageItem;

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
