package com.example.misch.androidexv1.calculatorAPI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.misch.androidexv1.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView resultField;
    EditText numberField;
    TextView operationField;
    Double operand = null;
    String lastOperation = "=";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator,container,false);
        resultField =(TextView) view.findViewById(R.id.Last);
        numberField = (EditText) view.findViewById(R.id.Number);
        operationField = (TextView) view.findViewById(R.id.Result);
        Button button0 = ((Button) view.findViewById(R.id.Num0));
        ((Button) view.findViewById(R.id.Num0)).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num1).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num2).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num3).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num4).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num5).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num6).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num7).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num8).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num9).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.Num0).setOnClickListener(new onNumberClick());
        view.findViewById(R.id.NumDev).setOnClickListener(new onOperationClick());
        view.findViewById(R.id.NumDiv).setOnClickListener(new onOperationClick());
        view.findViewById(R.id.NumMinus).setOnClickListener(new onOperationClick());
        view.findViewById(R.id.NumPlus).setOnClickListener(new onOperationClick());
        view.findViewById(R.id.NumProiz).setOnClickListener(new onOperationClick());
        view.findViewById(R.id.NumZap).setOnClickListener(new onOperationClick());

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

    private class onNumberClick implements View.OnClickListener {
        public void onClick(View view){
            Button button = (Button)view;
            numberField.append(button.getText());

            if(lastOperation.equals("=") && operand!=null){
                operand = null;
            }
        }
    }
    private class onOperationClick implements View.OnClickListener {
        public void onClick(View view) {
            Button button = (Button) view;
            String op = button.getText().toString();
            String number = numberField.getText().toString();
            if (number.length() > 0) {
                number = number.replace(',', '.');
                try {
                    performOperation(Double.valueOf(number), op);
                } catch (NumberFormatException ex) {
                    numberField.setText("");
                }
            }
            lastOperation = op;
            operationField.setText(lastOperation);
        }
    }

    private void performOperation(Double number, String operation){

        if(operand ==null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch(lastOperation){
                case "=":
                    operand =number;
                    break;
                case "/":
                    if(number==0){
                        operand =0.0;
                    }
                    else{
                        operand /=number;
                    }
                    break;
                case "*":
                    operand *=number;
                    break;
                case "+":
                    operand +=number;
                    break;
                case "-":
                    operand -=number;
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }
}
