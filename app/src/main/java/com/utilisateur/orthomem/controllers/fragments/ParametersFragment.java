// ParametersFragment.java
package com.utilisateur.orthomem.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.activities.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ParametersFragment extends Fragment {
    @BindView(R.id.button_logout)
    Button mLogoutButton;

    private FirebaseAuth mAuth;

    public ParametersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_parameters, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAuth = FirebaseAuth.getInstance();

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNextActivity("logout");
            }
        });
    }

    private void startNextActivity(String type) {
        switch (type) {
            case "logout":
                mAuth.signOut();
                Toast.makeText(getContext(), R.string.home_logout, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(getActivity(), HomeActivity.class);
                startActivity(myIntent);
                break;
        }
    }
}