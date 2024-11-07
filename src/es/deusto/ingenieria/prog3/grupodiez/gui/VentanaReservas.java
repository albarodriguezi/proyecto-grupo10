package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.time.LocalDate;

import es.deusto.ingenieria.prog3.grupodiez.domain.Reserva;

public class VentanaReservas extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Reserva> reservas;
    private JTable tablaReservas;
    private JTextField textoBusqueda;
    private DefaultTableModel modeloDatosReservas;
    private int reservaSeleccionada = -1;

    public VentanaReservas(List<Reserva> reservas) {
    	
        this.reservas = reservas;
        this.initTables();
        this.filtroReservas("");

        
        //tabla d reservas en un panel con scroll
        JScrollPane SPReservas = new JScrollPane(this.tablaReservas);
        SPReservas.setBorder(new TitledBorder("Reservas"));
        this.tablaReservas.setFillsViewportHeight(true);

        //para hacer busquedas o filtrar entre la lista de reservas
        this.textoBusqueda = new JTextField(30);
        
      //para que el texto que escribamos en la busqueda funcione
        DocumentListener documentListener = new DocumentListener() {
            
        	@Override
            public void insertUpdate(DocumentEvent e) { filtroReservas(textoBusqueda.getText()); }
            
            @Override
            public void removeUpdate(DocumentEvent e) { filtroReservas(textoBusqueda.getText()); }
            
            @Override
            public void changedUpdate(DocumentEvent e) {}
        };
        
        
        this.textoBusqueda.getDocument().addDocumentListener(documentListener);

        
        //la parte del panel para buscar
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Buscar: "));
        panelBusqueda.add(textoBusqueda);

        
        //el layout del panel princilap
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(BorderLayout.CENTER, SPReservas); //añadirle la tabla de reservas en medio
        panelPrincipal.add(BorderLayout.NORTH, panelBusqueda); //la parte de la busqueda al principio

        
        this.getContentPane().setLayout(new GridLayout(1, 1));
        this.getContentPane().add(panelPrincipal);
        
        this.setTitle("Reservas");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    
    
    
    private void initTables() {
        //configurar las columnas d la tabla
        Vector<String> cabecera = new Vector<>(Arrays.asList("Código Reserva", "Concierto", "Fecha", ""));
        
        this.modeloDatosReservas = new DefaultTableModel(new Vector<>(), cabecera) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; //solo la columna d ver los detalles, porque las otras no queremos editarlas
            }
        };
        
        this.tablaReservas = new JTable(this.modeloDatosReservas);

        
        //config del renderizador y de lo d editar el boton
        this.tablaReservas.getColumn("").setCellRenderer(new ButtonRenderer());
        this.tablaReservas.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), reservas));
        

        // cargar las reservas
        cargarReservas(this.reservas);
        
        
			
    }

    
    
    
    
    private void cargarReservas(List<Reserva> reservas) {
    	
        this.modeloDatosReservas.setRowCount(0); //borrar todo lo q habia antes
        for (Reserva reserva : reservas) {
            modeloDatosReservas.addRow(new Object[] {
                reserva.getLocator(), 
                reserva.getNombreConcierto(), 
                reserva.getFecha(), 
                "Ver Detalles" //la ultima columna, la que no tenia titulo, sera la del boton de ver los detalles
            });
            
        }
    }

    
    
    private void filtroReservas(String filtro) {
        this.modeloDatosReservas.setRowCount(0); //otra vez borrar porqye estamos filtrando y queremos q solo salgan los q cumplan la condicion
        
        //podremos filtrar tanto por el codigo de la reserva, como por el nombre del concierto
        for (Reserva reserva : reservas) {
            if (reserva.getLocator().contains(filtro) || reserva.getNombreConcierto().contains(filtro)) {
                this.modeloDatosReservas.addRow(new Object[] {
                    reserva.getLocator(),
                    reserva.getNombreConcierto(),
                    reserva.getFecha(),
                    "Ver Detalles"
                });
                
            }
        }
    }

    
    public static void main(String[] args) {
    	//hacer unas reservas de prueba. habra q cambiarlo en un futuro para q cuando hagamos una reserva tambien se nos añada y porque tendran otros atributos
        ArrayList<Reserva> reservas = new ArrayList<>();
        reservas.add(new Reserva("AM061", "Guts Tour"));
        reservas.add(new Reserva("BN218", "Eras Tour"));
        reservas.add(new Reserva("CO453", "Guts Tour"));
        reservas.add(new Reserva("DP953", "Eras Tour"));
        SwingUtilities.invokeLater(() -> new VentanaReservas(reservas).setVisible(true));
    }
}


//el boton d la tabla
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() { setOpaque(true); }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "Ver Detalles" : value.toString());
        return this;
        
    }  
}

//la accion del boton
class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private List<Reserva> reservas;
    private int rowIndex;

    public ButtonEditor(JCheckBox checkBox, List<Reserva> reservas) {
        super(checkBox);
        this.reservas = reservas;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reserva reserva = reservas.get(rowIndex);
                JOptionPane.showMessageDialog(button, "Detalles de la Reserva:\n" + "Código: " + reserva.getLocator() + "\n" + "Concierto: " + reserva.getNombreConcierto() + "\n" + "Fecha: " + reserva.getFecha(),  "Detalles de Reserva",  JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        rowIndex = row;
        button.setText((value == null) ? "Ver Detalles" : value.toString());
        return button;
    }
    
    @Override
    public Object getCellEditorValue() { return button.getText(); }
}
