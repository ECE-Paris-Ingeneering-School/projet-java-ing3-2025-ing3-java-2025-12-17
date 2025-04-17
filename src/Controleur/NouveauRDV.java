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
    public NouveauRDV(DaoFactory dao, Client client, LocalDate date, Attraction attraction,int nbBillet,int prix) {
        Reservation reservation = new Reservation(Date.valueOf(date),nbBillet,client.getClientId(),prix,attraction.getAttractionId());

        ReservationDAO reservationDAO = new ReservationDAOImpl(dao);
        reservationDAO.ajouterReservation(reservation);
    }
}
