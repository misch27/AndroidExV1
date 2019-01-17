package com.example.misch.androidexv1.contactsAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.misch.androidexv1.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact>{
    private static final List<Contact> contacts = new ArrayList<>();

    public ContactAdapter(Context context) {
            super(context, R.layout.contact_item, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Contact contact = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.contact_item, null);
            }
            ((TextView) convertView.findViewById(R.id.contact_name))
                    .setText(contact.name);
            ((TextView) convertView.findViewById(R.id.contact_number))
                    .setText(contact.number);
            return convertView;
        }
    }

