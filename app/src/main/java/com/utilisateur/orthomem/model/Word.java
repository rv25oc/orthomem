package com.utilisateur.orthomem.model;

public class Word {
    private String wordid;
    private String label;
    private Long nbsyllabes;
    private boolean isSelected;

    public Word() {}

    public Word(String wordId, String wordLabel, Long nbOfSyllabes, Boolean selected) {
        this.wordid = wordId;
        this.label = wordLabel;
        this.nbsyllabes = nbOfSyllabes;
        this.isSelected = selected;
    }

    public Word(String wordId, String wordLabel, Long nbOfSyllabes) {
        this.wordid = wordId;
        this.label = wordLabel;
        this.nbsyllabes = nbOfSyllabes;
    }

    public String getWordId() {
        return wordid;
    }

    public void setWordId(String id) {
        this.wordid = id;
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

    public boolean isSelected() {return isSelected;}

    public void setSelected(boolean selected) {isSelected = selected;}
}
