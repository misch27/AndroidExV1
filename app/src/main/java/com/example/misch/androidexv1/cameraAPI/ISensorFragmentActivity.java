package com.example.misch.androidexv1.cameraAPI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import java.io.File;

public interface ISensorFragmentActivity {

    public Context getApplicationContext();

    public Uri getSelectedPhotoPath();

    public File getCacheImg();

    public void setCacheImg(File cacheImg);

    public void startActivityForResult(Intent intent);

    public void setSelectedPhotoPath(Uri selectedPhotoPath);

    public ImageView getImageView();

    public void setImageView(ImageView imageView);

    }

