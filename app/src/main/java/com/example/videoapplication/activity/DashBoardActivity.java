package com.example.videoapplication.activity;

import android.os.Bundle;

import com.example.videoapplication.R;
import com.example.videoapplication.fragment.ChooseFormatFragment;
import com.example.videoapplication.fragment.ChooseImageFragment;
import com.example.videoapplication.fragment.HomeFragment;
import com.example.videoapplication.fragment.LibraryFragment;
import com.example.videoapplication.fragment.NewProjectFragment;
import com.example.videoapplication.fragment.PhotoFragment;
import com.example.videoapplication.fragment.VideoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.MediaStore;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.Menu;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        onHomeClick();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            onHomeClick();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_video) {

            onVideoClick();
        }
        if (id == R.id.nav_new_projet) {
            onNewProjClick();
        } else if (id == R.id.nav_photo) {

            onPhotoClick();

        } else if (id == R.id.nav_library) {

            onLibrary();

        } /*else if (id == R.id.rate) {

        } else if (id == R.id.contact) {

        }*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void LoadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    public void onHomeClick() {
        LoadFragment(new HomeFragment());
    }

    public void onPhotoClick() {
        toolbar.setTitle(getResources().getString(R.string.PHOTO));
        LoadFragment(new PhotoFragment());
    }

    public void onVideoClick() {
        toolbar.setTitle(getResources().getString(R.string.PRESENT_VIDEO));
        LoadFragment(new VideoFragment());
    }

    public void onNewProjClick() {
        toolbar.setTitle(getResources().getString(R.string.NEW_PROJECT));
        LoadFragment(new NewProjectFragment());
    }

    public void onLibrary() {
        toolbar.setTitle(getResources().getString(R.string.LIBRARY));
        LoadFragment(new LibraryFragment());
    }

    public void onNewProjectBtnClick()
    {
        toolbar.setTitle(getResources().getString(R.string.CHOOSE_FORMAT));
        LoadFragment(new ChooseFormatFragment());
    }

    public void onFormatClick() {
        toolbar.setTitle(getResources().getString(R.string.SELECT_PHOTO));
        LoadFragment(new ChooseImageFragment());
    }
}
