package com.example.misch.androidexv1.contactsAPI;

import android.content.ContentResolver;
import android.content.Context;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.misch.androidexv1.MenuActivity;


import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends ListFragment implements IContactsFragmentActivity{


    private TextView contactName;
    private TextView contactNumber;
    private static final List<Contact> contacts = new ArrayList<>();
    private Context applicationContext;
    private IContactsFragmentPresenter iContactsFragmentPresenter;
    private ContentResolver cr;
    private static final String CONTACT_ID = ContactsContract.Contacts._ID;
    private static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;

    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        applicationContext = MenuActivity.getContextOfApplication();
        cr = applicationContext.getContentResolver();
        if (iContactsFragmentPresenter == null) {
            iContactsFragmentPresenter = new ContactsFragmentPresenter(this);
        }
    }

    @Override
    public Context getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setAdapterList(ArrayAdapter<Contact> adapter) {
        setListAdapter(adapter);
    }

    public ContentResolver getContentResolver() {
        return cr;
    }

    public TextView getContactName() {
        return contactName;
    }

    public TextView getContactNumber() {
        return contactNumber;
    }
}
