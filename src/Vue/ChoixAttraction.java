package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.HashMap;
import java.util.Map;

public class ChoixAttraction extends JFrame{
    public void header(){
        // Création du bandeau vert en haut
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 77, 42));
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

            if (i == 0) {
                btn.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
            }
            navPanel.add(btn);
        }
        headerPanel.add(navPanel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
        setVisible(true);
    }
    public ChoixAttraction(){
        header();
    }
    public static void main(String[] args) {
        new ChoixAttraction();
    }

}
