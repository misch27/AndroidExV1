package com.example.misch.androidexv1.gitHubAuth;

public class PresenterSecondStepAuthGitHub implements ISecondStepAuthPresenter{
    private ISecondStepAuthActivity iSecondStepAuthActivity;
    public PresenterSecondStepAuthGitHub(ISecondStepAuthActivity iSecondStepAuthActivity) {
        this.iSecondStepAuthActivity = iSecondStepAuthActivity;
    }

    @Override
    public void onSecondAuthButtonSet() {
        GitHubConnection connection = new GitHubConnection(iSecondStepAuthActivity);
        connection.connectToGithub();
    }
}
