package Vue;

import Controleur.MainFrame;
import DAO.DaoFactory;
import Modele.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AccueilVue extends JPanel {

    public AccueilVue(MainFrame mainFrame, DaoFactory dao) {
        Client client = mainFrame.getClientConnecte();

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.BLACK);

        JLabel welcomeLabel = new JLabel("Bienvenue au Dino Parc");
        welcomeLabel.setLocation(0, 0);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBackground(Color.BLACK);
        welcomeLabel.setSize(getWidth(),getHeight());

        backgroundPanel.add(welcomeLabel);
        add(backgroundPanel);
    }

}