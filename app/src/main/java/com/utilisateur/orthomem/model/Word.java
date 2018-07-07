package com.utilisateur.orthomem.model;

public class Word {
    private String id;
    private String label;
    private Integer nbsyllabes;
    private boolean isSelected;

    public Word() {}

    public Word(String wordId, String wordLabel, Integer nbOfSyllabes) {
        this.id = wordId;
        this.label = wordLabel;
        this.nbsyllabes = nbOfSyllabes;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getNbsyllabes() {
        return nbsyllabes;
    }

    public void setNbsyllabes(Integer nbsyllabes) {
        this.nbsyllabes = nbsyllabes;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
