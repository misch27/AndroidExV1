package com.example.misch.androidexv1.gitHubAuth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.example.misch.androidexv1.MenuActivity;
import com.example.misch.androidexv1.R;

import java.util.ArrayList;

public class SecondStepAuthGitHub extends Activity implements ISecondStepAuthActivity {
    private ISecondStepAuthPresenter iSecondStepAuthPresenter;
    private ArrayList<String> repoList = new ArrayList<>();
    private TextInputEditText twoAuth;
    private String login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth2_git_hub);
        login = getIntent().getExtras().getString("login");
        password = getIntent().getExtras().getString("password");
        twoAuth = (TextInputEditText) findViewById(R.id.dualAuth);
        if (iSecondStepAuthPresenter == null) {
            iSecondStepAuthPresenter = new PresenterSecondStepAuthGitHub(this);
        }
    }


    public void onAuth2ButtonClick(View view) {
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
        if (!isError) {
            Intent intent = new Intent(SecondStepAuthGitHub.this, MenuActivity.class);
            intent.putExtra("login", login);
            intent.putExtra("repoList", repoList);
            startActivity(intent);
        }
    }

    @Override
    public void setLogin(String name) {
        twoAuth.setText(name);
    }

    @Override
    public void setRepo(ArrayList<String> repoList) {
        this.repoList = repoList;
    }


}
