package com.utilisateur.orthomem.api;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;
import com.utilisateur.orthomem.model.Exercice;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExerciceHelper {
    private static final String TAG = "";
    private static final String COLLECTION_NAME = "exercices";
    private static final String UNDER_COLLECTION_NAME = "exercicewords";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getExercicesCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }


    // --- CREATE EXERCICE
    public static Task<DocumentReference> createExercice(String user, String label, String goal, List<String> wordids) {

        final List<String> myIds;
        myIds=wordids;

        Map<String, Object> myExercice = new HashMap<>();
        myExercice.put("label", label);
        myExercice.put("goal", goal);
        myExercice.put("ownerid", user);
        myExercice.put("creadate", Calendar.getInstance().getTime());


        return ExerciceHelper.getExercicesCollection()
                .add(myExercice)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());

                        for (int index = 0; index < myIds.size(); index++) {

                            Map<String, Object> myWord = new HashMap<>();
                            myWord.put("id", myIds.get(index));

                            ExerciceHelper.getExercicesCollection().document(documentReference.getId()).collection(UNDER_COLLECTION_NAME)
                                    .add(myWord)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "WordExercice ajouté" + documentReference.getId());
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding word in exercice", e);
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding exercice", e);
                    }
                });
    }
}

