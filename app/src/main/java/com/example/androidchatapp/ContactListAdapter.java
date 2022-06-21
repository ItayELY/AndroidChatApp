package com.example.androidchatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidchatapp.Entities.Contact;

import java.util.ArrayList;

public class ContactListAdapter extends ArrayAdapter<Contact> {
    LayoutInflater inflater;

    public ContactListAdapter(Context ctx, ArrayList<Contact> contactArrayList){
        super(ctx, R.layout.contact_list_item, contactArrayList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int Position, @Nullable View convertView, @NonNull ViewGroup parent){
        Contact contact = getItem(Position);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.contact_list_item, parent, false);
        }
        ImageView profile = convertView.findViewById(R.id.profile_img);
        TextView name = convertView.findViewById(R.id.tvContactName);
        TextView lastMsg = convertView.findViewById(R.id.tvLastMessage);
        TextView Time = convertView.findViewById(R.id.tvLastMessageTime);

        name.setText(contact.getName());
        lastMsg.setText(contact.getLastMessageContent());
        Time.setText(contact.getLastMessageDate());


        return convertView;

    }
}
