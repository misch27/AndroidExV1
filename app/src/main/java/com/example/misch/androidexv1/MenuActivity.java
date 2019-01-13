package com.example.misch.androidexv1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
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

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CalculatorFragment.OnFragmentInteractionListener,
        SensorFragment.OnFragmentInteractionListener,GogleMap.OnFragmentInteractionListener,
        GitHubRepoListFragment.OnFragmentInteractionListener{


    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
    public static ArrayList<String> repoList;

    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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
        int id = item.getItemId();
        if (id == R.id.nav_calculator) {
            Fragment fragment = new Fragment();
            Class FragmentClass = CalculatorFragment.class;
            try{
                fragment = (Fragment) FragmentClass.newInstance();
            }catch (IllegalAccessException | InstantiationException e){
                e.getLocalizedMessage();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
            item.setChecked(true);
            setTitle(item.getTitle());
        } else if (id == R.id.nav_map) {
            Class google = GogleMap.class;
            Fragment mMapFragment = null;
            try {
                mMapFragment = (Fragment) google.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,mMapFragment).commit();
            item.setChecked(true);
            setTitle(item.getTitle());
        } else if (id == R.id.nav_camera) {

            Fragment fragment = new Fragment();
            Class FragmentClass = SensorFragment.class;
            try{
                fragment = (Fragment) FragmentClass.newInstance();
            }catch (IllegalAccessException | InstantiationException e){
                e.getLocalizedMessage();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
            item.setChecked(true);
            setTitle(item.getTitle());

        } else if (id == R.id.nav_manage) {
            Fragment fragment = null;
            Class FragmentClass = GitHubRepoListFragment.class;
            repoList = getIntent().getStringArrayListExtra("repoList");

            try {
                fragment = (Fragment) FragmentClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.getLocalizedMessage();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
            item.setChecked(true);
            setTitle(item.getTitle());

        } else if (id == R.id.nav_contacts) {
            Fragment fragment = new Fragment();
            Class FragmentClass = ContactsFragment.class;
            try{
                fragment = (Fragment) FragmentClass.newInstance();
            }catch (IllegalAccessException | InstantiationException e){
                e.getLocalizedMessage();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
            item.setChecked(true);
            setTitle(item.getTitle());
        }


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
