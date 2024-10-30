package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;

public class ConcertsListRenderer extends JFrame {
    
    private static final long serialVersionUID = 1L;

    private List<Concert> concerts;
    private JTable tablaConcert;
    private DefaultTableModel modeloDatosConcerts;
    private JTextField txtFiltro;

    public ConcertsListRenderer(List<Concert> concerts) {
        this.concerts = concerts;
        initTables();
        loadConcerts();
        initGUI();
    }

    private void initGUI() {
        JScrollPane scrollPaneConcerts = new JScrollPane(this.tablaConcert);
        scrollPaneConcerts.setBorder(new TitledBorder("Concerts"));
        this.tablaConcert.setFillsViewportHeight(true);

        this.txtFiltro = new JTextField(20);
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterConcerts(txtFiltro.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterConcerts(txtFiltro.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterConcerts(txtFiltro.getText());
            }
        };
        this.txtFiltro.getDocument().addDocumentListener(documentListener);

        JPanel panelFiltro = new JPanel();
        panelFiltro.add(new JLabel("Filtrar por nombre: "));
        panelFiltro.add(txtFiltro);

        JPanel panelConcert = new JPanel();
        panelConcert.setLayout(new BorderLayout());
        panelConcert.add(BorderLayout.CENTER, scrollPaneConcerts);
        panelConcert.add(BorderLayout.NORTH, panelFiltro);

        this.getContentPane().setLayout(new GridLayout(1, 1));
        this.getContentPane().add(panelConcert);

        this.setTitle("Concerts");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initTables() {
        Vector<String> cabeceraConcert = new Vector<>(Arrays.asList("LOGO","CODIGO", "NOMBRE", "DURACION", "ASIENTO", "PRECIO"));
        this.modeloDatosConcerts = new DefaultTableModel(new Vector<>(), cabeceraConcert);
        this.tablaConcert = new JTable(this.modeloDatosConcerts);

        TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
            JLabel result = new JLabel();

            if (column == 0 && value != null) { // Columna "LOGO"
                // Intentar cargar la imagen desde la ruta
                if (value instanceof String) {
                    String imagePath = (String) value;
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {  // Verificar que el archivo exista
                        ImageIcon icon = new ImageIcon(imagePath);
                        result.setIcon(icon);  // Establecer la imagen en el JLabel
                    } else {
                        result.setText("Imagen no encontrada");
                    }
                }
                result.setHorizontalAlignment(JLabel.CENTER);
            } else if (value != null) { // Para las demás columnas
                result.setText(value.toString());
            }
            
            return result;
        };


        TableCellRenderer headerRenderer = (table, value, isSelected, hasFocus, row, column) -> {
            JLabel result = new JLabel(value.toString());
            
            result.setHorizontalAlignment(JLabel.CENTER);
            result.setOpaque(true);
            return result;
        };

        this.tablaConcert.getTableHeader().setDefaultRenderer(headerRenderer);
        this.tablaConcert.setDefaultRenderer(Object.class, cellRenderer);
        this.tablaConcert.setRowHeight(26);
        this.tablaConcert.setAutoCreateRowSorter(true);
    }

    private void loadConcerts() {
        for (Concert concert : this.concerts) {
            this.modeloDatosConcerts.addRow(new Object[]{
            	
            	concert.getImagen(),
                concert.getCode(),
                concert.name(),
                concert.getDuration(),
                concert.getSeats(),
                concert.getPrice()
            });
        }
    }

    private void filterConcerts(String text) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(this.modeloDatosConcerts);
        this.tablaConcert.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
    }
}

