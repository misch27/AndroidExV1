package com.example.misch.androidexv1.contactsAPI;

import android.content.ContentResolver;
import android.content.Context;
import android.widget.ArrayAdapter;

public interface IContactsFragmentActivity {

    public Context getApplicationContext();

    void setAdapterList (ArrayAdapter<Contact> adapter);

    public ContentResolver getContentResolver();

    }
