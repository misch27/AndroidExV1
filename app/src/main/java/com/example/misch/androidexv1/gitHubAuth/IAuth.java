package com.example.misch.androidexv1.gitHubAuth;

import java.util.ArrayList;

public interface IAuth {
    void setRepo(ArrayList<String> repoList);
    public String getLogin();
    public String getPassword();
    public String get2Auth();
    void activateNextActivity(String login, String password, Boolean auth2);

}
