package com.example.misch.androidexv1.gitHubAuth;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;

import com.example.misch.androidexv1.MenuActivity;
import com.example.misch.androidexv1.R;

import java.util.ArrayList;

public class AuthGitHub extends Activity implements IAuthActivity{
    private IAuthPresenter iAuthPresenter;
    private TextInputEditText login, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_git_hub);
        login = (TextInputEditText) findViewById(R.id.login_data);
        password = (TextInputEditText) findViewById(R.id.password_data);
        if (iAuthPresenter == null) {
            iAuthPresenter = new PresenterAuthGithub(this);
        }
    }


    @Override
    public void onAuthButtonClick(View view) {
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

    }


    @Override
    public void setLogin(String name) {
        login.setText(name);
    }


    @Override
    public void activateNextActivity(String login, String password, Boolean hasErrors) {
        if (!hasErrors) {
            if (GitHubConnection.isAuth2Factor()) {
                Intent intent = new Intent(AuthGitHub.this, SecondStepAuthGitHub.class);
                intent.putExtra("login", login);
                intent.putExtra("password", password);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AuthGitHub.this, MenuActivity.class);
                //тут допилю в ближайшие часы нормальную аутенфикацию. Надо передать репоЛист.
            }
        }
    }
}
