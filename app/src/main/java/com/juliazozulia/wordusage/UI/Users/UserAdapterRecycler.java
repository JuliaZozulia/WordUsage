package com.juliazozulia.wordusage.UI.Users;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juliazozulia.wordusage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Julia on 22.12.2015.
 */

public class UserAdapterRecycler extends RecyclerView.Adapter<UserAdapterRecycler.ViewHolder> {

    private List<UserItem> mDataset;
    Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        com.pkmmte.view.CircularImageView icon;

        public ViewHolder(View row) {
            super(row);
            this.title = (TextView) row.findViewById(R.id.user_title);
            this.icon = (com.pkmmte.view.CircularImageView) row.findViewById(R.id.icon);
        }
    }


    public UserAdapterRecycler(Activity activity, List<UserItem> myDataset) {
        this.activity = activity;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserAdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(row);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.title.setText(mDataset.get(position).userFullName);
        Picasso.with(activity)
                .load(mDataset.get(position).profileImage)
                .fit().centerCrop()
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_person_black_24dp)
                .into(holder.icon);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}