package Controleur;

import Modele.Attraction;
import Modele.Client;
import Modele.Reservation;

import java.util.ArrayList;
import java.util.Objects;

public class CalculReglement {
    private ArrayList<Reservation> toutLesReservations;

    public CalculReglement(ArrayList<Reservation> toutLesReservations, Client client) {
        this.toutLesReservations=toutLesReservations;
    }
    public double calculPrixTotal(Client client) {
        double total=0;
        double prix=0;
        int nombre=0;
        if (!toutLesReservations.isEmpty()){
            for (int i = 0; i < toutLesReservations.size(); i++) {
                Reservation reservation = toutLesReservations.get(i);
                if (reservation.getClientId()==client.getClientId()){
                    prix=reservation.getReservationPrix();
                    nombre=reservation.getReservationNbPersonnes();
                    total=total+prix*nombre;
                }
            }
        }
        return total;
    }
}
