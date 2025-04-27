package Controleur;

import DAO.ReservationDAOImpl;
import DAO.ReservationDAO;
import DAO.DaoFactory;
import DAO.ReservationDAOImpl;
import Modele.Attraction;
import Modele.Client;
import Modele.Reservation;

import java.sql.Date;
import java.time.LocalDate;

public class NouveauRDV {
    public NouveauRDV(DaoFactory dao, LocalDate date, Attraction attraction,int nbBillet,int prix, MainFrame mainFrame) {
        Client client = mainFrame.getClientConnecte();
        int tempid = 0;
        if (client != null) {
            tempid = client.getClientId();
        }else {
            tempid = 0;
        }
        System.out.println("id client : " + tempid);
        Reservation reservation = new Reservation(Date.valueOf(date),nbBillet,prix,tempid,attraction.getAttractionId());
        ReservationDAO reservationDAO = new ReservationDAOImpl(dao);
        reservationDAO.ajouterReservation(reservation);
    }
}
