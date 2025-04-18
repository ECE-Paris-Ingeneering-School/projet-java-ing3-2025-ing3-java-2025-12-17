package Controleur;

import DAO.DaoFactory;
import Modele.Client;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DaoFactory dao  = DaoFactory.getInstance("projet_java", "root", "root");
            Client client=null;
            new MainFrame(dao,client).setVisible(true);
        });
    }
}