package com.utilisateur.orthomem.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.google.firebase.firestore.ServerTimestamp;

public class Exercice implements Serializable {
    private String id;
    private String label;
    private String goal;
    private List<String> exercicewords;
    private Date creadate;


    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal, List<String> wordsIdList) {
        this.id = exerciceId;
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
        this.exercicewords = wordsIdList;
    }

    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal) {
        this.id = exerciceId;
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
    }

    public Exercice() {
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

    public List<String> getExercicewords() {
        return exercicewords;
    }

    public void setExercicewords(List<String> wordsIdList) {
        this.exercicewords = wordsIdList;
    }

    @ServerTimestamp public Date getCreadate() { return creadate; }

    public void setCreadate(Date creadate) { this.creadate = creadate;}

}


/*

    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal, List<String> wordsIdList,String ownerId ) {
        this.id = exerciceId;
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
        this.exercicewords = wordsIdList;
        this.ownerid = ownerId;
    }

    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal, Date creadate) {
        this.id = exerciceId;
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
        this.creadate = creadate;
    }

    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal, Date creadate, String ownerid) {
        this.id = exerciceId;
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
        this.creadate = creadate;
    }

    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal, Date creadate, String ownerid, List<String> wordsIdList) {
        this.id = exerciceId;
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
        this.creadate = creadate;
        this.ownerid = ownerid;
        this.exercicewords = wordsIdList;
    }


    private String ownerid;
    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }
*/
