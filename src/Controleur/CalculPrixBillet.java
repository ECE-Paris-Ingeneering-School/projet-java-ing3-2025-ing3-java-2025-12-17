package Controleur;

import Modele.Attraction;
import Modele.Client;

import java.util.Objects;

public class CalculPrixBillet {
    private int prix;
    public CalculPrixBillet() {
        this.prix=0;
    }
    public int calculPrixDuBillet(Client client, Attraction attraction) {
        String type = client.gettypeClient();
        return switch (type.toLowerCase()) {
            case "jeune" ->(int) Float.parseFloat(attraction.getAttractionPrixJeune());
            case "senior" ->(int) Float.parseFloat(attraction.getAttractionPrixSenior());
            case "complet" ->(int) Float.parseFloat(attraction.getAttractionPrixComplet());
            case "reduit" ->(int) Float.parseFloat(attraction.getAttractionPrixHab());
            default -> throw new IllegalArgumentException("Type de client inconnu : " + type);
        };
    }
}
