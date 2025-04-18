package Vue;

import Controleur.CalculReglement;
import Controleur.ConnexionClient;
import Controleur.MainFrame;
import DAO.AttractionDAO;
import DAO.AttractionDAOImpl;
import DAO.DaoFactory;
import DAO.ReservationDAOImpl;
import DAO.ReservationDAO;
import Modele.Attraction;
import Modele.Client;
import Modele.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProfileView extends JPanel {

    class ProfilePanel extends JPanel {
        private ArrayList<Reservation> toutLesReservations;
        private ArrayList<Reservation> reservationsClient;
        private ArrayList<Attraction> toutLesAttractions;
        private int hoveredIndex = -1;
        private Color fondCase=new Color(25, 77, 42);
        private DaoFactory daoFactory;
        private MainFrame mF;
        private Client monsieur;
        private double prixTotal;
        private ArrayList<Float> opacities = new ArrayList<>();
        private Timer fadeInTimer;



        public void drawWrappedText(Graphics g, String text, int x, int y, int maxWidth) {
            Font font = new Font("Arial", Font.PLAIN, 10);
            FontMetrics fm = g.getFontMetrics(font);
            String[] words = text.split(" ");
            StringBuilder line = new StringBuilder();

            int lineHeight = fm.getHeight(); // Hauteur de la ligne
            int currentWidth = 0;

            // Définir un espacement dynamique entre les lignes en fonction de la taille de la fenêtre
            int lineSpacing =  10;  // Par exemple, 2% de la hauteur de la fenêtre

            // Parcourir chaque mot et déterminer si on doit le mettre sur une nouvelle ligne
            for (String word : words) {
                int wordWidth = fm.stringWidth(word + " "); // Largeur du mot avec espace

                if (currentWidth + wordWidth > maxWidth) {
                    // Si le mot dépasse la largeur max, on dessine la ligne précédente et on commence une nouvelle ligne
                    g.drawString(line.toString(), x, y);
                    y += lineHeight + lineSpacing;  // Déplace la position verticale pour la prochaine ligne avec espacement
                    line = new StringBuilder(word + " "); // Commence une nouvelle ligne avec le mot actuel
                    currentWidth = wordWidth;  // Réinitialise la largeur actuelle avec le mot actuel
                } else {
                    line.append(word).append(" "); // Ajouter le mot à la ligne
                    currentWidth += wordWidth; // Met à jour la largeur de la ligne
                }
            }

            // Afficher la dernière ligne
            if (line.length() > 0) {
                g.drawString(line.toString(), x, y);
            }
        }

        public ProfilePanel(MainFrame mainFrame, DaoFactory dao, ArrayList<Reservation> toutLesReservations, ArrayList<Attraction> toutLesAttractions,MainFrame mF) {
            Client client = mainFrame.getClientConnecte();
            this.toutLesReservations = toutLesReservations;
            this.toutLesAttractions = toutLesAttractions;
            this.daoFactory = dao;
            this.mF =mF;
            this.monsieur = client;
            for (int i = 0; i < toutLesReservations.size(); i++) {
                opacities.add(0f);
            }

            fadeInTimer = new Timer(40, e -> {
                boolean allOpaque = true;
                for (int i = 0; i < opacities.size(); i++) {
                    if (opacities.get(i) < 1f) {
                        opacities.set(i, Math.min(1f, opacities.get(i) + 0.05f));
                        allOpaque = false;
                    }
                }
                repaint();
                if (allOpaque) {
                    fadeInTimer.stop();
                }
            });
            fadeInTimer.start();
            CalculReglement calculReglement =new CalculReglement(toutLesReservations,client);
            this.prixTotal = calculReglement.calculPrixTotal(client);

            int rectHeight = 220;
            int spacing = 50;
            this.reservationsClient = new ArrayList<>();
            for (Reservation r : toutLesReservations) {
                if (r.getClientId() == monsieur.getClientId()) {
                    reservationsClient.add(r);
                }
            }
            int totalHeight = reservationsClient.size() * (rectHeight + spacing) + 100;
            setPreferredSize(new Dimension(1000, totalHeight));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(greenColor);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.setColor(Color.RED);
            g.drawString("Prix total a regler : \n" + this.prixTotal+" €", getWidth()/2-20,25);

            boolean aucuneReservationTrouvee = true;

            if (!reservationsClient.isEmpty()) {
                for (int i = 0; i < reservationsClient.size(); i++) {
                    Reservation reservation = reservationsClient.get(i);
                    int rectWidth = getWidth() - 100;  // Largeur du rectangle proportionnelle à la taille de la fenêtre
                    int rectHeight = 220; // ou 180, ou toute autre valeur fixe
                    int rectX = 50;
                    int rectY = 50 + i * (rectHeight + 50);

                    if (reservation.getClientId() == monsieur.getClientId()) {
                        for (int j = 0; j < toutLesAttractions.size(); j++) {
                            Attraction attraction = toutLesAttractions.get(j);
                            if (reservation.getIdAttraction() == attraction.getAttractionId()) {
                                aucuneReservationTrouvee = false;
                                if (i == hoveredIndex) {
                                } else {
                                    g.setColor(greenColor); // Couleur normale
                                }
                                JButton annulerBtn = new JButton("Annuler");
                                annulerBtn.setFocusPainted(false);
                                setLayout(null);
                                annulerBtn.setForeground(Color.WHITE);
                                annulerBtn.setBackground(new Color(25, 77, 42));
                                g.setColor(new Color(0, 0, 0, 250));
                                float alpha = (i < opacities.size()) ? opacities.get(i) : 1f;
                                Graphics2D g2d = (Graphics2D) g.create();
                                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                                g2d.setColor(new Color(0, 0, 0, 250));
                                g2d.fillRoundRect(rectX + 10, rectY + 10, rectWidth + 5, rectHeight + 5, 30, 30);
                                g2d.setColor(fondCase);
                                g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 30, 30);
                                g2d.dispose();

                                int lineSpacing = 25;
                                int nameX = 110;
                                int descriptionX = (int) (getWidth() * 0.235);
                                int textX = (int) (getWidth() * 0.66);
                                int infoX = (int) (0.95 * textX);
                                int infoY = 75 + i * (rectHeight + 50) + lineSpacing;
                                int fontSizeDetails = (int) (getWidth() * 0.03);
                                g.setColor(Color.WHITE);
                                g.setFont(new Font("Arial", Font.BOLD, fontSizeDetails));
                                g.drawString(attraction.getAttractionNom(), nameX, 90 + i * (rectHeight + 50));
                                g.drawImage(attraction.getAttractionImage(), nameX + 25, 100 + i * (rectHeight + 50), null);

                                g.setFont(new Font("Arial", Font.BOLD, (int) (fontSizeDetails * 0.50)));
                                drawWrappedText(g, attraction.getAttractionDescription(), descriptionX, 130 + i * (rectHeight + 50), 200);
                                g.drawString("Type d'attraction : \n" + attraction.getAttractionType(), infoX, infoY);
                                g.drawString("Date choisie : " + reservation.getReservationDate(), infoX, infoY + 50);
                                g.drawString("Vous avez reservé " + reservation.getReservationNbPersonnes() + " billet(s)", infoX, infoY + 100);
                                if (annulerBtn.getParent() != this) {
                                    annulerBtn.setBounds(infoX, infoY + 130, 120, 30);
                                    this.add(annulerBtn);
                                    annulerBtn.addActionListener(e -> {
                                        ReservationDAOImpl anul = new ReservationDAOImpl(daoFactory);
                                        anul.supprimerReservation(reservation.getReservationId());
                                        removeAll();
                                        mF.setPanel(new ProfileView(mF, daoFactory), "Profil");
                                    });
                                }
                            }
                        }
                        if (aucuneReservationTrouvee) {
                            g.setFont(new Font("Arial", Font.BOLD, 20));
                            g.setColor(Color.WHITE);
                            String message = "Vous n'avez aucune réservation";
                            int stringWidth = g.getFontMetrics().stringWidth(message);
                            g.drawString(message, (getWidth() - stringWidth) / 2, getHeight() / 2);
                        }
                    }
                }
            }
        }
    }
    private Color greenColor=new Color(12, 38, 21);

    public ProfileView(MainFrame mainFrame, DaoFactory dao) {
        Client client = mainFrame.getClientConnecte();

        setLayout(new BorderLayout());
        setBackground(greenColor);

        JLabel loadingLabel = new JLabel("Chargement du profil...");
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel loadingPanel = new JPanel();
        loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.Y_AXIS));
        loadingPanel.setBackground(greenColor);
        loadingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadingPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadingPanel.add(Box.createVerticalGlue());
        loadingPanel.add(loadingLabel);
        loadingPanel.add(Box.createVerticalGlue());
        add(loadingPanel, BorderLayout.CENTER);

        new SwingWorker<Void, Void>() {
            ArrayList<Reservation> reservations;
            ArrayList<Attraction> attractions;

            @Override
            protected Void doInBackground() throws Exception {
                ReservationDAO reservationDAO = new ReservationDAOImpl(dao);
                AttractionDAO attractionDAO = new AttractionDAOImpl(dao);
                reservations = reservationDAO.getAllReservations();
                attractions = new ArrayList<>(attractionDAO.getAllAttractions());
                return null;
            }

            @Override
            protected void done() {
                try {
                    remove(loadingPanel);
                    JPanel comptePanel = new JPanel(new BorderLayout());
                    comptePanel.setBackground(greenColor);
                    comptePanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
                    comptePanel.setMaximumSize(new Dimension(800, 60));


                    JLabel lblCompte = new JLabel("Vous etes connecter avec le nom '" + client.getclientNom()+"'");
                    lblCompte.setFont(new Font("Arial", Font.BOLD, 14));
                    lblCompte.setBackground(greenColor);
                    lblCompte.setForeground(Color.WHITE);
                    comptePanel.add(lblCompte, BorderLayout.CENTER);

                    JButton validerButton = new JButton("Se déconnecter");
                    validerButton.setForeground(Color.WHITE);
                    validerButton.setBackground(new Color(25, 77, 42));
                    validerButton.addActionListener(e -> {
                        mainFrame.setClientConnecte(null);
                        mainFrame.initHeader(dao);
                        CompteView compteView = new CompteView(mainFrame, dao);
                        mainFrame.setPanel(compteView, "CompteView");
                    });
                    comptePanel.add(validerButton, BorderLayout.EAST);

                    JLabel messageLabel = new JLabel(" ");
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setBackground(Color.WHITE);
                    comptePanel.add(messageLabel, BorderLayout.WEST);
                    add(comptePanel, BorderLayout.CENTER);

                    ProfilePanel profilePanel = new ProfilePanel(mainFrame, dao, reservations, attractions,mainFrame);
                    JScrollPane scrollPane = new JScrollPane(profilePanel);
                    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                    scrollPane.setBackground(greenColor);
                    scrollPane.getViewport().setBackground(greenColor);

                    JPanel mainContainer = new JPanel();
                    mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
                    mainContainer.setBackground(greenColor);

                    mainContainer.add(comptePanel);
                    mainContainer.add(scrollPane);

                    removeAll();
                    setLayout(new BorderLayout());
                    add(mainContainer, BorderLayout.CENTER);

                    revalidate();
                    repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.execute();
    }
}
