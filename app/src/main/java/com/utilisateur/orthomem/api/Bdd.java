package com.utilisateur.orthomem.api;


import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;

public class Bdd extends Application {
    // Create the instance
    private Bdd instance;

    private Bdd() {
        // Constructor hidden because this is a singleton
    }

    public Bdd getInstance() {
        if (instance == null) {
            synchronized (Bdd.class) {
                if (instance == null)
                    instance = new Bdd();
            }
        }
        // Return the instance
        return instance;
    }

    public void initBdd() {
        FirebaseFirestore.getInstance();
    }
}
