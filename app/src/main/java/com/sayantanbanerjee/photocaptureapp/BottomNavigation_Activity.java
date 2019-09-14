package com.sayantanbanerjee.photocaptureapp;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sayantanbanerjee.photocaptureapp.fragments.AboutMeFragment;
import com.sayantanbanerjee.photocaptureapp.fragments.AllPhotosFragment;
import com.sayantanbanerjee.photocaptureapp.fragments.HomePageFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class BottomNavigation_Activity extends AppCompatActivity {
    Fragment selectedFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomePageFragment();
                    break;
                case R.id.navigation_allphotos:
                    selectedFragment = new AllPhotosFragment();
                    break;
                case R.id.navigation_aboutme:
                    selectedFragment = new AboutMeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomePageFragment()).commit();
    }

}
