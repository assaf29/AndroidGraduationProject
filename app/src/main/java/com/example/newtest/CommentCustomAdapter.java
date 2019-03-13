package com.example.newtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.newtest.LogInActivity.currentUser;

public class CommentCustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<Comment> mCommentList;

    //Constructor

    public CommentCustomAdapter(Context mContext, List<Comment> mCommentList) {
        this.mContext = mContext;
        this.mCommentList = mCommentList;
    }

    @Override
    public int getCount() {
        return mCommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.comment_layout, null);
        TextView postTitle = (TextView)v.findViewById(R.id.postTitle);
        TextView postCategory = (TextView)v.findViewById(R.id.postCategory);
        TextView postDate = (TextView)v.findViewById(R.id.postDate);

        //Set text for TextView

        postTitle.setText(mCommentList.get(position).getCreatedUser());
        postCategory.setText(mCommentList.get(position).getContent());
        postDate.setText(mCommentList.get(position).getDate());

        //Save product id to tag
        v.setTag(mCommentList.get(position).getId());

        return v;
    }
}
