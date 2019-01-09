package com.example.misch.androidexv1.gitHubAuth;

public class PresenterAuthGithub implements IAuthPresenter {
    private IAuthActivity iAuthActivity;

    PresenterAuthGithub(IAuthActivity iAuthActivity) {
        this.iAuthActivity = iAuthActivity;
    }

    @Override
    public void onAuthButtonClick() {
        GitHubConnection connection = new GitHubConnection(iAuthActivity);
        connection.connectToGithub();
    }


}

