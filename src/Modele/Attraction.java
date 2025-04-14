package Modele;

import java.awt.*;

public class Attraction {
    private String attractionType;
    private String attractionNom;
    private Image attractionImage;
    private String attractionPrixComplet;
    private String attractionPrixHab;
    private String attractionPrixJeune;
    private String attractionPrixSenior;
    private String attractionDescription;


    // constructeur
    public Attraction(String attractionType, String attractionNom, Image attractionImage, String attractionPrixComplet, String attractionPrixHab, String attractionPrixJeune, String attractionPrixSenior, String attractionDescription) {
        this.attractionType=attractionType;
        this.attractionNom = attractionNom;
        this.attractionImage = attractionImage;
        this.attractionPrixComplet = attractionPrixComplet;
        this.attractionPrixHab = attractionPrixHab;
        this.attractionPrixJeune = attractionPrixJeune;
        this.attractionPrixSenior = attractionPrixSenior;
        this.attractionDescription = attractionDescription;
    }

    // getters
    //public int getAttractionId() {
    //    return attractionId;
    //}
    public String getAttractionType() {
        return attractionType;
    }

    public String getAttractionNom() {
        return attractionNom;
    }
    public Image getAttractionImage() {
        return attractionImage;
    }
    public String getAttractionPrixComplet() {
        return attractionPrixComplet;
    }
    public String getAttractionPrixHab() {
        return attractionPrixHab;
    }
    public String getAttractionPrixJeune() {
        return attractionPrixJeune;
    }
    public String getAttractionPrixSenior() {
        return attractionPrixSenior;
    }
    public String getAttractionDescription() {
        return attractionDescription;
    }

}
