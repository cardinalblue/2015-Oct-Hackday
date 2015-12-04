package com.cardinalblue.trek;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListViewAdapter extends BaseAdapter {

    private LayoutInflater myInflater;
    private ArrayList<String> mLocationtitles;
    private ArrayList<String> mSourceLinks;
    private ArrayList<Boolean> mChecked;
    private Context mContext;
    private CompoundButton.OnCheckedChangeListener mListener;
    private View.OnClickListener mClickListener;

    public CustomListViewAdapter(Context context) {
        mLocationtitles = new ArrayList<>();
        mSourceLinks = new ArrayList<>();
        mChecked = new ArrayList<>();
        myInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setCheckChangedListener(CompoundButton.OnCheckedChangeListener listener,
                                        View.OnClickListener clickListener) {
        mListener = listener;
        mClickListener = clickListener;
    }

    public void addItem(String locTitle, String srcLink) {
        mLocationtitles.add(locTitle);
        mSourceLinks.add(srcLink);
        mChecked.add(false);
    }

    public void update(Intent intent) {
//        int id = intent.getIntExtra(PreloaderActivity.KEY_ID, 0);
//        mLocationtitles.set(id, intent.getStringExtra(PreloaderActivity.KEY_LOC_TITLE));
    }

    public String getLocationTitle(int position) {
        return mLocationtitles.get(position);
    }

    public String getUrl(int position) {
        return mSourceLinks.get(position);
    }

    @Override
    public int getCount() {
        return mLocationtitles.size();
    }

    @Override
    public Object getItem(int position) {
        return mLocationtitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = myInflater.inflate(R.layout.listview_content, null);
        ImageView pinImage = (ImageView) convertView.findViewById(R.id.listview_pin_image);
        TextView titleText = (TextView) convertView.findViewById(R.id.listview_title_textview);
        CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.listview_checkbox);

        checkbox.setChecked(mChecked.get(position));

        titleText.setText(mLocationtitles.get(position));
        titleText.setOnClickListener(mClickListener);
        titleText.setTag(position);

        checkbox.setTag(position);
        checkbox.setOnCheckedChangeListener(mListener);

        switch (position + 1) {
            case 1:
                pinImage.setImageResource(R.drawable.num_icon_1);
                break;
            case 2:
                pinImage.setImageResource(R.drawable.num_icon_2);
                break;
            case 3:
                pinImage.setImageResource(R.drawable.num_icon_3);
                break;
            case 4:
                pinImage.setImageResource(R.drawable.num_icon_4);
                break;
            case 5:
                pinImage.setImageResource(R.drawable.num_icon_5);
                break;
            case 6:
                pinImage.setImageResource(R.drawable.num_icon_6);
                break;
            case 7:
                pinImage.setImageResource(R.drawable.num_icon_7);
                break;
            default:
                pinImage.setImageResource(R.drawable.num_icon_0);
        }

        return convertView;
    }

    public void setChecked(int position, boolean isChecked) {
        mChecked.set(position, isChecked);
    }

}
