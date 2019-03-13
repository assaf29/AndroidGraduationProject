package com.example.newtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class userListCustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> mUserList;

    //Constructor

    public userListCustomAdapter(Context mContext, List<User> mUserList) {
        this.mContext = mContext;
        this.mUserList = mUserList;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.user_list_layout, null);
        TextView userCategory = (TextView)v.findViewById(R.id.userCategory);
        TextView userName = (TextView)v.findViewById(R.id.userName);
        TextView userGender = (TextView)v.findViewById(R.id.userGender);
        CircleImageView userImage = (CircleImageView)v.findViewById(R.id.userImage);

        //Set text for TextView

        userCategory.setText(mUserList.get(position).getCategory());
        userName.setText(mUserList.get(position).getName());
        userGender.setText(mUserList.get(position).getGender());
//        Picasso.get().load(mUserList.get(position).getImage()).into(userImage);

        //Save product id to tag
        v.setTag(mUserList.get(position).getUserID());

        return v;
    }
}
