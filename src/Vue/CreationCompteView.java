package Vue;

import Controleur.ConnexionClient;
import Controleur.MainFrame;
import DAO.*;
import Modele.Client;
import Modele.Reservation;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class CreationCompteView extends JPanel {
    private Color greenColor=new Color(12, 38, 21);
    private HeaderView headerView;

    public CreationCompteView(MainFrame mainFrame, DaoFactory dao){
        Client client = mainFrame.getClientConnecte();
        setLayout(new BorderLayout());
        setBackground(greenColor);

        JPanel comptePanel = new JPanel();
        comptePanel.setLayout(new BoxLayout(comptePanel, BoxLayout.Y_AXIS));
        comptePanel.setBackground(greenColor);
        comptePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // marge intérieure

        // Titre
        JLabel lblCompte = new JLabel("Insrivez-vous dès maintenant ?");
        lblCompte.setFont(new Font("Arial", Font.BOLD, 22));
        lblCompte.setForeground(Color.WHITE);
        lblCompte.setAlignmentX(Component.CENTER_ALIGNMENT);
        comptePanel.add(lblCompte);

        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel nomLabel = new JLabel("Pseudo :");
        nomLabel.setForeground(Color.WHITE);
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField nomTextField = new JTextField(20);
        nomTextField.setMaximumSize(new Dimension(200, 50));
        nomTextField.setForeground(greenColor);
        nomTextField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel ageLabel = new JLabel("Age :");
        ageLabel.setForeground(Color.WHITE);
        ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField ageTaxt = new JTextField(20);
        ageTaxt.setMaximumSize(new Dimension(200, 50));
        ageTaxt.setForeground(greenColor);
        ageTaxt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mdpLabel = new JLabel("Mot de passe :");
        mdpLabel.setForeground(Color.WHITE);
        mdpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mdpLabel.setMaximumSize(new Dimension(200, 50));
        JPasswordField mdpPassField = new JPasswordField(20);
        mdpPassField.setMaximumSize(new Dimension(200, 50));
        mdpPassField.setForeground(greenColor);
        mdpPassField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mdpVerificationLabel = new JLabel("Comfirmer le mot de passe :");
        mdpVerificationLabel.setForeground(Color.WHITE);
        mdpVerificationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mdpVerificationLabel.setMaximumSize(new Dimension(200, 50));
        JPasswordField mdpPassVerificationField = new JPasswordField(20);
        mdpPassVerificationField.setMaximumSize(new Dimension(200, 50));
        mdpPassVerificationField.setForeground(greenColor);
        mdpPassVerificationField.setAlignmentX(Component.CENTER_ALIGNMENT);

        comptePanel.add(nomLabel);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        comptePanel.add(nomTextField);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        comptePanel.add(ageLabel);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        comptePanel.add(ageTaxt);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        comptePanel.add(mdpLabel);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        comptePanel.add(mdpPassField);

        comptePanel.add(mdpVerificationLabel);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        comptePanel.add(mdpPassVerificationField);
        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));


        JButton validerButton = new JButton("S'inscrire");
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
            String ageText = ageTaxt.getText();
            String mdp = new String(mdpPassField.getPassword());
            String mdpVerification = new String(mdpPassVerificationField.getPassword());
            if (nom.isEmpty() || ageText.isEmpty() || mdp.isEmpty() || mdpVerification.isEmpty()) {
                messageLabel.setText("Veuillez remplir toutes informations");
            }
            else{
                if(mdp.equals(mdpVerification)){
                    int age = Integer.parseInt(ageTaxt.getText());
                    String type = "complet";
                    if (age>70){
                        type = "senior";
                    }
                    if (age <23){
                        type = "jeune";
                    }
                    messageLabel.setText("");
                    Client clientTampon = new Client(nom,mdp,age,type);
                    ClientDAO clientDAO = new ClientDAOImpl(dao);
                    System.out.println("Ajout client : " + nom + ", " + mdp + ", " + age + ", " + type);
                    clientDAO.ajouter(clientTampon);
                    mainFrame.setClientConnecte(clientTampon);
                    mainFrame.updateHeader(dao);
                    mainFrame.setPanel(new ProfileView(mainFrame,dao), "Profil");
                }
                else{
                    messageLabel.setText("Verfifer que vos 2 mots de passe sont bien identique");
                }
            }
        });
        add(comptePanel, BorderLayout.CENTER);
    }
}
