package com.utilisateur.orthomem.api;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;

public class App extends Application {
    // Create the instance
    private static App instance;

    public App() {
        // Constructor hidden because this is a singleton
    }

    public static App getInstance() {
        if (instance == null) {
            synchronized (App.class) {
                if (instance == null)
                    instance = new App();
            }
        }
        // Return the instance
        return instance;
    }

    public void initFirebaseSdk() {
        //FacebookSdk.sdkInitialize(getApplicationContext());
        FirebaseFirestore.getInstance();
    }
}
