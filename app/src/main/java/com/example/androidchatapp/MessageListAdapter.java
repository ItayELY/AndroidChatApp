package com.example.androidchatapp;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.androidchatapp.Entities.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessageListAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;
    ArrayList<Message> messages;
    MutableLiveData<ArrayList<Message>> liveData;

    public MutableLiveData<ArrayList<Message>> getLiveData() {
        return liveData;
    }

    public void setLiveData(MutableLiveData<ArrayList<Message>> liveData) {
        this.liveData = liveData;
        notifyDataSetChanged();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
        liveData.postValue(messages);
        notifyDataSetChanged();
    }

    public MessageListAdapter(Context ctx, ArrayList<Message> messageArrayList){
        super(ctx, R.layout.message_list_item_sent, messageArrayList);
        this.messages = messageArrayList;
        this.inflater = LayoutInflater.from(ctx);
        liveData = new MutableLiveData<>();
        setMessages(messageArrayList);
    }

    public void addMessage(Message m)  {
        this.messages.add(m);
        setMessages(this.messages);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        TextView Time = convertView.findViewById(R.id.tvMessageTime);

        messageContent.setText(message.getContent());
        Time.setText(message.getCreated());


        return convertView;

    }
}
