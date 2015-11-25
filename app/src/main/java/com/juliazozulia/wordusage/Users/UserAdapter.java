package com.juliazozulia.wordusage.Users;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juliazozulia.wordusage.BasicWordFrequency.WordItem;
import com.juliazozulia.wordusage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Julia on 24.11.2015.
 */

class UserAdapter extends ArrayAdapter<UserItem> {

    Activity activity;

    UserAdapter(Activity activity, List<UserItem> items) {
        super(activity, R.layout.row_user, R.id.user_title, items);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        UserItem item = getItem(position);

        ViewHolder mViewHolder = (ViewHolder) row.getTag();

        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(row);
            row.setTag(mViewHolder);
        }

        if (item.userFullName != null) {
            mViewHolder.title.setText(item.userFullName);
        } else {
            mViewHolder.title.setText(item.userSkypeName);
        }
        Picasso.with(activity)
                .load(item.profileImage)
                .fit().centerCrop()
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_person_black_24dp)
                .into(mViewHolder.icon);


        return (row);
    }


    class ViewHolder {
        TextView title = null;
        ImageView icon = null;

        ViewHolder(View row) {
            this.title = (TextView) row.findViewById(R.id.user_title);
            this.icon = (ImageView) row.findViewById(R.id.icon);
        }

    }


}