package Vue;

import Controleur.MainFrame;
import DAO.DaoFactory;
import Modele.Client;

import javax.swing.*;
import java.awt.*;
// Import pour charger l'image
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL; // Pour charger depuis les ressources

public class AccueilVue extends JPanel {

    private Image backgroundImage; // Variable pour stocker l'image de fond

    public AccueilVue(MainFrame mainFrame, DaoFactory dao) {
        Client client = mainFrame.getClientConnecte();

        // Charger l'image de fond
        loadImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Créer le label pour le titre
        JLabel titleLabel = new JLabel("Bienvenue au Dino Parc");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuration GridBagConstraints pour centrer le label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Ajouter le label au panel avec les contraintes
        add(titleLabel, gbc);
    }

    // Méthode pour charger l'image de fond
    private void loadImage() {
        try {
            URL imageUrl = getClass().getResource("/asset/Entree_accueil.png");
            if (imageUrl == null) {
                System.err.println("Erreur: Impossible de trouver l'image de fond. Vérifiez le chemin.");
                setBackground(new Color(12, 38, 21));
            } else {
                backgroundImage = ImageIO.read(imageUrl);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'image de fond.");
            e.printStackTrace();
            //Défini une couleur de fond par défaut en cas d'erreur
            setBackground(new Color(12, 38, 21));
        }
    }

    // Redéfinir paintComponent pour dessiner l'image de fond
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        }
    }
}
