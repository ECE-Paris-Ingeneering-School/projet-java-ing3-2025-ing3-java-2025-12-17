package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class AccueilVue extends JFrame {

    // Composants principaux de l'interface
    private JPanel mainPanel;
    private JLabel titleLabel;

    // Bandeau supérieur avec logo et barre de navigation
    private JPanel headerPanel;
    private JLabel logoLabel;
    private JPanel navPanel;

    // Couleur verte nature foncée pour le bandeau
    private final Color DARK_GREEN = new Color(25, 77, 42); // Vert nature foncé

    public AccueilVue() {
        // Configuration de la fenêtre principale
        setTitle("Parc d'Attractions - Accueil");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialisation des composants graphiques
        initialiserComposants();

        // Rendre la fenêtre visible
        setVisible(true);
    }

    private void initialiserComposants() {
        // Panel principal avec BorderLayout
        mainPanel = new JPanel(new BorderLayout(0, 0)); // Pas d'espacement pour maximiser l'affichage

        // Création du bandeau supérieur vert foncé
        creerHeaderPanel();

        // Création du panel central avec image de fond et message de bienvenue
        creerCentralPanelAvecImage();

        // Ajout du panel principal à la fenêtre
        setContentPane(mainPanel);
    }

    private void creerHeaderPanel() {
        // Bandeau supérieur avec couleur verte nature foncée
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_GREEN);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        headerPanel.setPreferredSize(new Dimension(1000, 70)); // Hauteur fixe pour le bandeau

        // Logo (à gauche)
        logoLabel = new JLabel("LOGO PARC");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setPreferredSize(new Dimension(150, 50));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Navigation (au centre)
        navPanel = new JPanel();
        navPanel.setOpaque(false); // Panel transparent pour prendre la couleur du parent
        navPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton accueilBtn = createNavButton("Accueil", true);
        JButton catalogueBtn = createNavButton("Tout Parcourir", false);
        JButton rechercheBtn = createNavButton("Recherche", false);
        JButton reservationsBtn = createNavButton("Rendez-Vous", false);
        JButton compteBtn = createNavButton("Mon Compte", false);

        navPanel.add(accueilBtn);
        navPanel.add(catalogueBtn);
        navPanel.add(rechercheBtn);
        navPanel.add(reservationsBtn);
        navPanel.add(compteBtn);

        headerPanel.add(navPanel, BorderLayout.CENTER);

        // Ajout du bandeau supérieur au panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private JButton createNavButton(String text, boolean isActive) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setMargin(new Insets(5, 10, 5, 10));
        button.setContentAreaFilled(false); // Bouton transparent
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);

        // Si le bouton est actif, ajouter une bordure blanche en dessous
        if (isActive) {
            button.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        }

        // Effet de survol
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isActive) {
                    button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isActive) {
                    button.setBorder(null);
                }
            }
        });

        return button;
    }

    private void creerCentralPanelAvecImage() {
        // Panel conteneur pour l'image de fond et le message
        JPanel centralPanel = new JPanel(new BorderLayout());

        // Création d'un panel qui contiendra l'image de fond
        JPanel backgroundPanel = new BackgroundPanel();

        // Message de bienvenue superposé à l'image
        JPanel overlayPanel = new JPanel(new GridBagLayout());
        overlayPanel.setOpaque(false);

        // Panel semi-transparent pour le message
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 150)); // Fond semi-transparent
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Titre principal
        titleLabel = new JLabel("Bienvenue au Dino Parc", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Ajout du panel de titre au panel overlay
        overlayPanel.add(titlePanel);

        // Création d'un layered pane pour superposer les éléments
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        // Configuration des tailles pour s'adapter au conteneur
        backgroundPanel.setBounds(0, 0, 1000, 630);
        overlayPanel.setBounds(0, 0, 1000, 630);

        // Ajout des panels au layered pane
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);

        // Ajout du layeredPane au panel central
        centralPanel.add(layeredPane, BorderLayout.CENTER);

        // Ajout du panel central au panel principal
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        // Gestion du redimensionnement
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = centralPanel.getSize();
                backgroundPanel.setBounds(0, 0, size.width, size.height);
                overlayPanel.setBounds(0, 0, size.width, size.height);
                layeredPane.setPreferredSize(size);
            }
        });
    }

    // Classe pour l'image de fond
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            setOpaque(false);

            // Chargement de l'image de fond (à remplacer par votre chemin d'image)
            try {
                // Essayez d'abord de charger depuis les ressources du projet
                ImageIcon icon = new ImageIcon(getClass().getResource("/resources/parc_background.jpg"));

                // Si l'image n'est pas trouvée, essayez avec un chemin absolu (à modifier selon votre système)
                if (icon.getIconWidth() <= 0) {
                    // Utilisez un chemin absolu comme solution de repli
                    icon = new ImageIcon("C:/Images/parc_background.jpg");

                    // Si toujours pas d'image, utilisez une couleur de fond simple
                    if (icon.getIconWidth() <= 0) {
                        setBackground(new Color(10, 30, 15)); // Fond vert très foncé
                        setOpaque(true);
                        return;
                    }
                }

                backgroundImage = icon.getImage();
            } catch (Exception e) {
                // En cas d'erreur, utilisez une couleur de fond simple
                setBackground(new Color(10, 30, 15)); // Fond vert très foncé
                setOpaque(true);
                System.err.println("Erreur de chargement de l'image: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Si l'image a été chargée, la dessiner de manière à remplir le panel
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g.create();

                // Dessiner l'image pour remplir tout le panel
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                // Ajouter un léger assombrissement pour améliorer la lisibilité du texte
                g2d.setColor(new Color(0, 0, 0, 80)); // Noir semi-transparent
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            } else {
                // Si pas d'image, afficher un texte informatif
                g.setColor(new Color(200, 200, 200));
                g.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics fm = g.getFontMetrics();
                String message = "Image de fond non disponible";
                int textWidth = fm.stringWidth(message);
                int textHeight = fm.getHeight();
                g.drawString(message, (getWidth() - textWidth) / 2, getHeight() / 2);
            }
        }
    }

    // Méthode principale pour tester la vue
    public static void main(String[] args) {
        try {
            // Set Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AccueilVue();
            }
        });
    }
}