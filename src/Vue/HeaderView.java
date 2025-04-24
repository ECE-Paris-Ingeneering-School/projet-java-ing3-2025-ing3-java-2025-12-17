package Vue;

import DAO.DaoFactory;
import Controleur.MainFrame;
import Modele.Client;

import javax.swing.*;
import java.awt.*;
// Imports nécessaires pour l'image
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;


public class HeaderView extends JPanel{

    private static final int HEADER_HEIGHT = 100; // Hauteur du header
    private static final int LOGO_TARGET_HEIGHT = 80; // Hauteur souhaitée pour le logo

    public HeaderView(MainFrame mainFrame, DaoFactory dao,Client client) {
        setLayout(new BorderLayout());
        Color fond = new Color(25, 77, 42);
        if (client!=null) {
            if (client.gettypeClient().equals("admin")) {
                fond=new Color(251*2/3, 75*2/3, 42);
            }
        }

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(fond);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        headerPanel.setBackground(new Color(25, 77, 42));
        headerPanel.setPreferredSize(new Dimension(getWidth(), HEADER_HEIGHT));
        if (client!=null) {
            if (client.gettypeClient().equals("admin")) {
                headerPanel.setBackground(new Color(251*2/3, 75*2/3, 42));
            }
        }

        // --- Section Logo ---
        JLabel logoComponent; // JLabel pour l'image ou le texte de secours
        Image logoImage = loadLogoImage(); // Charger l'image

        if (logoImage != null) {
            // Redimensionner l'image pour qu'elle s'adapte bien à la hauteur du header
            Image scaledLogo = resizeImage(logoImage, LOGO_TARGET_HEIGHT);
            ImageIcon logoIcon = new ImageIcon(scaledLogo);
            logoComponent = new JLabel(logoIcon); // Créer le JLabel avec l'icône
        } else {
            // Solution de secours si l'image ne peut pas être chargée
            logoComponent = new JLabel("LOGO PARC"); // Texte original comme fallback
            logoComponent.setFont(new Font("Arial", Font.CENTER_BASELINE, 24));
            logoComponent.setForeground(Color.WHITE);
        }

        // Ajouter une marge autour du logo (image ou texte)
        logoComponent.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // (top, left, bottom, right)
        headerPanel.add(logoComponent, BorderLayout.WEST); // Ajouter le composant logo à gauche
        // --- Fin Section Logo ---


        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 25));
        navPanel.setOpaque(false);

        String[] navItems = {"Accueil", "Tout Parcourir", "Mon Compte"};
        for (int i = 0; i < navItems.length; i++) {
            String item = navItems[i];
            JButton btn = new JButton(navItems[i]);
            btn.setFont(new Font("Arial", Font.BOLD, 30));
            btn.setForeground(Color.WHITE);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setLocation(getWidth()/2,50);
            setPreferredSize(new Dimension(getWidth(), 100));
            btn.addActionListener(e -> {
                handleNavigation(mainFrame, dao, item);
            });
            navPanel.add(btn);
        }
        headerPanel.add(navPanel, BorderLayout.CENTER);

        JPanel comptePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int padding = 30;
                int width = getWidth();
                int height = getHeight();

                int rectWidth = 250;
                int rectHeight = 50;
                int x = width - rectWidth - padding;
                int y = 25;

                g2.setColor(new Color(75*2/3, 251*2/3, 126*2/3));
                if (client!=null) {
                    if (client.gettypeClient().equals("admin")) {
                        g2.setColor(new Color(230, 50, 70));
                    }
                }
                g2.fillRoundRect(x, y, rectWidth, rectHeight, 40, 40);

                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
                g2.setColor(Color.WHITE);
                if (client==null) {
                    g2.drawString("👤 Non connecté", x, 60);
                }
                if (client!=null){
                    g2.drawString("👤 " + client.getclientNom(),x, 60);
                }
                g2.dispose();
            }
        };
        comptePanel.setPreferredSize(new Dimension(300, 100));
        comptePanel.setOpaque(false);
        headerPanel.add(comptePanel, BorderLayout.EAST);
        headerPanel.revalidate();
        headerPanel.repaint();
        add(headerPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    // Méthode pour charger l'image du logo depuis les ressources
    private Image loadLogoImage() {
        try {
            URL imageUrl = getClass().getResource("/asset/logo_parc2.png");
            if (imageUrl == null) {
                System.err.println("Erreur: Logo non trouvé. Vérifiez le chemin: /asset/logo_parc.png");
                return null; // Retourne null si l'image n'est pas trouvée
            }
            return ImageIO.read(imageUrl); // Lit et retourne l'image
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'image du logo.");
            e.printStackTrace();
            return null; // Retourne null en cas d'erreur de lecture
        }
    }

    // Méthode utilitaire pour redimensionner une image en gardant les proportions
    private Image resizeImage(Image originalImage, int targetHeight) {
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);
        if (originalHeight <= 0) {
            return originalImage;
        }
        double aspectRatio = (double) originalWidth / originalHeight;
        int targetWidth = (int) (targetHeight * aspectRatio);
        if (targetWidth < 1) {
            targetWidth = 1;
        }
        // Utilise SCALE_SMOOTH pour une meilleure qualité de redimensionnement
        return originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
    }


    private void handleNavigation(MainFrame mainFrame, DaoFactory dao, String item) {
        Client client = mainFrame.getClientConnecte();
        mainFrame.setPanel(new AccueilVue(mainFrame, dao), "parc");
        switch (item) {
                case "Accueil":
                    mainFrame.setPanel(new AccueilVue(mainFrame, dao), "accueil");
                    break;
                case "Tout Parcourir":
                    mainFrame.setPanel(new ChoixAttraction(mainFrame, dao), "choix");
                    break;
                case "Mon Compte":
                    if (client == null) {
                        mainFrame.setPanel(new CompteView(mainFrame, dao), "compte");
                    } else {
                        mainFrame.setPanel(new ProfileView(mainFrame, dao), "Profil");
                    }
                    break;
        }
    }
}
