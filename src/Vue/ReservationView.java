package Vue;

import Controleur.CalculPrixBillet;
import Controleur.MainFrame;
import DAO.DaoFactory;
import Modele.Attraction;
import Modele.Client;
import Controleur.NouveauRDV;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReservationView extends JPanel {
    private JTabbedPane tabbedPane;
    private JButton[] buttons;
    private DefaultTableModel calendarModel;
    private JLabel monthLabel;
    private JLabel greenLabel;
    private Color greenColor=new Color(12, 38, 21);
    private JLabel yellowLabel;
    private Color yellowColor=new Color(200, 200, 50);
    private JLabel redLabel;
    private Color redColor=new Color(200, 50, 50);
    private LocalDate currentDate;
    private static Color[][] dayColors; // Tableau pour stocker la couleur de chaque jour
    private static Map<Point, Color> hoveredCells = new HashMap<>(); // Map pour garder une trace des cellules survolées
    private int total=0;


    public ReservationView(MainFrame mainFrame, DaoFactory dao, Attraction attractionChoisie, Color couleurChoisie, LocalDate dateChoisie) {
        Client client = mainFrame.getClientConnecte();
        setLayout(new BorderLayout());

        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(greenColor);
        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBackground(new Color(25, 77, 42));
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ImageIcon imageIcon = new ImageIcon(attractionChoisie.getAttractionImage());
        Image image = imageIcon.getImage().getScaledInstance(1536/4, 1024/4, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBackground(greenColor);
        imageLabel.setSize(new Dimension(getWidth(), 100));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel imageAndTextPanel = new JPanel(new BorderLayout());
        imageAndTextPanel.setBackground(greenColor);

        JLabel textLabel = new JLabel("<html><div style='color:white;font-size:32px;'>"
                + "<b>" + attractionChoisie.getAttractionNom() + "</b><br></div><div style='color:white;font-size:25px;'>"
                + "Type : " + attractionChoisie.getAttractionType() + "<br>"
                + "Description : " + attractionChoisie.getAttractionDescription());
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        imageAndTextPanel.add(imageLabel, BorderLayout.WEST);
        imageAndTextPanel.add(textLabel, BorderLayout.CENTER);

        JPanel secondLabel = new JPanel(new BorderLayout());
        secondLabel.setBackground(greenColor);

        JLabel tarifLabel = new JLabel("Tarif Plein "+attractionChoisie.getAttractionPrixComplet()+"€    Tarif Réduit "+attractionChoisie.getAttractionPrixHab()+"€    Tarif Jeune "+attractionChoisie.getAttractionPrixJeune()+"€    Tarif Senior "+attractionChoisie.getAttractionPrixSenior()+"€");
        tarifLabel.setForeground(Color.WHITE);
        tarifLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        tarifLabel.setSize(new Dimension(getWidth(), 30));
        JLabel titreLabel = new JLabel("<html><div style='color:white;font-size:40px;'>Reservez vos billets</html>");
        titreLabel.setFont(new Font("Arial", Font.PLAIN, 70));
        titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        secondLabel.add(tarifLabel, BorderLayout.NORTH);
        secondLabel.add(titreLabel, BorderLayout.SOUTH);

        topPanel.add(imageAndTextPanel);
        topPanel.add(secondLabel);

        add(topPanel, BorderLayout.NORTH);
        if (client==null) {
            pasConnecter(mainFrame,dao,attractionChoisie,couleurChoisie,dateChoisie);
        }
        else estConnecter(mainFrame,client, dao,attractionChoisie,couleurChoisie,dateChoisie);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                topPanel.setPreferredSize(new Dimension(width, 400));
                imageAndTextPanel.setPreferredSize(new Dimension(width, 500));

                revalidate();
                repaint();
            }
        });
    }

    public void pasConnecter(MainFrame mainFrame, DaoFactory dao, Attraction attractionChoisie, Color couleurChoisie, LocalDate dateChoisie){

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(greenColor);
        bottomPanel.setSize(getWidth(), 500);

        JPanel reservationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                int with=200;
                int height=50;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(25, 77, 42));
                g2.fillRoundRect((getWidth()-with)/2, getHeight()-200,with, height, 40, 40);
                g2.dispose();
            }
        };

        reservationPanel.setLayout(null); // Layout absolu
        reservationPanel.setOpaque(false);

        JButton bouton = new JButton("Se connecter");
        bouton.setForeground(Color.WHITE);
        bouton.setFont(new Font("Arial", Font.PLAIN, 20));
        bouton.setContentAreaFilled(false);
        bouton.setBorderPainted(false);
        int boutonX = (reservationPanel.getWidth() - 200) / 2;
        int boutonY = reservationPanel.getHeight() - 200;
        reservationPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                bouton.setBounds((reservationPanel.getWidth() - 200) / 2, reservationPanel.getHeight() - 200, 200, 50);
            }
        });

        bouton.addActionListener(e -> {
            CompteView cv=new CompteView(mainFrame,dao);
            mainFrame.setPanel(cv, "attraction_" );
        });

        reservationPanel.setBackground(greenColor);
        reservationPanel.setLayout(new BoxLayout(reservationPanel, BoxLayout.Y_AXIS));
        reservationPanel.setOpaque(false);

        reservationPanel.add(bouton);
        bottomPanel.add(reservationPanel);
        add(bottomPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void estConnecter(MainFrame mainFrame,Client client, DaoFactory dao, Attraction attractionChoisie, Color couleurChoisie, LocalDate dateChoisie){
        JPanel reservationPanel = new JPanel();
        reservationPanel.setLayout(new BoxLayout(reservationPanel, BoxLayout.Y_AXIS));
        reservationPanel.setBackground(greenColor);
        reservationPanel.setPreferredSize(new Dimension(getWidth(), 500));

        JLabel questionLabel = new JLabel("Vous avez choisi le " + dateChoisie + ", combien de billets voulez-vous ?");
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setBackground(greenColor);
        spinnerPanel.add(spinner);

        CalculPrixBillet calculPrixBillet = new CalculPrixBillet();

        JLabel calculLabel =new JLabel("Vous avez le tarif "+ client.gettypeClient()+", prix totale : 0€");
        calculLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculLabel.setForeground(Color.WHITE);
        calculLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        spinner.addChangeListener(e -> {
             int total = ((int) spinner.getValue() * calculPrixBillet.calculPrixDuBillet(client, attractionChoisie));
            calculLabel.setText("Vous avez le tarif " + client.gettypeClient() + ", prix total : " + total + "€");
        });



        JButton bouton = new JButton("Comfirmer ma resérvation");
        bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton.setForeground(Color.WHITE);
        bouton.setFont(new Font("Arial", Font.PLAIN, 20));
        bouton.setContentAreaFilled(false);
        bouton.setBorderPainted(false);
        bouton.setLocation(getWidth()/2,getHeight()-200);
        bouton.setPreferredSize(new Dimension(300, 100));

        bouton.addActionListener(e -> {
            int total = ((int) spinner.getValue() * calculPrixBillet.calculPrixDuBillet(client, attractionChoisie));
            new NouveauRDV(dao,client,dateChoisie,attractionChoisie,(int) spinner.getValue(),calculPrixBillet.calculPrixDuBillet(client, attractionChoisie));
            ProfileView profileView = new ProfileView(mainFrame, dao);
            mainFrame.setPanel(profileView, "Profil");
        });

        JPanel boutonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        boutonPanel.setOpaque(false);
        boutonPanel.add(bouton);
        reservationPanel.add(Box.createVerticalStrut(20));


        reservationPanel.add(Box.createVerticalStrut(20));
        reservationPanel.add(questionLabel);
        reservationPanel.add(Box.createVerticalStrut(10));
        reservationPanel.add(spinnerPanel);
        reservationPanel.add(Box.createVerticalStrut(10));
        reservationPanel.add(calculLabel);
        reservationPanel.add(Box.createVerticalStrut(10));
        reservationPanel.add(boutonPanel);

        add(reservationPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}