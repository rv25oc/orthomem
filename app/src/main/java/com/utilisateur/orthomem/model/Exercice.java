package com.utilisateur.orthomem.model;

import java.io.Serializable;
import java.util.List;

public class Exercice implements Serializable {
    private String id;
    private String label;
    private String goal;
    private List<String> exercicewords;
    //private String imageUrl;

    public Exercice() {}

    public Exercice(String exerciceLabel, String exerciceGoal) {
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
    }

    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal) {
        this.id = exerciceId;
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
    }

    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal, List<String> wordsIdList) {
        this.id = exerciceId;
        this.label = exerciceLabel;
        this.goal = exerciceGoal;
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


    public List<String> getExercicewords() {
        return exercicewords;
    }

    public void setExercicewords(List<String> wordsIdList) {
        this.exercicewords = wordsIdList;
    }

}