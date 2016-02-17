package com.juliazozulia.wordusage.UI.BasicWordFrequency;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juliazozulia.wordusage.R;

import com.juliazozulia.wordusage.Utils.Contextor;
import com.juliazozulia.wordusage.Utils.Frequency;

import java.util.Arrays;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Julia on 23.11.2015.
 */
public class FrequencyAdapter extends ArrayAdapter<String> /*implements StickyListHeadersAdapter*/ {

    Frequency f;
    private LayoutInflater inflater;

    public FrequencyAdapter(Activity activity, Frequency f) {

        super(activity, R.layout.row_word, R.id.word_title, f.getItems());
        inflater = LayoutInflater.from(Contextor.getInstance().getContext());
        this.f = f;
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

    enum FreqStatus{VERY_HIGH, HIGH, MEDIUM, LOW, VERY_LOW }

   // @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.list_internal_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header_text);
            holder.header = (LinearLayout) convertView.findViewById(R.id.header_internal);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText = "";
        switch (getFreqStatus(getItem(position))) {
            case VERY_HIGH: {
                headerText = "Very high";
                break;
            }
            case HIGH: {
                headerText = "High";
                break;

            }

            case MEDIUM: {
                headerText = "Medium";
                break;

            }
        }
        holder.text.setText(headerText);
     //   setColor(position, holder);

        return convertView;
    }

    private  FreqStatus getFreqStatus(String item) {
        // if (f.getPct(item) * 100)
        return FreqStatus.HIGH;
    }

   // @Override
    public long getHeaderId(int position) {
        return getFreqStatus(getItem(position)).ordinal();
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

    class HeaderViewHolder {
        TextView text;
        LinearLayout header;

    }
}
