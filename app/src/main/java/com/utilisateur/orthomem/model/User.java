package com.utilisateur.orthomem.model;

import android.support.annotation.Nullable;

public class User {
    private String uid;
    private String label;
    private Boolean isOrtho;

    public User() { }

    public User(String uid, String username, Boolean isortho) {
        this.uid = uid;
        this.label = username;
        this.isOrtho = isortho;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getLabel() { return label; }
    public Boolean getIsOrtho() { return isOrtho; }

    // --- SETTERS ---
    public void setLabel(String username) { this.label = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setIsOrtho(Boolean isortho) { isOrtho = isortho; }
}
