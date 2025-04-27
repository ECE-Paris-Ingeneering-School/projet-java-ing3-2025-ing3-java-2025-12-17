package DAO;

// import des packages

import java.util.ArrayList;
import Modele.Reservation;

public interface ReservationDAO {
    public void ajouterReservation(Reservation reservation);
    public void supprimerReservation(int reservationId);
    public void modifierReservation(Reservation reservation);
    public Reservation getReservation(int reservationId);
    public ArrayList<Reservation> getAllReservations();
}
