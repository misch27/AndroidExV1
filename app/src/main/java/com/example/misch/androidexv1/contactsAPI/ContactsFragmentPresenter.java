package com.example.misch.androidexv1.contactsAPI;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.misch.androidexv1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactsFragmentPresenter implements IContactsFragmentPresenter {

    private Context applicationContext;
    private IContactsFragmentActivity iContactsFragmentActivity;
    private static final String CONTACT_ID = ContactsContract.Contacts._ID;
    private static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;


    public ContactsFragmentPresenter(ContactsFragment contactsFragment) {
        iContactsFragmentActivity = contactsFragment;
        applicationContext = contactsFragment.getApplicationContext();
        ArrayAdapter<Contact> adapter = new ContactAdapter(applicationContext);
        //запрос к базе данных за контактами
        HashMap<Integer, ArrayList<String>> phones = new HashMap<>();
        ContentResolver cr = iContactsFragmentActivity.getContentResolver();
        Cursor pCur = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{PHONE_NUMBER, PHONE_CONTACT_ID},
                null,
                null,
                null
        );
        if (pCur != null) {
            if (pCur.getCount() > 0) {
                while (pCur.moveToNext()) {
                    Integer contactId = pCur.getInt(pCur.getColumnIndex(PHONE_CONTACT_ID));
                    ArrayList<String> curPhones = new ArrayList<>();
                    if (phones.containsKey(contactId)) {
                        curPhones = phones.get(contactId);
                    }
                    curPhones.add(pCur.getString(pCur.getColumnIndex(PHONE_NUMBER)));
                    phones.put(contactId, curPhones);
                }

                Cursor cur = cr.query(
                        ContactsContract.Contacts.CONTENT_URI,
                        new String[]{CONTACT_ID, DISPLAY_NAME, HAS_PHONE_NUMBER},
                        HAS_PHONE_NUMBER + " > 0",
                        null,
                        DISPLAY_NAME + " ASC"
                );

                if (cur != null) {
                    if (cur.getCount() > 0) {
                        while (cur.moveToNext()) {
                            int id = cur.getInt(cur.getColumnIndex(CONTACT_ID));
                            Contact con = new Contact(cur.getString(cur.getColumnIndex(DISPLAY_NAME)), TextUtils.join(",", phones.get(id).toArray()));
                            adapter.add(con);
                        }
                    }
                }
                cur.close();
            }
        }
        pCur.close();
        iContactsFragmentActivity.setAdapterList(adapter);
    }

}




