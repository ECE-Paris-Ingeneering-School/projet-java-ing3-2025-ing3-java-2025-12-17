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

public class ParcAttractionView extends JFrame {
    private JTabbedPane tabbedPane;
    private JButton[] buttons;
    private JTable calendarTable;
    private DefaultTableModel calendarModel;
    private JLabel monthLabel;
    private JLabel greenLabel;
    private Color greenColor=new Color(50, 144, 84);
    private JLabel yellowLabel;
    private Color yellowColor=new Color(200, 200, 50);
    private JLabel redLabel;
    private Color redColor=new Color(200, 50, 50);
    private LocalDate currentDate;
    private static Color[][] dayColors; // Tableau pour stocker la couleur de chaque jour
    private static Map<Point, Color> hoveredCells = new HashMap<>(); // Map pour garder une trace des cellules survolées

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

    public ParcAttractionView() {
        setTitle("Parc d'Attractions - Planning");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        header();
        // Initialisation du tableau de couleurs (blanc par défaut)
        dayColors = new Color[6][7]; // 6 lignes et 7 colonnes pour un mois complet
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                dayColors[i][j] = Color.WHITE; // On initialise chaque jour avec la couleur blanche
            }
        }

        // Panel du calendrier
        JPanel calendarPanel = new JPanel(new BorderLayout());
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navigationPanel.setBackground(new Color(12, 38, 21));

        JButton prevMonthButton = new JButton("<");
        JButton nextMonthButton = new JButton(">");
        prevMonthButton.setBackground(new Color(25, 77, 42));
        prevMonthButton.setForeground(Color.WHITE);
        nextMonthButton.setBackground(new Color(25, 77, 42));
        nextMonthButton.setForeground(Color.WHITE);
        monthLabel = new JLabel("", JLabel.CENTER);
        JPanel greenSquare = new JPanel();
        greenSquare.setPreferredSize(new Dimension(10, 10)); // Taille du carré
        greenLabel = new JLabel("Jour disponible");
        JPanel yellowSquare = new JPanel();
        yellowSquare.setPreferredSize(new Dimension(10, 10)); // Taille du carré
        yellowLabel = new JLabel("Jour avec réduction offerte");
        JPanel redSquare = new JPanel();
        redSquare.setPreferredSize(new Dimension(10, 10)); // Taille du carré
        redLabel = new JLabel("Jour indisponible");

        monthLabel.setForeground(Color.WHITE);
        greenSquare.setBackground(greenColor);
        greenLabel.setForeground(Color.WHITE);
        yellowSquare.setBackground(yellowColor);
        yellowLabel.setForeground(Color.WHITE);
        redSquare.setBackground(redColor);
        redLabel.setForeground(Color.WHITE);
        navigationPanel.add(greenSquare);
        navigationPanel.add(greenLabel);
        navigationPanel.add(yellowSquare);
        navigationPanel.add(yellowLabel);
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        navigationPanel.add(prevMonthButton);
        navigationPanel.add(monthLabel);
        navigationPanel.add(nextMonthButton);
        navigationPanel.add(redSquare);
        navigationPanel.add(redLabel);
        calendarPanel.add(navigationPanel, BorderLayout.NORTH);

        // Modèle du calendrier avec en-têtes des jours
        String[] daysOfWeek = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        calendarModel = new DefaultTableModel(null, daysOfWeek) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        calendarTable = new JTable(calendarModel);
        calendarTable.setRowHeight(40);
        calendarTable.setFont(new Font("Arial", Font.BOLD, 14));
        calendarTable.setGridColor(Color.LIGHT_GRAY);
        calendarTable.setSelectionBackground(Color.WHITE);
        calendarTable.setSelectionForeground(Color.WHITE);
        calendarTable.setBackground(Color.WHITE);
        calendarTable.setShowGrid(true);
        calendarTable.setRowSelectionAllowed(false);
        calendarTable.setColumnSelectionAllowed(false);
        calendarTable.setCellSelectionEnabled(false);

        JTableHeader header = calendarTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(25, 77, 42));
        header.setForeground(Color.WHITE);

        currentDate = LocalDate.now();
        fillCalendar(currentDate);

        // Suppression de la couleur orange au survol
        calendarTable.setDefaultRenderer(Object.class, new CalendarRenderer());

        // Gestion des flèches de navigation
        prevMonthButton.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            fillCalendar(currentDate);
        });
        nextMonthButton.addActionListener(e -> {
            currentDate = currentDate.plusMonths(1);
            fillCalendar(currentDate);
        });

        // Gestion des clics sur le calendrier
        calendarTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = calendarTable.getSelectedRow();
                    int col = calendarTable.getSelectedColumn();
                    Object dayValue = calendarModel.getValueAt(row, col);
                    if (dayValue instanceof Integer) {
                        int selectedDay = (Integer) dayValue;
                        LocalDate selectedDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), selectedDay);
                        Color selectedColor = dayColors[row][col]; // Récupérer la couleur du jour cliqué

                        // Afficher la date et la couleur dans une boîte de dialogue
                        if (selectedColor.equals(Color.GREEN)) {
                            JOptionPane.showMessageDialog(ParcAttractionView.this,
                                    "Jour cliqué: " + selectedDate + "\nRéduction de 20% pour ce jour.");
                        } else {
                            JOptionPane.showMessageDialog(ParcAttractionView.this,
                                    "Jour cliqué: " + selectedDate + "\nPas de réduction.");
                        }
                    }
                    // Forcer le rafraîchissement de la table
                    calendarTable.repaint();
                }
            }
        });

        // Ajouter un MouseMotionListener pour gérer la surbrillance des cellules survolées
        calendarTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = calendarTable.rowAtPoint(e.getPoint());
                int col = calendarTable.columnAtPoint(e.getPoint());

                // Si nous survolons une cellule valide
                if (row >= 0 && col >= 0 && row < 6 && col < 7) {
                    Point cell = new Point(row, col);

                    calendarTable.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    // Enlever la surbrillance précédente
                    hoveredCells.clear();

                    // Ajouter la nouvelle cellule survolée à la map de surbrillance
                    hoveredCells.put(cell, new Color(173, 216, 230)); // Surbrillance bleue claire
                    calendarTable.repaint();
                }
            }
        });

        calendarPanel.add(new JScrollPane(calendarTable), BorderLayout.CENTER);
        add(calendarPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void fillCalendar(LocalDate date) {
        calendarModel.setRowCount(0);
        YearMonth month = YearMonth.of(date.getYear(), date.getMonthValue());
        LocalDate firstDay = month.atDay(1);
        int dayOfWeek = (firstDay.getDayOfWeek().getValue() + 5) % 7; // Adapter au début du tableau
        monthLabel.setText(month.getMonth().toString() + " " + month.getYear());

        Object[][] days = new Object[6][7];
        int day = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i == 0 && j < dayOfWeek) || day > month.lengthOfMonth()) {
                    days[i][j] = "";
                } else {
                    days[i][j] = day++;

                    // Attribuer la couleur rouge aux vendredis
                    if (j == 4) {  // Vendredi (colonne 4)
                        dayColors[i][j] = yellowColor;
                    } else {
                        dayColors[i][j] = greenColor;  // Autres jours en vert
                    }
                }
            }
        }
        for (Object[] row : days) {
            calendarModel.addRow(row);
        }
    }

    private static class CalendarRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
            cell.setHorizontalAlignment(JLabel.CENTER);

            if (value instanceof Integer) {
                // Utiliser la couleur du jour stockée dans dayColors
                cell.setBackground(dayColors[row][column]);
            } else {
                cell.setBackground(Color.WHITE); // Case vide ou autre
            }

            // Appliquer la surbrillance si la cellule est survolée
            Point hoveredCell = new Point(row, column);
            if (hoveredCells.containsKey(hoveredCell)) {
                cell.setBackground(hoveredCells.get(hoveredCell)); // Appliquer la surbrillance
            }

            return cell;
        }
    }

    public static void main(String[] args) {
        new ParcAttractionView();
    }
}