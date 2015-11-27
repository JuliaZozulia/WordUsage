package com.juliazozulia.wordusage.BasicWordFrequency;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.juliazozulia.wordusage.R;

import org.apache.commons.math3.stat.Frequency;

/**
 * Created by Julia on 23.11.2015.
 */
public class FrequencyAdapter extends ArrayAdapter<String> {

    Frequency f;

    public FrequencyAdapter(Activity activity, Frequency f) {

        super(activity, R.layout.row_word, R.id.word_title, f.getItems());
        this.f = f;
    }


    @Override
    public int getCount() {
        return f.getUniqueCount();
    }


    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        String item = getItem(position);

        ViewHolder mViewHolder = (ViewHolder) row.getTag();

        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(row);
            row.setTag(mViewHolder);
        }

        mViewHolder.title.setText(item);
        mViewHolder.absolute.setText(Long.toString(f.getCount(item)));
        mViewHolder.relative.setText(String.format("%.2f", f.getPct(item) * 100) + "%");


        return (row);
    }


    class ViewHolder {
        TextView title = null;
        TextView absolute = null;
        TextView relative = null;

        ViewHolder(View row) {
            this.title = (TextView) row.findViewById(R.id.word_title);
            this.absolute = (TextView) row.findViewById(R.id.absolute);
            this.relative = (TextView) row.findViewById(R.id.relative);
        }

    }
}
