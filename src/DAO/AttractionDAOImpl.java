package DAO;

// import des packages
import Modele.Attraction;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class AttractionDAOImpl implements AttractionDAO {
    private DaoFactory daoFactory;

    public AttractionDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Attraction getAttraction(int attractionId) {
        Attraction attraction = null;

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement("select * from attraction where IdAttraction = ?");
            preparedStatement.setInt(1, attractionId);

            ResultSet resultats = preparedStatement.executeQuery();

            if (resultats.next()) {
                int clientId = resultats.getInt(1);
                String attractionType = resultats.getString(2);
                String attractionNom = resultats.getString(3);
                String attractionImage = resultats.getString(4);
                float attractionPrixComplet = resultats.getFloat(5);
                float attractionPrixHab = resultats.getFloat(6);
                float attractionPrixJeunes = resultats.getFloat(7);
                float attractionPrixSenior= resultats.getFloat(8);
                String attractionDescription = resultats.getString(9);
                String attractionPrixCompletStr = Float.toString(attractionPrixComplet);
                String attractionPrixHabStr = Float.toString(attractionPrixHab);
                String attractionPrixJeunesStr = Float.toString(attractionPrixJeunes);
                String attractionPrixSeniorStr = Float.toString(attractionPrixSenior);
                URL imageURL = getClass().getClassLoader().getResource(attractionImage);
                ImageIcon icon = new ImageIcon(imageURL);
                Image originalImage = icon.getImage();
                Image scaledImage = new ImageIcon(originalImage.getScaledInstance( 200, 200, Image.SCALE_SMOOTH)).getImage();

                attraction = new Attraction(clientId, attractionType, attractionNom, scaledImage, attractionPrixCompletStr, attractionPrixHabStr,attractionPrixJeunesStr,attractionPrixSeniorStr,attractionDescription);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération de l'attraction impossible");
        }

        return attraction;
    }

    @Override
    public ArrayList<Attraction> getAllAttractions() {
        ArrayList<Attraction> listeAttractions = new ArrayList<Attraction>();

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("select * from attraction");

            while (resultats.next()) {
                int attractionId = resultats.getInt(1);
                String attractionType = resultats.getString(2);
                String attractionNom = resultats.getString(3);
                String attractionImage = resultats.getString(4);
                float attractionPrixComplet = resultats.getFloat(5);
                float attractionPrixHab = resultats.getFloat(6);
                float attractionPrixJeunes = resultats.getFloat(7);
                float attractionPrixSenior= resultats.getFloat(8);
                String attractionDescription = resultats.getString(9);
                String attractionPrixCompletStr = Float.toString(attractionPrixComplet);
                String attractionPrixHabStr = Float.toString(attractionPrixHab);
                String attractionPrixJeunesStr = Float.toString(attractionPrixJeunes);
                String attractionPrixSeniorStr = Float.toString(attractionPrixSenior);
                URL imageURL = getClass().getClassLoader().getResource(attractionImage);
                    ImageIcon icon = new ImageIcon(imageURL);
                    Image originalImage = icon.getImage();
                    Image scaledImage = new ImageIcon(originalImage.getScaledInstance( 1536/7, 1024/7, Image.SCALE_SMOOTH)).getImage();
                Attraction attraction = new Attraction(attractionId, attractionType, attractionNom,scaledImage, attractionPrixCompletStr, attractionPrixHabStr, attractionPrixJeunesStr, attractionPrixSeniorStr,attractionDescription);

                listeAttractions.add(attraction);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste d'attractions impossible");
        }

        return listeAttractions;
    }

    @Override
    public void ajouterAttraction(Attraction attraction) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO attraction (NomAttraction, PrixEnfant, PrixAdulte, PrixSenior) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, attraction.getAttractionNom());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de l'attraction impossible");
        }
    }

    @Override
    public void modifierAttraction(Attraction attraction) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement("update attraction SET Type = ?, Nom = ?, PrixComplet = ?, PrixHabitué = ?, PrixJeune = ?, PrixSenior = ?, Description = ? where IdAttraction = ?");
            preparedStatement.setString(1, attraction.getAttractionType());
            preparedStatement.setString(2, attraction.getAttractionNom());
            preparedStatement.setString(3, attraction.getAttractionPrixComplet());
            preparedStatement.setString(4, attraction.getAttractionPrixHab());
            preparedStatement.setString(5, attraction.getAttractionPrixJeune());
            preparedStatement.setString(6, attraction.getAttractionPrixSenior());
            preparedStatement.setString(7, attraction.getAttractionDescription());
            preparedStatement.setInt(8,attraction.getAttractionId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification de l'attraction impossible");
        }
    }

    @Override
    public void supprimerAttraction(int attractionId) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement("delete from attraction where IdAttraction = ?");
            preparedStatement.setInt(1, attractionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression de l'attraction impossible");
        }
    }
}
