package com.utilisateur.orthomem.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import com.google.firebase.firestore.ServerTimestamp;

public class Exercice implements Serializable {
    private String id;
    private String label;
    private String goal;
    private ArrayList<String> exercicewords;
    private Date creadate;

    public Exercice() {}

    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal, Date exerciceCreadate, ArrayList<String> wordsIdList) {
        this.id = exerciceId;
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
        this.creadate = exerciceCreadate;
        this.exercicewords = wordsIdList;
    }


    //getters & setters
    public String getId() {
        return id;
    }

    public void setId(String exerciceId) {
        this.id = exerciceId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String exerciceLabel) {
        this.label = exerciceLabel;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String exerciceGoal) {
        this.goal = exerciceGoal;
    }

    public ArrayList<String> getExercicewords() {
        return exercicewords;
    }

    public void setExercicewords(ArrayList<String> wordsIdList) {
        this.exercicewords = wordsIdList;
    }

    @ServerTimestamp public Date getCreadate() { return creadate; }

    public void setCreadate(Date creadate) { this.creadate = creadate;}

}
