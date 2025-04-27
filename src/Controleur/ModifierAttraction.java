package Controleur;

import Controleur.CalculReglement;
import Controleur.MainFrame;
import DAO.AttractionDAO;
import DAO.AttractionDAOImpl;
import DAO.DaoFactory;
import DAO.ReservationDAOImpl;
import DAO.ReservationDAO;
import Modele.Attraction;
import Modele.Client;
import Modele.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModifierAttraction {
    public ModifierAttraction(DaoFactory dao, Attraction attraction, String nom, String type, String pc, String pr, String pj, String ps, String description) {
        if (attraction != null) {
            int id=attraction.getAttractionId();
            Attraction attractionTampon = new Attraction(id,nom,type, pc,pr,pj,ps,description);
            AttractionDAOImpl attractionDAO = new AttractionDAOImpl(dao);
            attractionDAO.modifierAttraction(attractionTampon);
        }
    }
}
