package com.utilisateur.orthomem.model;

import android.support.annotation.Nullable;

public class User {
    private String uid;
    private String username;
    private Boolean isOrtho;

    public User() { }

    public User(String uid, String username) {
        this.uid = uid;
        this.username = username;
        this.isOrtho = false;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public Boolean getIsMentor() { return isOrtho; }

    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setIsOrtho(Boolean isortho) { isOrtho = isortho; }
}
