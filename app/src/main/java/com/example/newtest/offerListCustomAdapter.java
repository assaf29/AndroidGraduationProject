package com.example.newtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class offerListCustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<Offer> mOfferList;

    //Constructor

    public offerListCustomAdapter(Context mContext, List<Offer> mOfferList) {
        this.mContext = mContext;
        this.mOfferList = mOfferList;
    }

    @Override
    public int getCount() {
        return mOfferList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOfferList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.offer_list_layout, null);

        TextView offerDate = (TextView)v.findViewById(R.id.offerDate);
        TextView postTitle = (TextView)v.findViewById(R.id.postTitle);
        TextView offerSenderDate = (TextView)v.findViewById(R.id.offerSenderName);

        //Set text for TextView

        offerDate.setText(mOfferList.get(position).getDate());
        offerSenderDate.setText(mOfferList.get(position).getOfferSender());
        postTitle.setText(mOfferList.get(position).getPostTitle());

        //Save product id to tag

        v.setTag(mOfferList.get(position).getId());

        return v;
    }
}
