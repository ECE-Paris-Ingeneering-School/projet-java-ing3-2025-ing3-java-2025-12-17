package Controleur;

import DAO.*;
import Modele.Client;

import java.util.ArrayList;

public class ConnexionClient{
    private final ClientDAO clientDAO;
    public ConnexionClient(DaoFactory dao){
        this.clientDAO = new ClientDAOImpl(dao);
    }
    public Client verification(String nom, String mdp) {
        ArrayList<Client> mesClients = clientDAO.getAll();
        for (Client client : mesClients) {
            if (client.getclientNom().equals(nom) && client.getclientMotDePasse().equals(mdp)) {
                return client;
            }
        }
        return null;
    }
}
