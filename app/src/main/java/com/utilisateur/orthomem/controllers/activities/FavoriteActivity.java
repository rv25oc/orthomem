package com.utilisateur.orthomem.controllers.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

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
        setContentView(R.layout.fragment_favorite);
        }
    }

