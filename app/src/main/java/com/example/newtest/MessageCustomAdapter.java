package com.example.newtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import static com.example.newtest.LogInActivity.currentUser;

public class MessageCustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<ChatMessage> mMessageList;

    //Constructor

    public MessageCustomAdapter(Context mContext, List<ChatMessage> mMessageList) {
        this.mContext = mContext;
        this.mMessageList = mMessageList;
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.message_layout, null);

        TextView messageUser = (TextView)v.findViewById(R.id.message_user);
        TextView messageText = (TextView)v.findViewById(R.id.message_text);
        TextView messageDate = (TextView)v.findViewById(R.id.message_time);

        //Set text for TextView

        messageUser.setText(mMessageList.get(position).getMessageUser());
        messageText.setText(mMessageList.get(position).getMessageText());
        messageDate.setText("2019");

        //Save product id to tag

        v.setTag(mMessageList.get(position).getMessageID());

        return v;
    }
}
