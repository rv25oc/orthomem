package com.utilisateur.orthomem.api;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.utilisateur.orthomem.model.Exercice;
import com.utilisateur.orthomem.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserHelper {
    private static final String TAG = "";
    private static final String COLLECTION_NAME = "users";
    private static final String UNDER_COLLECTION_NAME = "usersfavorites";
    private static boolean isfavorite;

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static CollectionReference getFavoritesCollection(String uid) {
        return UserHelper.getUsersCollection().document(uid).collection("favorites");
    }


    // --- CREATE ---

    public static Task<DocumentReference> addFavorite(String uid, String Exerciceid) {

        Map<String, Object> myFav = new HashMap<>();
        myFav.put("exerciceid", Exerciceid);

        return UserHelper.getFavoritesCollection(uid)
                .add(myFav)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Favorite added: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding Favorite", e);
                    }
                });

    }
/*
    public static Task<Void> createUser(String uid, String username) {
        User userToCreate = new User(uid, username);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }
*/
    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid){
        return UserHelper.getUsersCollection().document(uid).get();
    }

    public static Task<QuerySnapshot> getFavorites(String uid) {
        return UserHelper.getFavoritesCollection(uid).get();
    }

    // --- TOGGLE FAVORITE ---
    public static void toogleFavorite(final String uid,final String exerciceid) {

        Query myQuery = UserHelper.getFavoritesCollection(uid).whereEqualTo("exerciceid", exerciceid);
        myQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size()==0) {
                                UserHelper.addFavorite(uid, exerciceid);
                            }
                            else{
                                UserHelper.deleteFavorite(uid, exerciceid);
                            }
                        } else {
                            Log.w(TAG, "(task) Error getting DocumentSnapshot FavoriteExercice", task.getException());
                        }
                    }
                });
        }

/*
    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }

    public static Task<Void> updateIsMentor(String uid, Boolean isOrtho) {
        return UserHelper.getUsersCollection().document(uid).update("isortho", isOrtho);
    }
*/
    // --- DELETE ---

    public static Task<Void> deleteFavorite(String uid, String Exerciceid) {
        return UserHelper.getFavoritesCollection(uid).document(Exerciceid).delete();

    }

    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }


}