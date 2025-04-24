package Vue;

import Controleur.MainFrame;
import Controleur.ModifierAttraction;
import DAO.DaoFactory;
import Modele.Attraction;
import Modele.Client;

import javax.swing.*;
import java.awt.*;

public class ModifierAttractionView extends JPanel {
    private Color redColor =new Color(38, 12, 21);


    public ModifierAttractionView(MainFrame mainFrame, DaoFactory dao, Attraction attractionChoisie) {
        Client client = mainFrame.getClientConnecte();
        setLayout(new BorderLayout());

        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(redColor);
        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBackground(new Color(251*2/3, 75*2/3, 42));
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ImageIcon imageIcon = new ImageIcon(attractionChoisie.getAttractionImage());
        Image image = imageIcon.getImage().getScaledInstance(1536/8, 1024/8, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBackground(redColor);
        imageLabel.setSize(new Dimension(getWidth(), 100));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel imageAndTextPanel = new JPanel(new BorderLayout());
        imageAndTextPanel.setBackground(redColor);

        JLabel textLabel = new JLabel("<html><div style='color:white;font-size:32px;'>"
                + "<b>" + attractionChoisie.getAttractionNom() + "</b><br></div><div style='color:white;font-size:25px;'>"
                + "Type : " + attractionChoisie.getAttractionType() + "<br>"
                + "Description : " + attractionChoisie.getAttractionDescription());
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        imageAndTextPanel.add(imageLabel, BorderLayout.WEST);
        imageAndTextPanel.add(textLabel, BorderLayout.CENTER);

        JPanel secondLabel = new JPanel(new BorderLayout());
        secondLabel.setBackground(redColor);

        JLabel tarifLabel = new JLabel("Tarif Plein "+attractionChoisie.getAttractionPrixComplet()+"€    Tarif Réduit "+attractionChoisie.getAttractionPrixHab()+"€    Tarif Jeune "+attractionChoisie.getAttractionPrixJeune()+"€    Tarif Senior "+attractionChoisie.getAttractionPrixSenior()+"€");
        tarifLabel.setForeground(Color.WHITE);
        tarifLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        tarifLabel.setSize(new Dimension(getWidth(), 30));
        secondLabel.add(tarifLabel, BorderLayout.NORTH);

        topPanel.add(imageAndTextPanel);
        topPanel.add(secondLabel);

        add(topPanel, BorderLayout.NORTH);

       JPanel modiferPanel = new JPanel();
        modiferPanel.setLayout(new BoxLayout(modiferPanel, BoxLayout.Y_AXIS));
        modiferPanel.setBackground(redColor);
        modiferPanel.setPreferredSize(new Dimension(getWidth(), 500));

        JLabel questionLabel = new JLabel("Veulliez remplir les nouvelles information :");
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nomLabel = new JLabel("Nom de l'attraction :");
        nomLabel.setForeground(Color.WHITE);
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField nomTextField = new JTextField(20);
        nomTextField.setMaximumSize(new Dimension(200, 50));
        nomTextField.setForeground(redColor);
        nomTextField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel typeLabel = new JLabel("Type d'attraction");
        typeLabel.setForeground(Color.WHITE);
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField TypeLabelField = new JTextField(20);
        TypeLabelField.setMaximumSize(new Dimension(200, 50));
        TypeLabelField.setForeground(redColor);
        TypeLabelField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pcLabel = new JLabel("Prix complet");
        pcLabel.setForeground(Color.WHITE);
        pcLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField pcLabelField = new JTextField(20);
        pcLabelField.setMaximumSize(new Dimension(200, 50));
        pcLabelField.setForeground(redColor);
        pcLabelField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel prLabel = new JLabel("Prix réduit");
        prLabel.setForeground(Color.WHITE);
        prLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField prLabelField = new JTextField(20);
        prLabelField.setMaximumSize(new Dimension(200, 50));
        prLabelField.setForeground(redColor);
        prLabelField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pjLabel = new JLabel("Prix jeune");
        pjLabel.setForeground(Color.WHITE);
        pjLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField pjLabelField = new JTextField(20);
        pjLabelField.setMaximumSize(new Dimension(200, 50));
        pjLabelField.setForeground(redColor);
        pjLabelField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel psLabel = new JLabel("Prix senior");
        psLabel.setForeground(Color.WHITE);
        psLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField psLabelField = new JTextField(20);
        psLabelField.setMaximumSize(new Dimension(200, 50));
        psLabelField.setForeground(redColor);
        psLabelField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField descriptionLabelField = new JTextField(20);
        descriptionLabelField.setMaximumSize(new Dimension(200, 50));
        descriptionLabelField.setForeground(redColor);
        descriptionLabelField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton validerButton = new JButton("Enregistrer les modification :");
        validerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        validerButton.setForeground(Color.WHITE);
        validerButton.setBackground(new Color(25, 77, 42));


        modiferPanel.add(Box.createVerticalStrut(20));
        modiferPanel.add(questionLabel);

        modiferPanel.add(nomLabel);
        modiferPanel.add(nomTextField);
        modiferPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        modiferPanel.add(typeLabel);
        modiferPanel.add(TypeLabelField);
        modiferPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        modiferPanel.add(pcLabel);
        modiferPanel.add(pcLabelField);
        modiferPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        modiferPanel.add(prLabel);
        modiferPanel.add(prLabelField);
        modiferPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        modiferPanel.add(pjLabel);
        modiferPanel.add(pjLabelField);
        modiferPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        modiferPanel.add(psLabel);
        modiferPanel.add(psLabelField);
        modiferPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        modiferPanel.add(descriptionLabel);
        modiferPanel.add(descriptionLabelField);

        modiferPanel.add(Box.createVerticalStrut(10));
        modiferPanel.add(validerButton);

        validerButton.addActionListener(e -> {
            String nom = nomTextField.getText();
            String ntype = typeLabel.getText();
            String npc = pcLabelField.getText();
            String npr = prLabelField.getText();
            String npj = pjLabelField.getText();
            String nps = psLabelField.getText();
            String ndescription = descriptionLabelField.getText();
            new ModifierAttraction(dao,attractionChoisie,nom,ntype,npc,npr,npj,nps,ndescription);
            mainFrame.setPanel(new ProfileAdmin(mainFrame,dao), "ProfilAdmin");
        });

        add(modiferPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}