package Vue;

import Controleur.MainFrame;
import DAO.DaoFactory;
import Modele.Attraction;
import Modele.Client;

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

public class ParcAttractionView extends JPanel {
    private JTabbedPane tabbedPane;
    private JButton[] buttons;
    private JTable calendarTable;
    private DefaultTableModel calendarModel;
    private JLabel monthLabel;
    private JLabel greenLabel;
    private Color greenColor=new Color(12, 38, 21);
    private JLabel yellowLabel;
    private Color yellowColor=new Color(200, 200, 50);
    private JLabel redLabel;
    private Color redColor=new Color(200, 50, 50);
    private LocalDate currentDate;
    private static Color[][] dayColors; // Tableau pour stocker la couleur de chaque jour
    private static Map<Point, Color> hoveredCells = new HashMap<>(); // Map pour garder une trace des cellules survolées


    public ParcAttractionView(MainFrame mainFrame, DaoFactory dao, Attraction attractionChoisie) {
        Client client = mainFrame.getClientConnecte();

        dayColors = new Color[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                dayColors[i][j] = Color.GRAY;
            }
        }
        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(greenColor);
        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBackground(new Color(25, 77, 42));
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ImageIcon imageIcon = new ImageIcon(attractionChoisie.getAttractionImage());
        Image image = imageIcon.getImage().getScaledInstance(1536/4, 1024/4, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBackground(greenColor);
        imageLabel.setSize(new Dimension(getWidth(), 100));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel imageAndTextPanel = new JPanel(new BorderLayout());
        imageAndTextPanel.setBackground(greenColor);

        JLabel textLabel = new JLabel("<html><div style='color:white;font-size:32px;'>"
                + "<b>" + attractionChoisie.getAttractionNom() + "</b><br></div><div style='color:white;font-size:25px;'>"
                + "Type : " + attractionChoisie.getAttractionType() + "<br>"
                + "Description : " + attractionChoisie.getAttractionDescription());
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        imageAndTextPanel.add(imageLabel, BorderLayout.WEST);
        imageAndTextPanel.add(textLabel, BorderLayout.CENTER);

        JPanel secondLabel = new JPanel(new BorderLayout());
        secondLabel.setBackground(greenColor);

        JLabel tarifLabel = new JLabel("Tarfif Plein "+attractionChoisie.getAttractionPrixComplet()+"€    Tarfif Réduit "+attractionChoisie.getAttractionPrixHab()+"€    Tarfif Jeune "+attractionChoisie.getAttractionPrixJeune()+"€    Tarfif Senior "+attractionChoisie.getAttractionPrixSenior()+"€");
        tarifLabel.setForeground(Color.WHITE);
        tarifLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        tarifLabel.setSize(new Dimension(getWidth(), 30));
        tarifLabel.setBounds(1700, 30, 100,100);
        JLabel titreLabel = new JLabel("<html><div style='color:white;font-size:40px;'>Reservez vos billets</html>");
        titreLabel.setFont(new Font("Arial", Font.PLAIN, 70));
        titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        secondLabel.add(tarifLabel, BorderLayout.NORTH);
        secondLabel.add(titreLabel, BorderLayout.SOUTH);

        topPanel.add(imageAndTextPanel);
        topPanel.add(secondLabel);
        calendarPanel.setLayout(new BoxLayout(calendarPanel, BoxLayout.Y_AXIS));
        calendarPanel.add(topPanel);

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
        navigationPanel.add(prevMonthButton);
        navigationPanel.add(monthLabel);
        navigationPanel.add(nextMonthButton);
        navigationPanel.add(redSquare);
        navigationPanel.add(redLabel);

        String[] daysOfWeek = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
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
        calendarTable.setFillsViewportHeight(true);
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
                        ReservationView res = new ReservationView(mainFrame, dao, attractionChoisie,selectedColor,selectedDate);
                        mainFrame.setPanel(res, "Reservtion" + attractionChoisie);
                    }
                    // Forcer le rafraîchissement de la table
                    calendarTable.repaint();
                }
            }
        });
        calendarTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = calendarTable.rowAtPoint(e.getPoint());
                int col = calendarTable.columnAtPoint(e.getPoint());
                if (row >= 0 && col >= 0 && row < 6 && col < 7) {
                    Point cell = new Point(row, col);
                    calendarTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    hoveredCells.clear();
                    hoveredCells.put(cell, new Color(173, 216, 230));
                }
                else hoveredCells.clear();
                calendarTable.repaint();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(calendarTable);
        calendarPanel.add(tableScrollPane);
        add(calendarPanel, BorderLayout.CENTER);
        calendarPanel.add(navigationPanel,BorderLayout.NORTH);
        setVisible(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();

                calendarPanel.setPreferredSize(new Dimension(width,800));
                navigationPanel.setPreferredSize(new Dimension(width, 300));
                topPanel.setPreferredSize(new Dimension(width, 450));
                imageAndTextPanel.setPreferredSize(new Dimension(width, 400));

                revalidate();
                repaint();
            }
        });

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
                        dayColors[i][j] = greenColor;
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
            cell.setForeground(Color.white);

            if (value instanceof Integer) {
                // Utiliser la couleur du jour stockée dans dayColors
                cell.setBackground(dayColors[row][column]);
            } else {
                cell.setBackground(Color.GRAY); // Case vide ou autre
            }

            // Appliquer la surbrillance si la cellule est survolée
            Point hoveredCell = new Point(row, column);
            if (hoveredCells.containsKey(hoveredCell)) {
                cell.setBackground(hoveredCells.get(hoveredCell)); // Appliquer la surbrillance
            }

            return cell;
        }
    }
}