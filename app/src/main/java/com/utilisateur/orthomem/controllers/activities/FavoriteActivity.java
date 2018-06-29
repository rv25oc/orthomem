package com.utilisateur.orthomem.controllers.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utilisateur.orthomem.R;

public class FavoriteActivity extends FragmentActivity {

    /**********
     *
     * 1. Affichage de l'utilisateur connecté dans le msg de bienvenue
     * 2. Connexion à l'API FIREBASE / FIRESTORE
     * 3. Affichage de la liste des Favoris de l'utilisateur connecté
     *
     **********/


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        }
    }

