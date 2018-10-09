package com.utilisateur.orthomem.controllers.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.utilisateur.orthomem.R;

public class HomeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
    }

}