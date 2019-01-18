package com.example.misch.androidexv1.gitHubAuth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.misch.androidexv1.MenuActivity;
import com.example.misch.androidexv1.R;

import java.util.ArrayList;

public class SecondStepAuthGitHub extends Activity implements ISecondStepAuthActivity {
    private ISecondStepAuthPresenter iSecondStepAuthPresenter;
    private ArrayList<String> repoList = new ArrayList<>();
    private TextInputEditText twoAuth;
    private String login, password;
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth2_git_hub);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        login = getIntent().getExtras().getString("login");
        password = getIntent().getExtras().getString("password");
        twoAuth = (TextInputEditText) findViewById(R.id.dualAuth);
        if (iSecondStepAuthPresenter == null) {
            iSecondStepAuthPresenter = new PresenterSecondStepAuthGitHub(this);
        }
    }


    public void onAuth2ButtonClick(View view) {
        load = new ProgressDialog(this);
        load.setMessage("Авторизация..");
        load.setMax(2048);
        load.show();
        load.setIndeterminate(true);
        iSecondStepAuthPresenter.onSecondAuthButtonSet();
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String get2Auth() {
        return twoAuth.getText().toString();
    }

    @Override
    public void activateNextActivity(String login, String password, Boolean isError) {
        load.dismiss();
        if (!isError) {
            Intent intent = new Intent(SecondStepAuthGitHub.this, MenuActivity.class);
            intent.putExtra("login", login);
            intent.putExtra("repoList", repoList);
            startActivity(intent);
            this.finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Ошибка авторизации. Неверный код второго фактора.")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void setRepo(ArrayList<String> repoList) {
        this.repoList = repoList;
    }


}
