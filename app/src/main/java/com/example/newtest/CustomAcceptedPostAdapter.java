package com.example.newtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAcceptedPostAdapter extends BaseAdapter {

    private Context mContext;
    private List<AcceptedOffer> mPostList;

    //Constructor

    public CustomAcceptedPostAdapter(Context mContext, List<AcceptedOffer> mPostList) {

        this.mContext = mContext;
        this.mPostList = mPostList;

    }

    @Override
    public int getCount() {
        return mPostList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPostList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.accepted_post_layout, null);
        TextView postTitle = (TextView)v.findViewById(R.id.postTitle);
        TextView postState = (TextView)v.findViewById(R.id.postState);
        TextView jobSeeker = (TextView)v.findViewById(R.id.postJobSeeker);

        //Set text for TextView

        postTitle.setText(mPostList.get(position).getPostTitle());
//        postState.setText(mPostList.get(position).get);
        jobSeeker.setText(mPostList.get(position).getJobSeeker());

        //Save product id to tag
        v.setTag(mPostList.get(position).getId());

        return v;
    }
}
