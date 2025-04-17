package Modele;

import java.sql.Date;
import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private Date reservationDate;
    private int reservationNbPersonnes;
    private double reservationPrix;
    private int idAttraction;
    private int clientId;

    // constructeur
    public Reservation(Date reservationDate, int reservationNbPersonnes, int clientId, double reservationPrix, int idAttraction) {
        this.reservationDate = reservationDate;
        this.reservationNbPersonnes = reservationNbPersonnes;
        this.clientId = clientId;
        this.reservationPrix = reservationPrix;
        this.idAttraction = idAttraction;
    }

    // getters
    public int getReservationId() {
        return reservationId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public double getReservationPrix() {
        return reservationPrix;
    }

    public int getIdAttraction() {
        return idAttraction;
    }

    public int getReservationNbPersonnes() {
        return reservationNbPersonnes;
    }

    public int getClientId() {
        return clientId;
    }

}
