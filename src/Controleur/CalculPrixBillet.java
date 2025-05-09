package Controleur;

import Modele.Attraction;
import Modele.Client;

import java.util.Objects;

public class CalculPrixBillet {
    private int prix;
    public CalculPrixBillet() {
        this.prix=0;
    }
    public int calculPrixDuBillet(Attraction attraction, MainFrame mainFrame) {

        Client client = mainFrame.getClientConnecte();
        String type = "complet";
        if (client != null) {
            type = client.gettypeClient();
        }
        switch (type) {
            case "jeune":
                prix = (int) Double.parseDouble(attraction.getAttractionPrixJeune());
                break;
            case "senior":
                prix = (int) Double.parseDouble(attraction.getAttractionPrixSenior());
                break;
            case "complet":
                prix = (int) Double.parseDouble(attraction.getAttractionPrixComplet());
                break;
            case "reduit":
                prix = (int) Double.parseDouble(attraction.getAttractionPrixHab());
                break;
        }
        return prix;
    }
}
