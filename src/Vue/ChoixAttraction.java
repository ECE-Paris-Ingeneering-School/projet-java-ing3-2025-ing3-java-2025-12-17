package Vue;

import DAO.AttractionDAOImpl;
import DAO.DaoFactory;
import Modele.Attraction;
import DAO.AttractionDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Controleur.MainFrame;
import Modele.Client;

import java.net.URL;


public class ChoixAttraction extends JPanel {
    private AttractionDAO mesAttraction;
    private ArrayList<Attraction> touteLesAttractions;
    private Color fondGeneral = new Color(12, 38, 21); // Vert clair
    private Color fond=new Color(25, 77, 42);
    private Color fondSurvol=new Color(75, 251, 126);
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

    class AttractionPanel extends JPanel {
        private ArrayList<Attraction> attractions;
        private int hoveredIndex = -1;

        public AttractionPanel(MainFrame mainFrame, DaoFactory dao, ArrayList<Attraction> attractions) {
            Client client = mainFrame.getClientConnecte();

            this.attractions = attractions;
            setOpaque(false);

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    int oldHoveredIndex = hoveredIndex;
                    hoveredIndex = getAttractionAtPoint(e.getPoint());

                    if (hoveredIndex != oldHoveredIndex) {
                        repaint();
                    }
                }

            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int index = getAttractionAtPoint(e.getPoint());
                    if (index != -1) {
                        Attraction selected = attractions.get(index);
                        if(client != null) {
                            ParcAttractionView pav = new ParcAttractionView(mainFrame, dao, selected);
                            mainFrame.setPanel(pav, "attraction_" + selected);
                        }
                        else if(client == null) {
                            CompteView compteView=new CompteView(mainFrame,dao);
                        }
                    }
                }


            });

        }
        private int getAttractionAtPoint(Point p) {
            int y = p.y;
            int rectHeight = 220;
            int spacing = 50;

            for (int i = 0; i < attractions.size(); i++) {
                int topY = 50 + i * (rectHeight + spacing);
                if (y >= topY && y <= topY + rectHeight) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public Dimension getPreferredSize() {
            int rectHeight = 220;
            int spacing = 50;
            int totalHeight = attractions.size() * (rectHeight + spacing) + 50;
            return new Dimension(800, totalHeight);
        }


        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            for (int i = 0; i < attractions.size(); i++) {
                Attraction attraction = attractions.get(i);

                int rectWidth = getWidth() - 100;  // Largeur du rectangle proportionnelle à la taille de la fenêtre
                int rectHeight = 220; // ou 180, ou toute autre valeur fixe
                int rectX = 50;
                int rectY = 50 + i * (rectHeight + 50);

                if (i == hoveredIndex) {
                    g.setColor(new Color(0, 0, 0, 250)); // Ombre noire semi-transparente
                    g.fillRoundRect(rectX + 10, rectY + 10, rectWidth+5, rectHeight+5, 30, 30);
                    g.setColor(fondSurvol); // Couleur principale avec survol
                } else {
                    g.setColor(fond); // Couleur normale
                }

                g.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 30, 30);
                int lineSpacing = 25;
                int nameX = 100;
                int descriptionX = (int) (getWidth() * 0.25);
                int textX = (int) (getWidth() * 0.66);
                int fontSizeDetails = (int) (getWidth() * 0.03);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD,fontSizeDetails));
                g.drawString(attraction.getAttractionNom(), nameX, 90 + i*(rectHeight+50));
                g.drawImage(attraction.getAttractionImage(), nameX -10, 110 + i * (rectHeight + 50), null);

                g.setFont(new Font("Arial", Font.BOLD, (int) (fontSizeDetails*0.60)));
                drawWrappedText(g, attraction.getAttractionDescription(), descriptionX, 100 + i * (rectHeight + 50), 200);
                g.drawString("Type d'attraction : \n" + attraction.getAttractionType(),(int) (0.95*textX), 75 + i * (rectHeight+50) + lineSpacing);
                g.drawString("Prix de l'attraction : " + attraction.getAttractionPrixComplet() + " €", textX, 125 + i * (rectHeight+50) + lineSpacing);
                g.drawString("Tarif réduit : " +  attraction.getAttractionPrixHab() + " €", textX, 150 + i * (rectHeight+50) + lineSpacing);
                g.drawString("Tarif Jeune : " + attraction.getAttractionPrixJeune() + " €", textX, 175 + i * (rectHeight+50) + lineSpacing);
                g.drawString("Tarif Senior : " + attraction.getAttractionPrixSenior() + " €", textX, 200 + i * (rectHeight+50) + lineSpacing);

            }
        }
    }

    public ChoixAttraction(MainFrame mainFrame, DaoFactory dao) {
        Client client = mainFrame.getClientConnecte();

        setLayout(new BorderLayout());
        setBackground(fondGeneral);

        JLabel loadingLabel = new JLabel("Chargement des attractions...");
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(loadingLabel, BorderLayout.CENTER);

        new SwingWorker<ArrayList<Attraction>, Void>() {
            @Override
            protected ArrayList<Attraction> doInBackground() throws Exception {
                AttractionDAO attractionDAO = new AttractionDAOImpl(dao);
                return attractionDAO.getAllAttractions();
            }

            @Override
            protected void done() {
                try {
                    touteLesAttractions = get();
                    remove(loadingLabel);

                    AttractionPanel attractionPanel = new AttractionPanel(mainFrame, dao, touteLesAttractions);
                    JScrollPane scrollPane = new JScrollPane(attractionPanel);
                    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                    scrollPane.setBackground(fondGeneral);
                    scrollPane.getViewport().setBackground(fondGeneral);
                    add(scrollPane, BorderLayout.CENTER);

                    revalidate();
                    repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.execute();
    }

}