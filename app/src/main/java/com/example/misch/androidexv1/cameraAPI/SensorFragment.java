package com.example.misch.androidexv1.cameraAPI;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misch.androidexv1.BuildConfig;
import com.example.misch.androidexv1.MenuActivity;
import com.example.misch.androidexv1.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.FileProvider.getUriForFile;


public class SensorFragment extends Fragment implements ISensorFragmentActivity {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ISensorFragmentPresenter iSensorFragmentPresenter;
    private String mParam1;
    private String mParam2;
    private ImageView imageView;
    private TextView textParamOne;
    private TextView textParamTwo;
    private TextView textParamTree;
    private TextView textNameOfSensor;
    private Button takePhotoButton;
    private Button savePhotoButton;
    private static final int CAMERA_REQUEST = 0;
    private Uri selectedPhotoPath = null;
    private File cacheImg = null;
    private String timeStamp;
    private Context applicationContext;
    private Sensor defSensor;
    private SensorManager sensorManager;
    private int permissionStatusForWES;
    private int permissionStatusForC;

    public static final int REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE = 122;
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 123;
    public static final int REQUEST_CODE_PERMISSION_ALL = 124;

    private OnFragmentInteractionListener mListener;

    public SensorFragment() {
        // Required empty public constructor
    }

    public static SensorFragment newInstance(String param1, String param2) {
        SensorFragment fragment = new SensorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sensor, container, false);
        applicationContext = MenuActivity.getContextOfApplication();
        applicationContext.getContentResolver();
        takePhotoButton = view.findViewById(R.id.takePhotoButton);
        takePhotoButton.setOnClickListener(new onTakePictureButtonClick());
        takePhotoButton.setEnabled(false);
        savePhotoButton = view.findViewById(R.id.savePhotoButton);
        savePhotoButton.setOnClickListener(new onSavePictureButtonClick());
        savePhotoButton.setEnabled(false);
        imageView = view.findViewById(R.id.imageView);
        textParamOne = view.findViewById(R.id.textView_data1);
        textParamTwo = view.findViewById(R.id.textView_data2);
        textParamTree = view.findViewById(R.id.textView_data3);
        textNameOfSensor = view.findViewById(R.id.textView_nameOfSensor);
        sensorManager = (SensorManager) applicationContext.getSystemService(Context.SENSOR_SERVICE);
        defSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (iSensorFragmentPresenter == null) {
            iSensorFragmentPresenter = new SensorFragmentPresenter(this);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            permissionStatusForWES = applicationContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissionStatusForC = applicationContext.checkSelfPermission(Manifest.permission.CAMERA);
            if(permissionStatusForWES == PackageManager.PERMISSION_DENIED || permissionStatusForC == PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                        REQUEST_CODE_PERMISSION_ALL);
            }

            if((permissionStatusForC == PackageManager.PERMISSION_GRANTED)&&(permissionStatusForWES == PackageManager.PERMISSION_GRANTED)){
                takePhotoButton.setEnabled(true);
            }
        }


        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_PERMISSION_ALL && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                takePhotoButton.setEnabled(true);
            }else {
                takePhotoButton.setEnabled(false);
            }
        }

    }

    private final SensorEventListener workingSensorEventListener = new SensorEventListener() {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            textParamOne.setText(String.format(Locale.ENGLISH,"%.3f",event.values[0]));
            textParamTwo.setText(String.format(Locale.ENGLISH,"%.3f",event.values[1]));
            textParamTree.setText(String.format(Locale.ENGLISH,"%.3f",event.values[2]));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(defSensor != null){
            sensorManager.registerListener(workingSensorEventListener, defSensor, SensorManager.SENSOR_DELAY_NORMAL);
            textNameOfSensor.setText(defSensor.getName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(defSensor != null){
            sensorManager.unregisterListener(workingSensorEventListener,defSensor);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST){
           if(resultCode == RESULT_OK){
               imageView.setImageResource(0);
               imageView.setImageURI(selectedPhotoPath);
               savePhotoButton.setEnabled(true);
           } else{
               savePhotoButton.setEnabled(false);
               Toast toast = Toast.makeText(getApplicationContext(),
                       "Что-то пошло не так", Toast.LENGTH_SHORT);
               toast.show();
           }
        }
    }

    public Uri getSelectedPhotoPath() {
        return selectedPhotoPath;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setSelectedPhotoPath(Uri selectedPhotoPath) {
        this.selectedPhotoPath = selectedPhotoPath;
    }

    public File getCacheImg() {
        return cacheImg;
    }

    public void setCacheImg(File cacheImg) {
        this.cacheImg = cacheImg;
    }

    @Override
    public void startActivityForResult(Intent intent) {
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = selectedPhotoPath;
        mediaScanIntent.setData(contentUri);
        applicationContext.sendBroadcast(mediaScanIntent);
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
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

    class onTakePictureButtonClick implements View.OnClickListener {
        //работа с камерой
        public void onClick(View view) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
            File imagePath = new File(applicationContext.getCacheDir(), "img");
            imagePath.mkdirs(); //create folders where write files
            File newFile = null;
            try {
                newFile = File.createTempFile("default_image", ".jpg", imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cacheImg = newFile;
            selectedPhotoPath = getUriForFile(applicationContext, BuildConfig.APPLICATION_ID + ".fileprovider", newFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedPhotoPath);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                ClipData clip = ClipData.newUri(applicationContext.getContentResolver(), "A photo", selectedPhotoPath);
                intent.setClipData(clip);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    class onSavePictureButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            if (selectedPhotoPath != null) {
                File source = cacheImg;
                File file = new File(applicationContext.getFilesDir(),"images");
                file.mkdirs();
                File dest = new File(file, timeStamp + ".jpg");
                try {
                    copyFile(source, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                galleryAddPic();
                imageView.setImageResource(0);
                selectedPhotoPath = null;
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Нужно сделать фото", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

}
