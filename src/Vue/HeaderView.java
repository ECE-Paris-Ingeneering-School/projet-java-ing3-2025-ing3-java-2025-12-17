package Vue;

import DAO.DaoFactory;
import Controleur.MainFrame;
import Modele.Client;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class HeaderView extends JPanel{

    public HeaderView(MainFrame mainFrame, DaoFactory dao,Client client) {
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 77, 42));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));

        JLabel logoLabel = new JLabel("LOGO PARC");
        logoLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 24));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        headerPanel.add(logoLabel, BorderLayout.WEST);

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
                handleNavigation(dao, item);
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


    private void handleNavigation(DaoFactory dao, String item) {
        MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
        mainFrame.setPanel(new AccueilVue(mainFrame, dao), "parc");
        switch (item) {
            case "Accueil":
                mainFrame.setPanel(new AccueilVue(mainFrame, dao), "accueil");
                break;
            case "Tout Parcourir":
                mainFrame.setPanel(new ChoixAttraction(mainFrame, dao), "choix");
                break;
            case "Mon Compte":
                mainFrame.setPanel(new CompteView(mainFrame, dao), "compte");
                break;
        }
    }
}
