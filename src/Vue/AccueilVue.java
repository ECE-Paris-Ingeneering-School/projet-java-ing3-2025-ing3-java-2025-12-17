package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AccueilVue extends JFrame {

    // Couleur verte nature foncée pour le bandeau
    private final Color DARK_GREEN = new Color(25, 77, 42);

    public AccueilVue() {
        setTitle("Parc d'Attractions - Accueil");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Création du bandeau vert en haut
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_GREEN);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));

        // Logo à gauche
        JLabel logoLabel = new JLabel("LOGO PARC");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Navigation au centre
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navPanel.setOpaque(false);

        // Boutons de navigation
        String[] navItems = {"Accueil", "Tout Parcourir", "Reserver", "Mon Compte"};
        for (int i = 0; i < navItems.length; i++) {
            JButton btn = new JButton(navItems[i]);
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setForeground(Color.WHITE);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);

            // Actif pour "Accueil"
            if (i == 0) {
                btn.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
            }

            navPanel.add(btn);
        }
        headerPanel.add(navPanel, BorderLayout.CENTER);

        // Panel principal pour le fond noir (à remplacer par vidéo)
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(Color.BLACK);

        // Texte de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue au Dino Parc", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 42));
        welcomeLabel.setForeground(Color.WHITE);
        backgroundPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Ajout des composants à la fenêtre
        add(headerPanel, BorderLayout.NORTH);
        add(backgroundPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new AccueilVue();
        });
    }
}