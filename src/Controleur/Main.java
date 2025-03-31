package Controleur;

// import des packages
import java.sql.*;
import DAO.*;
import Modele.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Déclaration et instanciation des objets des classes DaoFactory, ProduitDAOImpl, VueProduit,
            // ClientDAOImpl, VueClient, CommanderDAOImpl et VueCommander
            DaoFactory dao = DaoFactory.getInstance("projet_java", "root", "");
            ClientDAOImpl clidao = new ClientDAOImpl(dao);

        }

        catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }
}