/*package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Nombre;

public class ConcertsListRenderer extends JFrame {

    private static final long serialVersionUID = 1L;
    private List<Concert> concerts;
    private JTable tablaConcert;
    private DefaultTableModel modeloDatosConcenrts;
    private JTextField txtFiltro;

    public ConcertsListRenderer(List<Concert> concerts) {
        this.concerts = concerts;
        this.initTables();
        this.loadConcerts();

        JScrollPane scrollPaneConcerts = new JScrollPane(this.tablaConcert);
        scrollPaneConcerts.setBorder(new TitledBorder("Concerts"));
        this.tablaConcert.setFillsViewportHeight(true);

        this.txtFiltro = new JTextField(20);
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Implement filterConcert(txtFiltro.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                // Implement filterConcert(txtFiltro.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("Texto modificado: " + txtFiltro.getText());
            }
        };
        this.txtFiltro.getDocument().addDocumentListener(documentListener);

        JPanel panelFiltro = new JPanel();
        panelFiltro.add(new JLabel("Filtro por nombre: "));
        panelFiltro.add(txtFiltro);

        JPanel panelConcert = new JPanel();
        panelConcert.setLayout(new BorderLayout());
        panelConcert.add(BorderLayout.CENTER, scrollPaneConcerts);
        panelConcert.add(BorderLayout.NORTH, panelFiltro);

        this.getContentPane().setLayout(new GridLayout(2, 1));
        this.getContentPane().add(panelConcert);

        this.setTitle("Concerts");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initTables() {
        Vector<String> cabeceraConcert = new Vector<>(Arrays.asList("CODIGO", "NOMBRE", "DURACION", "ASIENTO", "PRECIO"));
        this.modeloDatosConcenrts = new DefaultTableModel(new Vector<>(), cabeceraConcert);
        this.tablaConcert = new JTable(this.modeloDatosConcenrts);

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
        };
        this.tablaConcert.addKeyListener(keyListener);

        TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
            JLabel result = new JLabel(value.toString());
            if (value instanceof Nombre) {
                Nombre e = (Nombre) value;
                result.setText("");
                result.setToolTipText(e.toString());
                result.setHorizontalAlignment(JLabel.CENTER);
                switch (e) {
                    case ERASTOUR -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/Erastour.png"));
                    case ADELELIVE -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/AdeleLive.png"));
                    case BELIEVETOUR -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/BelieveTour.png"));
                    case BORNTODIE -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/BornToDie.png"));
                    case FUTURENOSTALGIA -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/FutureNostalgia.png"));
                    case GUTSWORLTOUR -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/GutsWorldTour.png"));
                    case LOVEONTOUR -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/LoveOnTour.png"));
                    case MUSICOFTHESPHERE -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/MusicOfTheSphere.png"));
                    case ONTHEROADAGAIN -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/OnTheRoadAgain.png"));
                    case THEMATHEMATICSTOUR -> result.setIcon(new ImageIcon("/proyecto-grupo10/images/TheMathematicTour.png"));
                }
            } else if (value instanceof Number) {
                result.setHorizontalAlignment(JLabel.CENTER);
            } else {
                result.setText(value.toString());
            }
            result.setOpaque(true);
            return result;
        };

        JComboBox<Nombre> jComboNombre = new JComboBox<>(Nombre.values());
        DefaultCellEditor nombreEditor = new DefaultCellEditor(jComboNombre);
        this.tablaConcert.getColumnModel().getColumn(1).setCellEditor(nombreEditor);

        this.tablaConcert.getTableHeader().setDefaultRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel result = new JLabel(value.toString());
            result.setHorizontalAlignment(JLabel.CENTER);
            result.setOpaque(true);
            return result;
        });
        
        this.tablaConcert.setRowHeight(26);
        this.tablaConcert.getTableHeader().setReorderingAllowed(false);
        this.tablaConcert.getTableHeader().setResizingAllowed(false);
        this.tablaConcert.setAutoCreateRowSorter(true);

        this.tablaConcert.setDefaultRenderer(Object.class, cellRenderer);

        this.tablaConcert.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int filaSeleccionadaConcert = tablaConcert.rowAtPoint(e.getPoint());
                tablaConcert.repaint();
            }
        });
    }

    private void loadConcerts() {
        modeloDatosConcenrts.setRowCount(0);
        for (Concert concert : concerts) {
            Vector<Object> row = new Vector<>();
            row.add(concert.getCode());
            row.add(concert.name());
            row.add(concert.getDuration());
            row.add(concert.getSeats());
            row.add(concert.getPrice());
            modeloDatosConcenrts.addRow(row);
        }
    }
}*/

