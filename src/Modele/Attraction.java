package Modele;

public class Attraction {
    private int attractionId;
    private String attractionNom;
    private float[] attractionPrix;

    // constructeur
    public Attraction(int attractionId, String attractionNom, float[] attractionPrix) {
        this.attractionId = attractionId;
        this.attractionNom = attractionNom;
        this.attractionPrix = attractionPrix;
    }

    // getters
    public int getAttractionId() {
        return attractionId;
    }

    public String getAttractionNom() {
        return attractionNom;
    }

    public float[] getAttractionPrix() {
        return attractionPrix;
    }
}
