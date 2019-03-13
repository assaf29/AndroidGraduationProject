package com.example.newtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NgocTri on 11/15/2015.
 */
public class customAdapter extends BaseAdapter {

    private Context mContext;
    private List<Post> mPostList;

    //Constructor

    public customAdapter(Context mContext, List<Post> mPostList) {
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

        View v = View.inflate(mContext, R.layout.post_layout, null);

        TextView postTitle = (TextView)v.findViewById(R.id.postTitle);
        TextView postDate = (TextView)v.findViewById(R.id.postDate);
        TextView createdUser = (TextView)v.findViewById(R.id.createdUser);
        TextView postPrice = (TextView)v.findViewById(R.id.postPrice);

        //Set text for TextView

        postTitle.setText(mPostList.get(position).getTitle());
        postDate.setText(mPostList.get(position).getDate());
        createdUser.setText(mPostList.get(position).getCreatedUser());
        postPrice.setText(mPostList.get(position).getPrice());

        //Save product id to tag
        v.setTag(mPostList.get(position).getId());

        return v;
    }
}
