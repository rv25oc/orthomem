package com.utilisateur.orthomem.api;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class ExerciceHelper {

    private static final String COLLECTION_NAME = "Exercices";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getExerciciesCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }
}
