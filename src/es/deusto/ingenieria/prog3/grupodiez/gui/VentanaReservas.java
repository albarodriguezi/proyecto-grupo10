package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
import es.deusto.ingenieria.prog3.grupodiez.persistence.GestorBD;

public class VentanaReservas extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Reserva> reservas;
    private JTable tablaReservas;
    private JTextField textoBusqueda;
    private DefaultTableModel modeloDatosReservas;
    private GestorBD gestorBD;


    public VentanaReservas() {
    	
        this.reservas = getReservas();
        this.initTables();
        this.filtroReservas("");

        
        //tabla d reservas en un panel con scroll
        JScrollPane SPReservas = new JScrollPane(this.tablaReservas);
        SPReservas.setBackground(new Color(255,233,244));
        SPReservas.setBorder(new TitledBorder("Reservas"));
        this.tablaReservas.setFillsViewportHeight(true);

        //para hacer busquedas o filtrar entre la lista de reservas
        this.textoBusqueda = new JTextField(30);
        
        
      //para que el texto que escribamos en la busqueda funcione
        //igual hacer algun cambio o en esta parte o en la del filtro para q cuando se borre el texto vuelvan a aparecer todas las reservas xq a veces no va
        DocumentListener documentListener = new DocumentListener() {
        	@Override
            public void insertUpdate(DocumentEvent e) {
        		filtroReservas(textoBusqueda.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	filtroReservas(textoBusqueda.getText());            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	filtroReservas(textoBusqueda.getText());            }

            private void actualizarFiltro() {
                String texto = textoBusqueda.getText().trim(); // Elimina espacios en blanco
                if (!texto.isEmpty()) {
                    filtroReservas(texto);
                } else {
                    // Si el campo de búsqueda está vacío, mostramos todas las reservas
                    
                }
            }
			
		};
		
        
        this.textoBusqueda.getDocument().addDocumentListener(documentListener);

        
        //la parte del panel para buscar
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Buscar: "));
        panelBusqueda.add(textoBusqueda);
        panelBusqueda.setBackground(new Color(255,233,244));

        
        //el layout del panel princilap
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(BorderLayout.CENTER, SPReservas); //añadirle la tabla de reservas en medio
        panelPrincipal.add(BorderLayout.NORTH, panelBusqueda); //la parte de la busqueda al principio
        panelPrincipal.setBackground(new Color(255,233,244));
        
        this.getContentPane().setLayout(new GridLayout(1, 1));
        this.getContentPane().add(panelPrincipal);
        this.setBackground(new Color(255,233,244));
        this.setTitle("Reservas");
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setSize(700, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        
        

    }

    
    
    
    private void initTables() {
        //configurar las columnas d la tabla
        Vector<String> cabecera = new Vector<>(Arrays.asList("Código Reserva", "Concierto", "Fecha", ""));
        
        this.modeloDatosReservas = new DefaultTableModel(new Vector<>(), cabecera) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			

			
        };
    
        this.tablaReservas = new JTable(this.modeloDatosReservas);

        
		//tamaños
		this.tablaReservas.setRowHeight(35);
		this.tablaReservas.getColumnModel().getColumn(0).setPreferredWidth(100);
		this.tablaReservas.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tablaReservas.getColumnModel().getColumn(2).setPreferredWidth(100);
		this.tablaReservas.getColumnModel().getColumn(3).setPreferredWidth(50);

		
		
		
        //
		this.tablaReservas.getColumn("Código Reserva").setCellRenderer(new NormalRenderer());
		this.tablaReservas.getColumn("Concierto").setCellRenderer(new NormalRenderer());
		this.tablaReservas.getColumn("Fecha").setCellRenderer(new NormalRenderer());
        this.tablaReservas.getColumn("").setCellRenderer(new ButtonRenderer());
        this.tablaReservas.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), reservas));
        

        // cargar las reservas
        cargarReservas(getReservas());
        
        
			
    }

    
    
    
    
    private void cargarReservas(List<Reserva> reservas) {
    	
        this.modeloDatosReservas.setRowCount(0); //borrar todo lo q habia antes
        for (Reserva reserva : reservas) {
        
            modeloDatosReservas.addRow(new Object[] {
            	reserva.getLocator(), 
            	AnadirFecha.readConcert().get(reserva.getLocator()).getName(), 
                reserva.getFecha(), 
                "Ver Detalles" //la ultima columna, la que no tenia titulo, sera la del boton de ver los detalles
            });
            
        }
    }

    private ArrayList<Reserva> getReservas(){
    	ArrayList<Reserva> reservas=new ArrayList<Reserva>();
    	Scanner sc;
		try {
			sc = new Scanner(new File("resources\\data\\Reservas.csv"));
			while(sc.hasNextLine()){
		        String linea=sc.nextLine();
		        String[] campos=linea.split(";");
		        String code = campos[0];
		        String date = campos[2];
		        String[] datedet= date.split("-");
		        System.out.println(linea);
		        LocalDate ldate=LocalDate.of(Integer.parseInt(datedet[0]),Integer.parseInt(datedet[1]),Integer.parseInt(datedet[2]));
		        String strAtt = campos[3];
		        String[] attdet= strAtt.split(":");
		        ArrayList<String> nombre = new ArrayList<String>();
		        for (String s:attdet) {
		        	if (!s.equals("")) {
		        		nombre.add(s);
		        	}
		        }
		        
		       //List<String>
		        //Integer duration = Integer.parseInt(campos[3]);
		        Reserva r =new Reserva(code,AnadirFecha.readConcert().get(code),ldate,nombre);
		        reservas.add(r);
		        
		        
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reservas;
    	
    }
    
    
    private void filtroReservas(String filtro) {
        this.modeloDatosReservas.setRowCount(0); //otra vez borrar porqye estamos filtrando y queremos q solo salgan los q cumplan la condicion
        
        //podremos filtrar tanto por el codigo de la reserva, como por el nombre del concierto
        this.reservas.forEach(reserva -> {;
        System.out.println(reserva.getLocator());
        Concert c= AnadirFecha.readConcert().get(reserva.getLocator());
            if (reserva.getLocator().contains(filtro) || c.getName().contains(filtro)) {
            	
                this.modeloDatosReservas.addRow(new Object[] {
                    reserva.getUcode(),
                    c.getName(),
                    reserva.getFecha(),
                    "Ver Detalles"
                });
                
            }
        });
    }

    
    public static void main(String[] args) {
    	
    	//hacer unas reservas de prueba. habra q cambiarlo en un futuro para q cuando hagamos una reserva tambien se nos añada y porque tendran otros atributos
        ArrayList<Reserva> reservas = new ArrayList<>();
        
        
        //crear unos conciertos
        Concert AdeleLive = new Concert("Concert.Logo.ADELELIVE", "123456", "Adele Live", 3, 92567, 150);
		Concert BelieveTour = new Concert("Concert.Logo.BELIEVETOUR", "456789", "Believe Tour", 3, 92567, 150);
		Concert BornToDie = new Concert("Concert.Logo.BORNTODIE", "789123", "Born To Die", 3, 92567, 150);
		Concert ErasTour = new Concert("Concert.Logo.ERASTOUR", "789456", "Eras Tour", 3, 92567, 150);
		Concert FutureNostalgia = new Concert("Concert.Logo.FUTURENOSTALGIA", "123123", "Future Nostalgia", 3, 92567, 150);
		Concert GutsWorldTour = new Concert("Concert.Logo.GUTSWORLTOUR", "456456", "Guts World Tour", 3, 92567, 150);
		Concert LoveOnTour = new Concert("Concert.Logo.LOVEONTOUR", "789789", "Love on Tour", 3, 92567, 150);
		Concert MusicOfTheSphere = new Concert("Concert.Logo.MUSICOFTHESPHERE", "147369", "Music Of The Sphere", 3, 92567, 150);
		Concert OnTheRoadAgain = new Concert("Concert.Logo.ONTHEROADAGAIN", "258147", "On the Road Again", 3, 92567, 150);
		Concert TheMathematicsTour = new Concert("Concert.Logo.THEMATHEMATICSTOUR", "369258", "The Mathematics Tour", 3, 92567, 150);
		
		
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
		Fecha fecha5 = new Fecha(Lfecha5, GutsWorldTour, 29500);
		Fecha fecha6 = new Fecha(Lfecha6, LoveOnTour, 38000);
		Fecha fecha7 = new Fecha(Lfecha7, GutsWorldTour, 27000);
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
		List<String> lista7 = Arrays.asList("Inés Prieto", "Luis Castro", "Patricia Ibáñez");
		List<String> lista8 = Arrays.asList("Francisco Morales", "Beatriz Aguilar", "Ricardo Reyes");
		List<String> lista9 = Arrays.asList("Adriana Herrera", "Victor Soto", "Marta Delgado");
		List<String> lista10 = Arrays.asList("Cristina Serrano", "Andrés Peña", "Esteban Vargas");
		List<String> lista11 = Arrays.asList("Julia Ríos", "Manuel Paredes", "Gabriela Márquez");
		List<String> lista12 = Arrays.asList("Sergio Bautista", "Paola Vargas", "Juan Cordero");

		

		//crear las reservas
		/*reservas.add(new Reserva(fecha1.getConcert().getCode(), fecha1.getConcert(), fecha1, lista1));
		reservas.add(new Reserva(fecha2.getConcert().getCode(), fecha2.getConcert(), fecha2, lista2));
		reservas.add(new Reserva(fecha3.getConcert().getCode(), fecha3.getConcert(), fecha3, lista3));
		reservas.add(new Reserva(fecha4.getConcert().getCode(), fecha4.getConcert(), fecha4, lista4));
		reservas.add(new Reserva(fecha5.getConcert().getCode(), fecha5.getConcert(), fecha5, lista5));
		reservas.add(new Reserva(fecha6.getConcert().getCode(), fecha6.getConcert(), fecha6, lista6));
		reservas.add(new Reserva(fecha7.getConcert().getCode(), fecha7.getConcert(), fecha7, lista7));
		reservas.add(new Reserva(fecha8.getConcert().getCode(), fecha8.getConcert(), fecha8, lista8));
		reservas.add(new Reserva(fecha9.getConcert().getCode(), fecha9.getConcert(), fecha9, lista9));
		reservas.add(new Reserva(fecha10.getConcert().getCode(), fecha10.getConcert(), fecha10, lista10));
		reservas.add(new Reserva(fecha11.getConcert().getCode(), fecha11.getConcert(), fecha11, lista11));
		reservas.add(new Reserva(fecha12.getConcert().getCode(), fecha12.getConcert(), fecha12, lista12));
        
		*/
        SwingUtilities.invokeLater(() -> new VentanaReservas().setVisible(true));
   
    }
}


//el boton d la tabla

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() { setOpaque(true); }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "Ver Detalles" : value.toString());
        if (row % 2 != 0) {
			setBackground(new Color(255, 233, 244));
		} else {
			setBackground(new Color(248, 190, 255));
		}
        return this;
        
    }  
}

