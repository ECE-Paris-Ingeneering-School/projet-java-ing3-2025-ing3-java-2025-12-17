package Vue;

import Controleur.ConnexionClient;
import Controleur.MainFrame;
import DAO.DaoFactory;
import Modele.Client;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ProfileView extends JPanel {
    private Color greenColor=new Color(12, 38, 21);

    public ProfileView(MainFrame mainFrame, DaoFactory dao) {
        Client client = mainFrame.getClientConnecte();
        setLayout(new BorderLayout());
        setBackground(greenColor);

        JPanel comptePanel = new JPanel();
        comptePanel.setLayout(new BoxLayout(comptePanel, BoxLayout.Y_AXIS));
        comptePanel.setBackground(greenColor);
        comptePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // marge intérieure

        // Titre
        JLabel lblCompte = new JLabel("Vous etes connecter sous le nom " +client.getclientNom());
        lblCompte.setFont(new Font("Arial", Font.BOLD, 14));
        lblCompte.setBackground(greenColor);
        lblCompte.setForeground(Color.WHITE);
        lblCompte.setAlignmentX(Component.CENTER_ALIGNMENT);
        comptePanel.add(lblCompte);

        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));



        JButton validerButton = new JButton("Se connecter");
        validerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        validerButton.setForeground(Color.WHITE);
        validerButton.setBackground(new Color(25, 77, 42));
        comptePanel.add(validerButton);

        JLabel messageLabel = new JLabel("");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBackground(Color.WHITE);
        comptePanel.add(messageLabel);
        ConnexionClient connexion = new ConnexionClient(dao);
        add(comptePanel, BorderLayout.CENTER);
    }
}
