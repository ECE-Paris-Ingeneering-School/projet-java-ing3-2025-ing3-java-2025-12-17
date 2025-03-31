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
    private LocalDate currentDate;
    private static Color[][] dayColors; // Tableau pour stocker la couleur de chaque jour
    private static Map<Point, Color> hoveredCells = new HashMap<>(); // Map pour garder une trace des cellules survolées

    public ParcAttractionView() {
        setTitle("Parc d'Attraction");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Initialisation du tableau de couleurs (blanc par défaut)
        dayColors = new Color[6][7]; // 6 lignes et 7 colonnes pour un mois complet
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                dayColors[i][j] = Color.WHITE; // On initialise chaque jour avec la couleur blanche
            }
        }

        // Barre d'onglets en haut avec boutons arrondis et ergonomiques
        tabbedPane = new JTabbedPane();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(50, 50, 50));
        buttons = new JButton[4];
        String[] buttonLabels = {"Accueil", "Attractions", "Billetterie", "Infos"};
        Color buttonColor = new Color(70, 130, 180);

        for (int i = 0; i < 4; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setBackground(buttonColor);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 14));
            buttons[i].setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            buttons[i].setOpaque(true);
            buttons[i].setBorderPainted(false);
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel.add(buttons[i]);
        }

        tabbedPane.add("Menu", panel);
        add(tabbedPane, BorderLayout.NORTH);

        // Panel du calendrier
        JPanel calendarPanel = new JPanel(new BorderLayout());
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevMonthButton = new JButton("<");
        JButton nextMonthButton = new JButton(">");
        monthLabel = new JLabel("", JLabel.CENTER);
        navigationPanel.add(prevMonthButton);
        navigationPanel.add(monthLabel);
        navigationPanel.add(nextMonthButton);
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
        calendarTable.setSelectionBackground(new Color(70, 130, 180));
        calendarTable.setSelectionForeground(Color.WHITE);
        calendarTable.setBackground(Color.WHITE);
        calendarTable.setShowGrid(true);
        calendarTable.setRowSelectionAllowed(false);
        calendarTable.setColumnSelectionAllowed(false);
        calendarTable.setCellSelectionEnabled(false);

        JTableHeader header = calendarTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(70, 130, 180));
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

                    // Attribuer la couleur verte aux vendredis
                    if (j == 4) {  // Vendredi (colonne 4)
                        dayColors[i][j] = Color.GREEN;
                    } else {
                        dayColors[i][j] = Color.WHITE;  // Autres jours en blanc
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
