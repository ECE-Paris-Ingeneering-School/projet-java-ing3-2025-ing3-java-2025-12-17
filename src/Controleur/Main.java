package Controleur;

import DAO.DaoFactory;
import Modele.Client;
import Vue.HeaderView;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DaoFactory dao  = DaoFactory.getInstance("projet_java", "root", "");
            Client client=null;
            new MainFrame(dao,client).setVisible(true);
        });
    }
}