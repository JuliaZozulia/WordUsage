package com.juliazozulia.wordusage.UI.Messages;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.juliazozulia.wordusage.Model.MessageItem;
import com.juliazozulia.wordusage.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia on 28.12.2015.
 */
public class MessageAdapter extends ArrayAdapter<MessageItem> {
    MessageAdapter(Activity activity, List<MessageItem> items) {
        super(activity, R.layout.row_message, R.id.message_text, new ArrayList(items));
    }


    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        MessageItem item = getItem(position);

        ViewHolder mViewHolder = (ViewHolder) row.getTag();

        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(row);
            row.setTag(mViewHolder);
        }

        mViewHolder.text.setText(item.getText());
        mViewHolder.title.setText(item.getRecipient());

        mViewHolder.date.setText(DateFormat.getDateTimeInstance().format(item.getDate()));


        return (row);
    }


    class ViewHolder {
        TextView text = null;
        TextView title = null;
        TextView date = null;

        //com.pkmmte.view.CircularImageView icon = null;

        ViewHolder(View row) {
            this.text = (TextView) row.findViewById(R.id.message_text);
            this.title = (TextView) row.findViewById(R.id.message_title);
            this.date = (TextView) row.findViewById(R.id.message_date);

            //this.icon = (com.pkmmte.view.CircularImageView) row.findViewById(R.id.icon);
        }

    }
}
