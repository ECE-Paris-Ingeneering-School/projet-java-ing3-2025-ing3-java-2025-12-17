package Vue;

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
        private ArrayList<Attraction> toutLesAttractions;
        private int hoveredIndex = -1;


        public void drawWrappedText(Graphics g, String text, int x, int y, int maxWidth) {
            Font font = new Font("Arial", Font.PLAIN, 10);
            FontMetrics fm = g.getFontMetrics(font);
            String[] words = text.split(" ");
            StringBuilder line = new StringBuilder();

            int lineHeight = fm.getHeight(); // Hauteur de la ligne
            int currentWidth = 0;

            // Définir un espacement dynamique entre les lignes en fonction de la taille de la fenêtre
            int lineSpacing = (int) (getHeight() * 0.02);  // Par exemple, 2% de la hauteur de la fenêtre

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

        public ProfilePanel(MainFrame mainFrame, DaoFactory dao, ArrayList<Reservation> toutLesReservations, ArrayList<Attraction> toutLesAttractions) {
            Client client = mainFrame.getClientConnecte();
            this.toutLesReservations = toutLesReservations;
            this.toutLesAttractions = toutLesAttractions;

        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.setColor(greenColor);

            if (!toutLesReservations.isEmpty()) {
                for (int i = 0; i < toutLesReservations.size(); i++) {
                    Reservation reservation = toutLesReservations.get(i);


                    int rectWidth = getWidth() - 100;  // Largeur du rectangle proportionnelle à la taille de la fenêtre
                    int rectHeight = 220; // ou 180, ou toute autre valeur fixe
                    int rectX = 50;
                    int rectY = 50 + i * (rectHeight + 50);

                    for (int j = 0; j < toutLesAttractions.size(); j++) {
                        Attraction attraction = toutLesAttractions.get(j);
                        if (reservation.getIdAttraction() == attraction.getAttractionId()) {
                            if (i == hoveredIndex) {
                            } else {
                                g.setColor(greenColor); // Couleur normale
                            }

                            g.setColor(new Color(0, 0, 0, 250));
                            g.fillRoundRect(rectX + 10, rectY + 10, rectWidth + 5, rectHeight + 5, 30, 30);
                            g.setColor(greenColor);

                            g.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 30, 30);
                            int lineSpacing = 25;
                            int nameX = 100;
                            int descriptionX = (int) (getWidth() * 0.25);
                            int textX = (int) (getWidth() * 0.66);
                            int fontSizeDetails = (int) (getWidth() * 0.03);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("Arial", Font.BOLD, fontSizeDetails));
                            g.drawString(attraction.getAttractionNom(), nameX, 90 + i * (rectHeight + 50));
                            g.drawImage(attraction.getAttractionImage(), nameX - 10, 110 + i * (rectHeight + 50), null);

                            g.setFont(new Font("Arial", Font.BOLD, (int) (fontSizeDetails * 0.60)));
                            drawWrappedText(g, attraction.getAttractionDescription(), descriptionX, 100 + i * (rectHeight + 50), 200);
                            g.drawString("Type d'attraction : \n" + attraction.getAttractionType(), (int) (0.95 * textX), 75 + i * (rectHeight + 50) + lineSpacing);
                            g.drawString("Vous avez reservé "+reservation.getReservationNbPersonnes() +" billet(s)",(int) (0.95 * textX), 175 + i * (rectHeight + 50) + lineSpacing);
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
        add(loadingLabel, BorderLayout.CENTER);

        JPanel comptePanel = new JPanel();
        comptePanel.setLayout(new BoxLayout(comptePanel, BoxLayout.Y_AXIS));
        comptePanel.setBackground(greenColor);
        comptePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));


        JLabel lblCompte = new JLabel("Vous etes connecter sous le nom " + client.getclientNom());
        lblCompte.setFont(new Font("Arial", Font.BOLD, 14));
        lblCompte.setBackground(greenColor);
        lblCompte.setForeground(Color.WHITE);
        lblCompte.setAlignmentX(Component.CENTER_ALIGNMENT);
        comptePanel.add(lblCompte);

        comptePanel.add(Box.createRigidArea(new Dimension(0, 20)));


        JButton validerButton = new JButton("Se déconnecter");
        validerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        validerButton.setForeground(Color.WHITE);
        validerButton.setBackground(new Color(25, 77, 42));
        validerButton.addActionListener(e -> {
            mainFrame.setClientConnecte(null);
            mainFrame.initHeader(dao);
            CompteView compteView = new CompteView(mainFrame, dao);
            mainFrame.setPanel(compteView, "CompteView");
        });
        comptePanel.add(validerButton);

        JLabel messageLabel = new JLabel("");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBackground(Color.WHITE);
        comptePanel.add(messageLabel);
        ConnexionClient connexion = new ConnexionClient(dao);
        add(comptePanel, BorderLayout.CENTER);

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
                    remove(loadingLabel);

                    ProfilePanel profilePanel = new ProfilePanel(mainFrame, dao, reservations, attractions);
                    JScrollPane scrollPane = new JScrollPane(profilePanel);
                    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                    scrollPane.setBackground(greenColor);
                    scrollPane.getViewport().setBackground(greenColor);

                    JPanel mainContainer = new JPanel();
                    mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
                    mainContainer.setBackground(greenColor);

                    comptePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, comptePanel.getPreferredSize().height));
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
