package Controleur;

import DAO.DaoFactory;
import Modele.Client;
import Vue.AccueilVue;
import Vue.ChoixAttraction;
import Vue.HeaderView;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel cardPanel;
    private Client clientTampon;
    private HeaderView headerView;
    private Client clientConnecte;


    public MainFrame(DaoFactory dao, Client client) {
        this.clientTampon = client;
        this.clientConnecte = client;
        updateHeader(dao);
        setTitle("Ton Appli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // plein écran
        setLayout(new BorderLayout());

        initHeader(dao);

        // Panel central avec CardLayout
        cardPanel = new JPanel(new CardLayout());
        add(cardPanel, BorderLayout.CENTER);

        // Ajoute ici tes vues
        cardPanel.add(new AccueilVue(this, dao), "accueil");
        cardPanel.add(new ChoixAttraction(this, dao), "choix");

        // Affiche la première
        showPanel("accueil");

        setVisible(true);
    }



    public void showPanel(String name) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, name);
    }

    public void setPanel(JPanel panel, String name) {
        cardPanel.add(panel, name);
        cardPanel.revalidate();
        cardPanel.repaint();
        showPanel(name);
    }
    public void setClientConnecte(Client client) {
        this.clientConnecte = client;
    }

    public void initHeader(DaoFactory dao) {
        if (headerView != null) {
            remove(headerView);
        }
        headerView = new HeaderView(this, dao, clientConnecte);
        add(headerView, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    public void updateHeader(DaoFactory dao) {
        if (headerView != null) {
            remove(headerView);
        }
        headerView = new HeaderView(this, dao, clientConnecte);
        add(headerView, BorderLayout.NORTH);
        revalidate();
        repaint();
    }


    public Client getClientConnecte() {
        return clientConnecte;
    }

}
