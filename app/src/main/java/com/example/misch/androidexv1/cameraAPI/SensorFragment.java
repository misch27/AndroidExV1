package com.example.misch.androidexv1.cameraAPI;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorFragment extends Fragment implements ISensorFragmentActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ISensorFragmentPresenter iSensorFragmentPresenter;
    private String mParam1;
    private String mParam2;
    private ImageView imageView;
    private TextView textParamOne;
    private TextView textParamTwo;
    private TextView textParamTree;
    private TextView textNameOfSensor;
    private static final int CAMERA_REQUEST = 0;
    private Uri selectedPhotoPath = null;
    private File cacheImg = null;
    private String timeStamp;
    private Context applicationContext;
    private Sensor defSensor;
    private SensorManager sensorManager;


    private OnFragmentInteractionListener mListener;

    public SensorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SensorFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        view.findViewById(R.id.takePhotoButton).setOnClickListener(new onTakePictureButtonClick());
        view.findViewById(R.id.savePhotoButton).setOnClickListener(new onSavePictureButtonClick());
        imageView = view.findViewById(R.id.imageView);
        textParamOne = view.findViewById(R.id.textView_data1);
        textParamTwo = view.findViewById(R.id.textView_data2);
        textParamTree = view.findViewById(R.id.textView_data3);
        textNameOfSensor = view.findViewById(R.id.textView_nameOfSensor);
        sensorManager = (SensorManager) applicationContext.getSystemService(Context.SENSOR_SERVICE);
        //TODO Поменять кому-то гироскоп на любой другой тип сенсора. Я возьму освещенность
        defSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (iSensorFragmentPresenter == null) {
            iSensorFragmentPresenter = new SensorFragmentPresenter(this);
        }

        // Inflate the layout for this fragment
        return view;
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

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    //TODO Обработать результат не окей, чтоб не срабатывало сохранение непонятно чего.
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            imageView.setImageResource(0);
            imageView.setImageURI(selectedPhotoPath);
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

    //для корневой создать индексирование фотоальбомом
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
            //вытащить файл, вытащить дату и время, сохранить в основной папке
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
                //индексирование картинки галереей
                galleryAddPic();
                //очистить вьюху
                imageView.setImageResource(0);
                selectedPhotoPath = null;
            } else {
                //сказать, чтоб сделали фотку
            }

        }
    }
    /*
      Разобраться, почему временные файлы не удаляются, этож хрень какая-то.
     */
}
