// MainActivity.java
package com.utilisateur.orthomem.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.ExerciceListRecyclerViewAdapter;
import com.utilisateur.orthomem.controllers.fragments.AddListFragment;
import com.utilisateur.orthomem.controllers.fragments.FavoriteFragment;
import com.utilisateur.orthomem.controllers.fragments.MyListsFragment;
import com.utilisateur.orthomem.controllers.fragments.ParametersFragment;
import com.utilisateur.orthomem.model.Exercice;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity /*implements MyListsFragment.OnButtonClickedListener*/ {

    @Override
    // Affichage du layout et de la BottomNavigationView
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        showFragment(new FavoriteFragment());

        }

    @Override
    protected void onResume() {
        super.onResume();
        //Crashlytics.getInstance().crash();  // Force application to crash for testing CrashLytic service from firebase
    }

    // Affichage du fragment selon l'option cliquée
    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favorite:
                    showFragment(new FavoriteFragment());
                    return true;
                case R.id.navigation_mylists:
                    showFragment(new MyListsFragment());
                    return true;
                case R.id.navigation_addlist:
                    showFragment(new AddListFragment());
                    return true;
                case R.id.navigation_parameters:
                    showFragment(new ParametersFragment());
                    return true;
            }
            return false;
        }
    };
/*
    @Override
    public void onButtonClicked(View view) {
        Log.e(getClass().getSimpleName(),"MyLists Exo clicked : Trace src:Mainactivity !");
        startActivity(new Intent(this, MyListActivity.class));
    }
*/
}