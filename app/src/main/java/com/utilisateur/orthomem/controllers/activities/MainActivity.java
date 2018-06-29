// MainActivity.java
package com.utilisateur.orthomem.controllers.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.fragments.AddListFragment;
import com.utilisateur.orthomem.controllers.fragments.FavoriteFragment;
import com.utilisateur.orthomem.controllers.fragments.MyListsFragment;
import com.utilisateur.orthomem.controllers.fragments.ParametersFragment;



public class MainActivity extends FragmentActivity /*implements MyListsFragment.OnButtonClickedListener*/ {

    /**********
    *
    * 1. Initialisation de la BottomNavigationView
    * 2. Connextion par email en dur à la bdd à l'aide de l'API AUTHENTICATION et Information du statut de la tentative de connexion l'utilisateur par TOAST
    * 3. Lancement de l'activité FavoriteActivity dans le Fragment FavoriteFragment
    *
    **********/

    private static final String TAG = "FavoriteFragTAG init";

    private TextView mStatusTextView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mBdd;

    @Override
    // Affichage du layout et de la BottomNavigationView
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mAuth = FirebaseAuth.getInstance();
        mBdd = FirebaseFirestore.getInstance();



        //showFragment(new FavoriteFragment());
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        this.signIn("mail@mail.mail","orthomem" );
        //startFavoriteActivity();
        }


    @Override
    protected void onResume() {
        super.onResume();
        //Crashlytics.getInstance().crash();  // Force application to crash for testing CrashLytic service from firebase
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

    // Rattachement du Fragment à l'activité
    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"L'utilisateur user "+user.getEmail()+" a bien été connecté.", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Pb de connexion par email.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), R.string.auth_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        Toast.makeText(getApplicationContext(),"MainActivity UpdateUI() : ok", Toast.LENGTH_SHORT).show();

/*
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));

        } else {
            mStatusTextView.setText(R.string.signed_out);
        }

        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            mLoginButton.setVisibility(View.GONE);
            mLogoutButton.setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            mLoginButton.setVisibility(View.VISIBLE);
            mLogoutButton.setVisibility(View.GONE);
        }
    */
    }



    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }
    protected Boolean isCurrentUserLogged(){ return (getCurrentUser() != null); }
/*
    @Override
    public void onButtonClicked(View view) {
        Log.e(getClass().getSimpleName(),"MyLists Exo clicked : Trace src:Mainactivity !");
        startActivity(new Intent(this, MyListActivity.class));
    }
*/

    private void startFavoriteActivity(){

        Intent myIntent = new Intent(getBaseContext(), FavoriteActivity.class);
        startActivity(myIntent);
    }
}