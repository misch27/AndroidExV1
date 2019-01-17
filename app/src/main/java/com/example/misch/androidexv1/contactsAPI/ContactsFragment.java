package com.example.misch.androidexv1.contactsAPI;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.misch.androidexv1.MenuActivity;


public class ContactsFragment extends ListFragment implements IContactsFragmentActivity{

    private TextView contactName;
    private TextView contactNumber;

    private Context applicationContext;
    private IContactsFragmentPresenter iContactsFragmentPresenter;
    private ContentResolver cr;

    public static final int REQUEST_CODE_PERMISSION_READ_CONTACTS = 11;




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        applicationContext = MenuActivity.getContextOfApplication();
        cr = applicationContext.getContentResolver();
        if (iContactsFragmentPresenter == null) {
            iContactsFragmentPresenter = new ContactsFragmentPresenter(this);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int permissionStatus = applicationContext.checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if(permissionStatus == PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},
                        REQUEST_CODE_PERMISSION_READ_CONTACTS);
            } else {
                iContactsFragmentPresenter.getContacts();
            }
        }
    }

    @Override
    public Context getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_PERMISSION_READ_CONTACTS && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                iContactsFragmentPresenter.getContacts();
            }
        }
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
