package DAO;

import Modele.Reservation;
import java.sql.*;
import java.util.ArrayList;

public class ReservationDAOImpl implements ReservationDAO {
    private DaoFactory daoFactory;

    public ReservationDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouterReservation(Reservation reservation) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement("insert into reservation (DateReservation, NombreBillets, PrixUnBillet, IdClient, IdAttraction) values(?,?,?,?,?)");
            preparedStatement.setDate(1, reservation.getReservationDate());
            preparedStatement.setInt(2, reservation.getReservationNbPersonnes());
            preparedStatement.setDouble(3, reservation.getReservationPrix());
            preparedStatement.setInt(4, reservation.getClientId());
            preparedStatement.setInt(5, reservation.getIdAttraction());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de la réservation impossible");
        }
    }

    @Override
    public void supprimerReservation(int reservationId) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement("delete from reservation where IdReservation = ?");
            preparedStatement.setInt(1, reservationId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression de la réservation impossible");
        }
    }

    @Override
    public void modifierReservation(Reservation reservation) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement("update reservation set IdClient = ?, DateReservation = ?, NombreBillets = ?, NombreTables = ? where IdReservation = ?");
            preparedStatement.setInt(1, reservation.getClientId());
            preparedStatement.setDate(2, reservation.getReservationDate());
            preparedStatement.setInt(3, reservation.getReservationNbPersonnes());
            preparedStatement.setDouble(4, reservation.getReservationPrix());
            preparedStatement.setInt(5, reservation.getIdAttraction());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification de la réservation impossible");
        }
    }

    @Override
    public Reservation getReservation(int reservationId) {
        Reservation reservation = null;
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement("select * from reservation where IdReservation = ?");
            preparedStatement.setInt(1, reservationId);
            ResultSet resultats = preparedStatement.executeQuery();
            if (resultats.next()) {
                reservation = new Reservation(resultats.getInt(1),resultats.getDate(2), resultats.getInt(3), resultats.getDouble(4), resultats.getInt(5), resultats.getInt(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération de la réservation impossible");
        }
        return reservation;
    }

    @Override
    public ArrayList<Reservation> getAllReservations() {
        ArrayList<Reservation> listeReservations = new ArrayList<Reservation>();
        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery("select * from reservation");
            while (resultats.next()) {
                Reservation reservation = new Reservation(resultats.getInt(1),resultats.getDate(2), resultats.getInt(3), resultats.getDouble(4), resultats.getInt(5), resultats.getInt(6));
                listeReservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération de la liste des réservations impossible");
        }
        return listeReservations;
    }
}
