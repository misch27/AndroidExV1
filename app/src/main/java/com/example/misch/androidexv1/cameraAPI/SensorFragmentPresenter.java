package com.example.misch.androidexv1.cameraAPI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class SensorFragmentPresenter implements ISensorFragmentPresenter {
    private Context applicationContext;
    private ISensorFragmentActivity iSensorFragmentActivity;

    public Context getApplicationContext() {
        return applicationContext;
    }

    public ISensorFragmentActivity getiSensorFragmentActivity() {
        return iSensorFragmentActivity;
    }

    public SensorFragmentPresenter(SensorFragment sensorFragment) {
        iSensorFragmentActivity = sensorFragment;
        applicationContext = sensorFragment.getApplicationContext();
    }

    void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    //для корневой создать индексирование фотоальбомом
    void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = iSensorFragmentActivity.getSelectedPhotoPath();
        mediaScanIntent.setData(contentUri);
        applicationContext.sendBroadcast(mediaScanIntent);
    }


}
