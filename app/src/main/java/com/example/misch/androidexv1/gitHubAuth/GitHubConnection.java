package com.example.misch.androidexv1.gitHubAuth;

import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class GitHubConnection {
    private static IAuth iAuth;
    private static boolean auth2Factor = false;

    GitHubConnection(IAuth iAuth) {
        GitHubConnection.iAuth = iAuth;
    }

    public static void clearValues() {
        auth2Factor = false;
    }

    void connectToGithub() {
        String encoding = Base64.encodeToString(
                (iAuth.getLogin() + ":" + iAuth.getPassword()).getBytes(),
                Base64.DEFAULT);
        AuthAsyncTask authAsyncTask = new AuthAsyncTask();
        authAsyncTask.execute(encoding, iAuth.get2Auth());
    }

    static class AuthAsyncTask extends AsyncTask<String, Void, Void> {
        private ArrayList<String> repoList = new ArrayList<>();
        private boolean hasErrors = false;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String login = iAuth.getLogin();
            String password = iAuth.getPassword();
            iAuth.setRepo(repoList);
            iAuth.activateNextActivity(login, password, hasErrors);
        }

        @Override
        protected Void doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            hasErrors = false;
            try {
                URL location = new URL("https://api.github.com/user/repos");
                connection = (HttpURLConnection) location.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(false);
                connection.setRequestProperty("Authorization", "Basic " + strings[0].trim()
                        .replaceAll("\\n", ""));
                connection.setRequestProperty("X-GitHub-OTP", strings[1]);
                InputStream content = null;
                try {
                    content = connection.getInputStream();
                } catch (Exception ex) {
                    content = connection.getErrorStream();
                }
                bufferedReader = new BufferedReader(
                        new InputStreamReader(content));
                String result = bufferedReader.readLine();

                if (result.toLowerCase().contains("two-factor authentication") && auth2Factor) {
                    hasErrors = true;
                } else if (result.toLowerCase().contains("two-factor authentication")) {
                    auth2Factor = true;
                } else {
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jObj = array.getJSONObject(i);
                        repoList.add(jObj.getString("name"));
                    }
                }
            } catch (Exception e) {
                hasErrors = true;
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return null;
        }

    }

    public static boolean isAuth2Factor() {
        return auth2Factor;
    }
}
