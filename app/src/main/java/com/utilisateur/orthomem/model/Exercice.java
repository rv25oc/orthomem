package com.utilisateur.orthomem.model;

import java.io.Serializable;
import java.util.List;

public class Exercice implements Serializable {
    private String mexerciceId;
    private String mexerciceLabel;
    private String mexerciceGoal;
    private List<String> mWordsList;
    //private String imageUrl;

    public Exercice(String exerciceLabel, String exerciceGoal) {
        this.mexerciceLabel = exerciceLabel;
        this.mexerciceGoal = exerciceGoal;
    }

    public Exercice(String exerciceId, String exerciceLabel, String exerciceGoal, List<String> wordsList) {
        this.mexerciceId = exerciceId;
        this.mexerciceLabel = exerciceLabel;
        this.mexerciceGoal = exerciceGoal;
        this.mWordsList = wordsList;
    }


    //getters & setters

    public String getExerciceId() {
        return mexerciceId;
    }

    public void setExerciceId(String exercicieId) {
        this.mexerciceId = exercicieId;
    }

    public String getExerciceLabel() {
        return mexerciceLabel;
    }

    public void setExercicieLabel(String exerciceLabel) {
        this.mexerciceLabel = exerciceLabel;
    }


    public String getExerciceGoal() {
        return mexerciceGoal;
    }

    public void setExerciceGoal(String exerciceGoal) {
        this.mexerciceGoal = exerciceGoal;
    }


    public List<String> getWordsList() {
        return mWordsList;
    }

    public void setWordsList(List<String> wordsList) {
        this.mWordsList = wordsList;
    }

}