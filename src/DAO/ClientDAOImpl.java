//CODE REPRIS DU TP JAVA ECE DAO
package DAO;

// import des packages
import Modele.Client;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * ClientDao.
 */
public class ClientDAOImpl implements ClientDAO {
    // attribut privé pour l'objet du DaoFactoru
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public ClientDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    /**
     * Récupérer de la base de données tous les objets des clients dans une liste
     * @return : liste retournée des objets des clients récupérés
     */
    public ArrayList<Client> getAll() {
        ArrayList<Client> listeClients = new ArrayList<Client>();

        /*
            Récupérer la liste des clients de la base de données dans listeClients
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from client");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                int clientId = resultats.getInt(1);
                String clientNom = resultats.getString(2);
                String clientMotDePasse = resultats.getString(3);
                int clientAge = resultats.getInt(4);
                String clienTarif = resultats.getString(5);


                // instancier un objet de Client
                Client client = new Client(clientId,clientNom,clientMotDePasse,clientAge,clienTarif);

                // ajouter ce produit à listeProduits
                listeClients.add(client);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de clients impossible");
        }

        return listeClients;
    }

    /**
     Ajouter un nouveau client en paramètre dans la base de données
     @params : client = objet de Client à insérer dans la base de données
     */
    public void ajouter(Client client){
        /*
            A COMPLETER
         */
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client (NomUtilisateur, MotDePasse, Age, Tarif) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, client.getclientNom());
            preparedStatement.setString(2, client.getclientMotDePasse());
            preparedStatement.setInt(3, client.getclientAge());
            preparedStatement.setString(4, client.gettypeClient());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Permet de chercher et récupérer un objet de Client dans la base de données via son id en paramètre
     * @param : id
     * @return : objet de classe Client cherché et retourné
     */
    public Client chercher(int id)  {
        Client client = null;

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // Exécution de la requête SELECT pour récupérer le client de l'id dans la base de données
            ResultSet resultats = statement.executeQuery("select * from clients where clientID="+id);

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                int clientId = resultats.getInt(1);
                String clientNom = resultats.getString(2);
                String clientMotDePasse = resultats.getString(3);
                int clientAge = resultats.getInt(4);
                String Tarif = resultats.getString(5);

                // Si l'id du client est trouvé, l'instancier et sortir de la boucle
                if (id == clientId) {
                    // instancier un objet de Produit avec ces 3 champs en paramètres
                    client = new Client(clientId,clientNom, clientMotDePasse, clientAge, Tarif);
                    break;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Client non trouvé dans la base de données");
        }

        return client;
    }

    /**
     * Permet de modifier les données du nom de l'objet de la classe Client en paramètre
     * dans la base de données à partir de l'id de cet objet en paramètre
     * @param : client = objet en paramètre de la classe Client à mettre à jour à partir de son id
     * @return : objet client en paramètre mis à jour  dans la base de données à retourner
     */
    public Client modifier(Client client) {
        /*
            A COMPLETER
         */
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE clients SET clientNom = ? WHERE clientID = ?");
            preparedStatement.setString(1, client.getclientNom());
            preparedStatement.setInt(2, client.getClientId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return client;
    }

    @Override
    /**
     * Supprimer un objet de la classe Client en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un client, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du client supprimé.
     * @params : client = objet de Client en paramètre à supprimer de la base de données
     */
    public void supprimer (Client client) {
        /*
            A COMPLETER
         */
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clients WHERE clientID = ?");
            preparedStatement.setInt(1, client.getClientId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}