class NormalRenderer extends JLabel implements TableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NormalRenderer() { setOpaque(true); }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	
    	
    	if (column==1) {
    		setText((String)value);
    	}else {
    		setText(value.toString());
    	}
    	setFont(new Font("DIN",Font.BOLD,12));
    	setHorizontalAlignment(JLabel.CENTER);
    	
        if (row % 2 == 0) {
			setBackground(new Color(255, 233, 244));
		} else {
			setBackground(new Color(248, 190, 255));
		}
        return this;
        
    }  
}




//desde aqui con ayuda de ia generativa aunq he tenido q cambiar cosas xq me estoy volviendo loca no puc més
class ButtonEditor extends DefaultCellEditor {
    private JButton botonMasInformacion;
    private List<Reserva> reservas;
    private int rowIndex;

    
    public ButtonEditor(JCheckBox checkBox, List<Reserva> reservas) {
        super(checkBox);
        this.reservas = reservas;
        botonMasInformacion = new JButton();

        botonMasInformacion.setOpaque(true);
        botonMasInformacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reserva reserva = reservas.get(rowIndex);
                
                JOptionPane.showMessageDialog(botonMasInformacion, "Código:"+ reserva.getUcode() + "\n" 
                + "Fecha: " + reserva.getFecha() + "\n" 
                + "Asistentes:\n- " +  String.join("\n- ", reserva.getAttendees()),
                
                "Detalles de Reserva", 
                JOptionPane.PLAIN_MESSAGE
                ); //se podria poner con el information message q queda parecido pero con la tipica i d info
            }
        });
    }

    
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        rowIndex = row;
        botonMasInformacion.setText((value == null) ? "Ver Detalles" : value.toString());
        return botonMasInformacion;
    }
    
    
    @Override
    public Object getCellEditorValue() { return botonMasInformacion.getText(); }
}
