package com.utilisateur.orthomem.controllers.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.fragments.MyListsFragment;

public class MyListsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylists);

        if (savedInstanceState == null) {
            MyListsFragment myFragment = new MyListsFragment();
            getSupportFragmentManager().beginTransaction().add(
                    android.R.id.content, myFragment).commit();
        }
    }
}
