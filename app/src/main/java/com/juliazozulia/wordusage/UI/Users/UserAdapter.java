package com.juliazozulia.wordusage.UI.Users;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.juliazozulia.wordusage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia on 24.11.2015.
 */

class UserAdapter extends ArrayAdapter<UserItem> {

    Activity activity;

    UserAdapter(Activity activity, List<UserItem> items) {
        super(activity, R.layout.row_user, R.id.user_title, new ArrayList(items));
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

        mViewHolder.title.setText(item.userFullName);
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
        com.pkmmte.view.CircularImageView icon = null;

        ViewHolder(View row) {
            this.title = (TextView) row.findViewById(R.id.user_title);
            this.icon = (com.pkmmte.view.CircularImageView) row.findViewById(R.id.icon);
        }

    }


}