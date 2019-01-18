package com.example.misch.androidexv1.gitHubAuth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.misch.androidexv1.MenuActivity;
import com.example.misch.androidexv1.R;

import java.util.ArrayList;

public class AuthGitHub extends Activity implements IAuthActivity{
    private IAuthPresenter iAuthPresenter;
    private ProgressDialog load;
    private TextInputEditText login, password;
    private ArrayList<String> repoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_git_hub);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        login = (TextInputEditText) findViewById(R.id.login_data);
        password = (TextInputEditText) findViewById(R.id.password_data);
        if (iAuthPresenter == null) {
            iAuthPresenter = new PresenterAuthGithub(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onAuthButtonClick(View view) {
        load = new ProgressDialog(this);
        load.setMessage("Авторизация..");
        load.setMax(2048);
        load.show();
        load.setIndeterminate(true);
        iAuthPresenter.onAuthButtonClick();

    }

    @Override
    public String getLogin() {
        try {
            return login.getText().toString();
        } catch (NullPointerException loginNull) {
            return null;
        }
    }

    @Override
    public String getPassword() {
        try {
            return password.getText().toString();
        } catch (NullPointerException loginNull) {
            return null;
        }
    }

    @Override
    public String get2Auth() {
        return " ";
    }

    @Override
    public void setRepo(ArrayList<String> repoList) {
        this.repoList = repoList;
    }

    @Override
    public void activateNextActivity(String login, String password, Boolean hasErrors) {
        load.dismiss();
        if (!hasErrors) {
            if (GitHubConnection.isAuth2Factor()) {
                Intent intent = new Intent(AuthGitHub.this, SecondStepAuthGitHub.class);
                intent.putExtra("login", login);
                intent.putExtra("password", password);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AuthGitHub.this, MenuActivity.class);
                intent.putExtra("login", login);
                intent.putExtra("repoList", repoList);
                startActivity(intent);
                this.finish();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Ошибка авторизации. Неверные логин/пароль.")
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

}
