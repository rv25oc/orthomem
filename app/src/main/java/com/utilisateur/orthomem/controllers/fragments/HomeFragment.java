package com.utilisateur.orthomem.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.model.User;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class HomeFragment extends Fragment {

    /**********
     *
     * 1. Connextion par email en dur à la bdd à l'aide de l'API AUTHENTICATION et Information du statut de la tentative de connexion l'utilisateur par TOAST
     * 2.
     *
     **********/

    private static final String TAG = "";
    private BottomNavigationView mNavigation;
    private ImageView mLogo;
    private TextView mStatusTextView;
    private Button mOrthoButton;
    private Button mPatientButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mBdd;
    private FirebaseUser mUser;


    public HomeFragment() {/*Required empty public constructor*/}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        mAuth.signOut();
        Toast.makeText(getContext(), R.string.home_logout, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLogo = view.findViewById(R.id.logo);
        mOrthoButton = view.findViewById(R.id.home_ortho_button);
        mPatientButton = view.findViewById(R.id.home_patient_button);
        mStatusTextView = view.findViewById(R.id.home_status);

        mNavigation = this.getActivity().findViewById(R.id.navigation);
        mNavigation.setVisibility(GONE);

        mBdd = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        mOrthoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signIn("ortho@mail.mail", "orthomem");
            }
        });

        mPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn("patient@mail.mail", "orthomem");
            }
        });
    }

    private void signIn(final String email, String password) {
        // [START sign_in_with_email]

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.w(TAG, "signInWithEmail:success => " + mAuth.getCurrentUser().getUid() + " | " + mAuth.getCurrentUser().getEmail());
                            //Toast.makeText(getApplicationContext(), "L'utilisateur " + mAuth.getCurrentUser().getEmail() + " a bien été connecté.", Toast.LENGTH_LONG).show();
                            updateUI(mAuth.getCurrentUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Echec de connexion " + email.toString() + " : " + R.string.auth_failed, Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {

            mBdd.collection("users").document(user.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    document.getData();

                                    User myUser = new User(document.getId(), document.get("label").toString(), (Boolean) document.get("isortho"));


                                    if (!myUser.getIsOrtho()) {
                                        mNavigation.findViewById(R.id.navigation_addlist).setVisibility(GONE);
                                    } else {
                                        mNavigation.findViewById(R.id.navigation_addlist).setVisibility(VISIBLE);
                                    }

                                    //mStatusTextView.setText("Connecté en tant qu'orthophoniste ? " + myUser.getIsOrtho());
                                    mLogo.setImageResource(R.drawable.logo_orthomem_600x600_color);
                                    mNavigation.setVisibility(VISIBLE);
                                    mStatusTextView.setText(getContext().getResources().getString(R.string.home_welcome) + myUser.getLabel());
                                    //startNextActivity(myUser.getUid(), myUser.getLabel());

                                } else {
                                    Log.d(TAG, "No such document", task.getException());
                                    mStatusTextView.setText("No such document Users : " + task.getException());
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                                mStatusTextView.setText("ko task : " + task.getException());
                            }
                        }
                    });
        } else {
            mLogo.setImageResource(R.drawable.logo_orthomem_600x600_grey);
            mNavigation.setVisibility(GONE);
        }
    }

}
