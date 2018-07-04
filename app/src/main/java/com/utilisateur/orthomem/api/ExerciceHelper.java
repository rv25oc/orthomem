package com.utilisateur.orthomem.api;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class ExerciceHelper {

    private static final String COLLECTION_NAME = "exercices";
    private static final String UNDER_COLLECTION_NAME = "words";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getExercicesCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

}
