package com.example.androidchatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidchatapp.Entities.Message;

import java.util.ArrayList;

public class MessageListAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;

    public MessageListAdapter(Context ctx, ArrayList<Message> messageArrayList){
        super(ctx, R.layout.message_list_item_sent, messageArrayList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int Position, @Nullable View convertView, @NonNull ViewGroup parent){
        Message message = getItem(Position);
        if(message.getSent() == true){
            convertView = inflater.inflate(R.layout.message_list_item_sent, parent, false);
        }
        else{
            convertView = inflater.inflate(R.layout.message_list_item_recieved, parent, false);
        }
        TextView messageContent = convertView.findViewById(R.id.tvMessageContent);
       // TextView Time = convertView.findViewById(R.id.tvMessageTime);

        messageContent.setText(message.getContent());


        return convertView;

    }
}
