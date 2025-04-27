//CODE REPRIS DU TP JAVA ECE DAO
package DAO;

// import des packages

import java.util.ArrayList;
import Modele.Attraction;

public interface AttractionDAO {
    public void ajouterAttraction(Attraction attraction);
    public void supprimerAttraction(int attractionId);
    public void modifierAttraction(Attraction attraction);
    public Attraction getAttraction(int attractionId);
    public ArrayList<Attraction> getAllAttractions();
}
