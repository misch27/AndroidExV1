package com.example.misch.androidexv1.infoAPI;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.misch.androidexv1.R;

public class InfoPresenter extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView textParamOne;
    private TextView textParamTwo;
    private TextView textParamTree;
    private TextView textParamFour;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InfoPresenter() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InfoPresenter newInstance(String param1, String param2) {
        InfoPresenter fragment = new InfoPresenter();
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
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        textParamOne = view.findViewById(R.id.textView11);
        textParamTwo = view.findViewById(R.id.textView13);
        textParamTree = view.findViewById(R.id.textView15);
        textParamFour = view.findViewById(R.id.textView17);
        getInfo();
        return view;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getInfo() {
        try {
            textParamOne.setText(Build.MODEL);
        } catch (Exception e) {
            textParamOne.setText("Ошибка");
        }
        try {
            textParamTwo.setText(Build.VERSION.INCREMENTAL);
        } catch (Exception e) {
            textParamOne.setText("Ошибка");
        }
        try {
            textParamTree.setText(Build.VERSION.RELEASE);
        } catch (Exception e) {
            textParamOne.setText("Ошибка");
        }
        try {
            textParamFour.setText(Build.VERSION.SDK_INT);
        } catch (Exception e) {
            textParamOne.setText("Ошибка");
        }

    }
}
