// MainActivity.java
package com.utilisateur.orthomem.controllers.activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.fragments.AddListFragment;
import com.utilisateur.orthomem.controllers.fragments.FavoriteFragment;
import com.utilisateur.orthomem.controllers.fragments.HomeFragment;
import com.utilisateur.orthomem.controllers.fragments.MyListsFragment;
import com.utilisateur.orthomem.controllers.fragments.ParametersFragment;


public class MainActivity extends FragmentActivity {

    /**********
     *
     * 1. Initialisation de la BottomNavigationView
     *
     **********/
    private static final String TAG="";
    private BottomNavigationView mNavigation;
    private FirebaseFirestore mBdd;

    @Override
    protected void onResume() {
        super.onResume();
        //Crashlytics.getInstance().crash();  // Force application to crash for testing CrashLytic service from firebase
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //mNavigation.setVisibility(GONE);

        mBdd = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mBdd.setFirestoreSettings(settings); //Permet d'utiliser les TIMESTAMPS de Firebase
        showFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.w(TAG, "Navigation case : home");
                    showFragment(new HomeFragment());
                    return true;
                case R.id.navigation_favorite:
                    Log.w(TAG, "Navigation case : favorite");
                    showFragment(new FavoriteFragment());
                    return true;
                case R.id.navigation_mylists:
                    Log.w(TAG, "Navigation case : mylists");
                    showFragment(new MyListsFragment());
                    return true;
                case R.id.navigation_addlist:
                    Log.w(TAG, "ONavigation case : addlist");
                    showFragment(new AddListFragment());
                    return true;
                case R.id.navigation_parameters:
                    Log.w(TAG, "Navigation case : parameters");
                    showFragment(new ParametersFragment());
                    return true;
            }
            return false;
        }
    };

    // Rattachement du Fragment à l'activité
    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
