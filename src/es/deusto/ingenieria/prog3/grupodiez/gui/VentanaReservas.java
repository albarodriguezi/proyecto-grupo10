package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.domain.Reserva;

public class VentanaReservas extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Reserva> reservas;
    private JTable tablaReservas;
    private JTextField textoBusqueda;
    private DefaultTableModel modeloDatosReservas;


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

        
		//tamaños
		this.tablaReservas.setRowHeight(35);
		this.tablaReservas.getColumnModel().getColumn(0).setPreferredWidth(100);
		this.tablaReservas.getColumnModel().getColumn(1).setPreferredWidth(300);
		this.tablaReservas.getColumnModel().getColumn(2).setPreferredWidth(100);
		this.tablaReservas.getColumnModel().getColumn(3).setPreferredWidth(50);
        
		//añadir el renderer
		
		
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
                reserva.getFecha().getConcert().getName(), 
                reserva.getFecha(), 
                "Ver Detalles" //la ultima columna, la que no tenia titulo, sera la del boton de ver los detalles
            });
            
        }
    }

    
    
    private void filtroReservas(String filtro) {
        this.modeloDatosReservas.setRowCount(0); //otra vez borrar porqye estamos filtrando y queremos q solo salgan los q cumplan la condicion
        
        //podremos filtrar tanto por el codigo de la reserva, como por el nombre del concierto
        this.reservas.forEach(reserva -> {;
            if (reserva.getLocator().contains(filtro) || reserva.getNombreConcierto().contains(filtro)) {
            	
                this.modeloDatosReservas.addRow(new Object[] {
                    reserva.getLocator(),
                    reserva.getConcert().getName(),
                    reserva.getFecha().getFecha(),
                    "Ver Detalles"
                });
                
            }
        });
    }

    
    public static void main(String[] args) {
    	
    	//hacer unas reservas de prueba. habra q cambiarlo en un futuro para q cuando hagamos una reserva tambien se nos añada y porque tendran otros atributos
        ArrayList<Reserva> reservas = new ArrayList<>();
        
        
        //crear unos conciertos
        Concert AdeleLive = new Concert(Concert.Logo.ADELELIVE, "123456", "Adele Live", 3, 92567, 150);
		Concert BelieveTour = new Concert(Concert.Logo.BELIEVETOUR, "456789", "Believe Tour", 3, 92567, 150);
		Concert BornToDie = new Concert(Concert.Logo.BORNTODIE, "789123", "Born To Die", 3, 92567, 150);
		Concert ErasTour = new Concert(Concert.Logo.ERASTOUR, "789456", "Eras Tour", 3, 92567, 150);
		Concert FutureNostalgia = new Concert(Concert.Logo.FUTURENOSTALGIA, "123123", "future Nostalgia", 3, 92567, 150);
		Concert GutSWorldTour = new Concert(Concert.Logo.GUTSWORLTOUR, "456456", "Guts World Tour", 3, 92567, 150);
		Concert LoveOnTour = new Concert(Concert.Logo.LOVEONTOUR, "789789", "Love on Tour", 3, 92567, 150);
		Concert MusicOfTheSphere = new Concert(Concert.Logo.MUSICOFTHESPHERE, "147369", "Music Of Thw Sphere", 3, 92567, 150);
		Concert OnTheRoadAgain = new Concert(Concert.Logo.ONTHEROADAGAIN, "258147", "on the Road Again", 3, 92567, 150);
		Concert TheMathematicsTour = new Concert(Concert.Logo.THEMATHEMATICSTOUR, "369258", "the Mathematics Tour", 3, 92567, 150);
		
		
		//crear unas fechas locales para meter en el constructor de las fecha
		LocalDate Lfecha1 = LocalDate.of(2024, 11, 15); 
		LocalDate Lfecha2 = LocalDate.of(2023, 12, 25);
		LocalDate Lfecha3 = LocalDate.of(2025, 1, 1);
		LocalDate Lfecha4 = LocalDate.of(2022, 5, 20);
		LocalDate Lfecha5 = LocalDate.of(2024, 3, 8);
		LocalDate Lfecha6 = LocalDate.of(2023, 7, 14);
		LocalDate Lfecha7 = LocalDate.of(2022, 10, 31);
		LocalDate Lfecha8 = LocalDate.of(2026, 4, 19);
		LocalDate Lfecha9 = LocalDate.of(2023, 8, 23);
		LocalDate Lfecha10 = LocalDate.of(2025, 6, 15);
		LocalDate Lfecha11 = LocalDate.of(2024, 9, 9);
		LocalDate Lfecha12 = LocalDate.of(2023, 2, 14);
		
		//las fechas, usando las fechas locales q acabmos de hacer y los conciertos
		Fecha fecha1 = new Fecha(Lfecha1, TheMathematicsTour, 35000);
		Fecha fecha2 = new Fecha(Lfecha2, BornToDie, 60000);
		Fecha fecha3 = new Fecha(Lfecha3, AdeleLive, 75000);
		Fecha fecha4 = new Fecha(Lfecha4, MusicOfTheSphere, 50000);
		Fecha fecha5 = new Fecha(Lfecha5, GutSWorldTour, 29500);
		Fecha fecha6 = new Fecha(Lfecha6, LoveOnTour, 38000);
		Fecha fecha7 = new Fecha(Lfecha7, GutSWorldTour, 27000);
		Fecha fecha8 = new Fecha(Lfecha8, BelieveTour, 16000);
		Fecha fecha9 = new Fecha(Lfecha9, FutureNostalgia, 30000);
		Fecha fecha10 = new Fecha(Lfecha10, MusicOfTheSphere, 41000);
		Fecha fecha11 = new Fecha(Lfecha11, ErasTour, 67000);
		Fecha fecha12= new Fecha(Lfecha12, OnTheRoadAgain, 39500);
        
		
		//crear listas de las personas que atienden
		List<String> lista1 = Arrays.asList("Ana Martínez", "Carlos Ramírez", "Lucía Pérez");
		List<String> lista2 = Arrays.asList("Pedro Gómez", "María López", "José Fernández");
		List<String> lista3 = Arrays.asList("Sofía García", "Miguel Torres", "Raúl Sánchez");
		List<String> lista4 = Arrays.asList("Laura Díaz", "Ángel Ortega", "Elena Navarro");
		List<String> lista5 = Arrays.asList("Sara Vega", "Alberto Molina", "Pablo Herrera");
		List<String> lista6 = Arrays.asList("David Ruiz", "Carmen Ramos", "Antonio Gil");

		//crear las reservas
		reservas.add(new Reserva(fecha1.getConcert().getCode(), fecha1.getConcert(), fecha1, lista1));
		reservas.add(new Reserva(fecha2.getConcert().getCode(), fecha2.getConcert(), fecha2, lista2));
		reservas.add(new Reserva(fecha3.getConcert().getCode(), fecha3.getConcert(), fecha3, lista3));
		reservas.add(new Reserva(fecha4.getConcert().getCode(), fecha4.getConcert(), fecha4, lista4));
		reservas.add(new Reserva(fecha5.getConcert().getCode(), fecha5.getConcert(), fecha5, lista5));
		reservas.add(new Reserva(fecha6.getConcert().getCode(), fecha6.getConcert(), fecha6, lista6));
		reservas.add(new Reserva(fecha7.getConcert().getCode(), fecha7.getConcert(), fecha7, lista1));
		reservas.add(new Reserva(fecha8.getConcert().getCode(), fecha8.getConcert(), fecha8, lista2));
		reservas.add(new Reserva(fecha9.getConcert().getCode(), fecha9.getConcert(), fecha9, lista3));
		reservas.add(new Reserva(fecha10.getConcert().getCode(), fecha10.getConcert(), fecha10, lista4));
		reservas.add(new Reserva(fecha11.getConcert().getCode(), fecha11.getConcert(), fecha11, lista5));
		reservas.add(new Reserva(fecha12.getConcert().getCode(), fecha12.getConcert(), fecha12, lista6));
        
		
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
                JOptionPane.showMessageDialog(button, "Detalles de la Reserva:\n" + "Código: " 
                + reserva.getLocator() + "\n" 
                + "Concierto: " + reserva.getFecha().getConcert().getName() + "\n" 
                + "Fecha: " + reserva.getFecha().getFecha(),  
                "Detalles de Reserva",  
                JOptionPane.INFORMATION_MESSAGE);
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
