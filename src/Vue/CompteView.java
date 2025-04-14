package Vue;

import Controleur.ConnexionClient;
import Controleur.MainFrame;
import DAO.DaoFactory;
import Modele.Client;

import javax.swing.*;
import java.awt.*;

public class CompteView extends JPanel {
    private Color greenColor=new Color(12, 38, 21);
    private HeaderView headerView;

    public CompteView(MainFrame mainFrame, DaoFactory dao) {
        Client client = mainFrame.getClientConnecte();

        if (client==null){
            connexionView(mainFrame,dao,client);
        }
        else {
            ProfileView profileView = new ProfileView(mainFrame, dao);
            mainFrame.setPanel(profileView, "Profil");
        }
    }
    public void connexionView(MainFrame mainFrame, DaoFactory dao,Client client){
        setLayout(new BorderLayout());
        setBackground(greenColor);

        JPanel comptePanel = new JPanel();
        comptePanel.setLayout(new BoxLayout(comptePanel, BoxLayout.Y_AXIS));
        comptePanel.setBackground(greenColor);
        comptePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // marge intérieure

        // Titre
        JLabel lblCompte = new JLabel("Vous avez déjà un compte ?");
        lblCompte.setFont(new Font("Arial", Font.BOLD, 22));
        lblCompte.setForeground(Color.WHITE);
        lblCompte.setAlignmentX(Component.CENTER_ALIGNMENT);
        comptePanel.add(lblCompte);

        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel nomLabel = new JLabel("Identifiant :");
        nomLabel.setForeground(Color.WHITE);
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField nomTextField = new JTextField(20);
        nomTextField.setMaximumSize(new Dimension(200, 50));
        nomTextField.setForeground(greenColor);
        nomTextField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mdpLabel = new JLabel("Mot de passe :");
        mdpLabel.setForeground(Color.WHITE);
        mdpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPasswordField mdpPassField = new JPasswordField(20);
        mdpPassField.setMaximumSize(new Dimension(200, 50));
        mdpPassField.setForeground(greenColor);
        mdpPassField.setAlignmentX(Component.CENTER_ALIGNMENT);

        comptePanel.add(nomLabel);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        comptePanel.add(nomTextField);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        comptePanel.add(mdpLabel);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        comptePanel.add(mdpPassField);
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
        validerButton.addActionListener(e -> {
            String nom = nomTextField.getText();
            String mdp = new String(mdpPassField.getPassword());
            if (nom.isEmpty() || mdp.isEmpty()) {
                messageLabel.setText("Veuillez remplir les deux informations");
            }
            else{
                Client clientTampon=connexion.verification(nom,mdp);
                if(clientTampon!=null){
                    messageLabel.setText("");
                    mainFrame.setClientConnecte(clientTampon);
                    mainFrame.initHeader(dao);
                    mainFrame.setPanel(new AccueilVue(mainFrame, dao), "accueil");
                }
                else{
                    messageLabel.setText("Mauvaise connexion, veuillez rééessayer");
                }
            }
        });
        add(comptePanel, BorderLayout.CENTER);
    }
}
