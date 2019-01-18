package com.example.misch.androidexv1;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.misch.androidexv1.calculatorAPI.CalculatorFragment;
import com.example.misch.androidexv1.cameraAPI.SensorFragment;
import com.example.misch.androidexv1.contactsAPI.ContactsFragment;
import com.example.misch.androidexv1.gitHubAuth.GitHubRepoListFragment;
import com.example.misch.androidexv1.googleMapAPI.GogleMap;
import com.example.misch.androidexv1.infoAPI.InfoPresenter;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CalculatorFragment.OnFragmentInteractionListener,
        SensorFragment.OnFragmentInteractionListener,GogleMap.OnFragmentInteractionListener,
        GitHubRepoListFragment.OnFragmentInteractionListener, InfoPresenter.OnFragmentInteractionListener{
    private TextView username;

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
    public static ArrayList<String> repoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        contextOfApplication = getApplicationContext();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        username = (TextView) findViewById(R.id.usernameAndroid);
        username.setText(getIntent().getExtras().getString("login"));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = new Fragment();
        Class FragmentClass = null;
        int id = item.getItemId();
        if (id == R.id.nav_calculator) {
            FragmentClass = CalculatorFragment.class;
            setTitle(item.getTitle());
        } else if (id == R.id.nav_map) {
            FragmentClass = GogleMap.class;
            Fragment mMapFragment = null;
            item.setChecked(true);
            setTitle(item.getTitle());
        } else if (id == R.id.nav_camera) {
            FragmentClass = SensorFragment.class;
            setTitle(item.getTitle());
        } else if (id == R.id.nav_info) {
            FragmentClass = InfoPresenter.class;
            setTitle(item.getTitle());
        } else if (id == R.id.nav_manage) {
            FragmentClass = GitHubRepoListFragment.class;
            repoList = getIntent().getStringArrayListExtra("repoList");
            setTitle(item.getTitle());
        } else if (id == R.id.nav_contacts) {
            FragmentClass = ContactsFragment.class;
            setTitle(item.getTitle());
        } else if (id == R.id.nav_finish) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAndRemoveTask();
            } else {
                finishAffinity();
            }
        }else {
            return false;
        }

        try {
            fragment = (Fragment) FragmentClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.getLocalizedMessage();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public ArrayList<String> getRepoList () {
        return null;
    }




}
