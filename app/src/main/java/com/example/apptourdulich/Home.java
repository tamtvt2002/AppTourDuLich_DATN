package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.IntentFilter;
import android.net.ConnectivityManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.apptourdulich.Fragments.fmLocation;
import com.example.apptourdulich.Fragments.fmProfile;
import com.example.apptourdulich.Fragments.fmPromo;
import com.example.apptourdulich.Fragments.fmTour;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    ActionBar toolbar;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        Bundle b = getIntent().getExtras();
        String sdt = b.getString("SoDienThoai");

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        getSupportFragmentManager().beginTransaction().replace(R.id.fm_Container,
//                new fmProfile()).commit();
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fm_Container,
//                new fmHome()).commit();

        loadFragment(new fmHome());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
//            switch (item.getItemId()) {
//                case R.id.navigation_tour:
//                    fragment = new fmTour();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_promo:
//                    fragment=new fmPromo();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_home:
//                    fragment = new fmHome();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_location:
//                    fragment=new fmLocation();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_profile:
//                    fragment=new fmProfile();
//                    loadFragment(fragment);
//                    return true;
//
//            }

            if (item.getItemId() == R.id.navigation_tour) {
                fragment = new fmTour();
                loadFragment(fragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_promo) {
                fragment = new fmPromo();
                loadFragment(fragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_home) {
                fragment = new fmHome();
                loadFragment(fragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_location) {
                fragment = new fmLocation();
                loadFragment(fragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_profile) {
                fragment = new fmProfile();
                loadFragment(fragment);
                return true;
            }

            return false;
        }


    };

    private void loadFragment(Fragment fragment) {
        // load Fragment
        Bundle b = getIntent().getExtras();
        String sdt = b.getString("SoDienThoai");
        Bundle bundle = new Bundle();
        bundle.putString("SoDienThoai", sdt);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fm_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListener);
    }

}