package com.utilisateur.orthomem.model;

public class Word {
    private String id;
    private String label;
    private Long nbsyllabes;
    private boolean isSelected;

    public Word() {}

    public Word(String wordId, String wordLabel, Long nbOfSyllabes) {
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

    public Long getNbsyllabes() {
        return nbsyllabes;
    }

    public void setNbsyllabes(Long nbsyllabes) {
        this.nbsyllabes = nbsyllabes;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
