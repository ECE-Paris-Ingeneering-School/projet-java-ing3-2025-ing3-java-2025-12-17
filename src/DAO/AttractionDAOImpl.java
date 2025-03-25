package DAO;

// import des packages
import Modele.Attraction;
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
                String attractionNom = resultats.getString(2);
                float[] attractionPrix = {resultats.getFloat(3), resultats.getFloat(4), resultats.getFloat(5)};

                attraction = new Attraction(attractionId, attractionNom, attractionPrix);
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
                String attractionNom = resultats.getString(2);
                float[] attractionPrix = {resultats.getFloat(3), resultats.getFloat(4), resultats.getFloat(5)};

                Attraction attraction = new Attraction(attractionId, attractionNom, attractionPrix);

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
            preparedStatement.setFloat(2, attraction.getAttractionPrix()[0]);
            preparedStatement.setFloat(3, attraction.getAttractionPrix()[1]);
            preparedStatement.setFloat(4, attraction.getAttractionPrix()[2]);
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
            PreparedStatement preparedStatement = connexion.prepareStatement("update attraction set NomAttraction = ?, PrixEnfant = ?, PrixAdulte = ?, PrixSenior = ? where IdAttraction = ?");
            preparedStatement.setString(1, attraction.getAttractionNom());
            preparedStatement.setFloat(2, attraction.getAttractionPrix()[0]);
            preparedStatement.setFloat(3, attraction.getAttractionPrix()[1]);
            preparedStatement.setFloat(4, attraction.getAttractionPrix()[2]);
            preparedStatement.setInt(5, attraction.getAttractionId());
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
