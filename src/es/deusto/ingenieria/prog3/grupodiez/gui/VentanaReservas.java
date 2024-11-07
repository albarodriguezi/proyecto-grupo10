package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.grupodiez.domain.Reserva;

public class VentanaReservas extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static List<Reserva> reservas;
	private JTable tablaReservas;
	private JTextField textoBusqueda;
	private DefaultTableModel modeloDatosReservas;
	private int reservaSeleccionada = -1;
	
	public VentanaReservas(List<Reserva> reservas) {
		
		this.reservas = reservas;
		this.initTables();
		this.filtroR("");
		
		
		//tabla d reservas en un panel con scroll
		JScrollPane SPReservas = new JScrollPane(this.tablaReservas);
		SPReservas.setBorder(new TitledBorder("Reservas"));
		this.tablaReservas.setFillsViewportHeight(true);
		
		//para hacer busquedas o filtrar entre la lista de reservas
				this.textoBusqueda = new JTextField(20);
				
		//para que el texto que escribamos en la busqueda funcione
		DocumentListener documentListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtroR(textoBusqueda.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtroR(textoBusqueda.getText());				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("Texto modificado:" + textoBusqueda.getText());				
			}
			
		};
		this.textoBusqueda.getDocument().addDocumentListener(documentListener);
		
		//la parte del panel para buscar
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.add(new JLabel("Buscar: "));
		panelBusqueda.add(textoBusqueda);
		
		//el layout 
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		panelPrincipal.add(BorderLayout.CENTER, SPReservas); //añadirle la tabla de reservas con scroll en medio
		panelPrincipal.add(BorderLayout.NORTH, panelBusqueda); //la parte de busqueda al principio
		
		
		this.getContentPane().setLayout(new GridLayout(1, 1));
		this.getContentPane().add(panelPrincipal);
		
		this.setTitle("Reservas");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
	}

    
	
	
	
	
    
    
	
	private void initTables() {
		//titulos en la cabecera
		Vector<String> cabecera = new Vector<String>(Arrays.asList( "Código Reserva", "Concierto", "Fecha", ""));	
		this.modeloDatosReservas = new DefaultTableModel(new Vector<Vector<Object>>(), cabecera) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		this.tablaReservas = new JTable(this.modeloDatosReservas);
		
		
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());
			
			//las filas pares e impares estan renderizadas con distintos colore			
			if (table.equals(tablaReservas)) {
				if (row % 2 == 0) {
					result.setBackground(new Color(240, 249, 249));
				} else {
					result.setBackground(new Color(180, 227, 219));
				}
				
			} else {
				result.setBackground(table.getBackground());
				result.setForeground(table.getForeground());
			}

			
			if (isSelected) {
				result.setBackground(table.getSelectionBackground());
				result.setForeground(table.getSelectionForeground());			
			}
			
			
			//para poder ver que reserva estamos seleccionando
			if (reservaSeleccionada != -1 && table.equals(tablaReservas) && row == reservaSeleccionada) {
				result.setBackground(new Color(255, 204, 255));
			}
			
			MouseMotionAdapter mouseMoveAdapter = new MouseMotionAdapter() {
				public void mouseMoved(MouseEvent e) {

					reservaSeleccionada = tablaReservas.rowAtPoint(e.getPoint());
					tablaReservas.repaint();
				}
			};
			
			result.setOpaque(true);
			
			return result;
		};
		
		
		
		//titulos
		Vector<String> cabeceraPersonajes = new Vector<String>(Arrays.asList( "Código Reserva", "Concierto", "Fecha", "")); //el ultimo para lo d "ver detalles"
		//otro renderer, esta vez para los titulos d la tabla
		TableCellRenderer titulosRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());			
			result.setHorizontalAlignment(JLabel.CENTER);
			
			switch (value.toString()) {
				case "Código Reserva":
				case "Concierto":
				case "Fecha":
					result.setHorizontalAlignment(JLabel.LEFT);
			}
			
			result.setBackground(table.getBackground());
			result.setForeground(table.getForeground());
			
			result.setOpaque(true);
			
			return result;
		};
		
		
		this.tablaReservas.setDefaultRenderer(Object.class, cellRenderer);
		this.tablaReservas.setRowHeight(30);
		this.tablaReservas.getTableHeader().setReorderingAllowed(false);
		this.tablaReservas.getTableHeader().setResizingAllowed(false);
		
		
		
		this.tablaReservas.getTableHeader().setDefaultRenderer(titulosRenderer);	
		
		//tamaños d las columnas d la tabla
		this.tablaReservas.getColumnModel().getColumn(0).setPreferredWidth(100);
		this.tablaReservas.getColumnModel().getColumn(1).setPreferredWidth(300);
		this.tablaReservas.getColumnModel().getColumn(2).setPreferredWidth(100);

		//especificar q solo se puede seleccionar una fila porque luego pondremos q se enseñen los detalles de una reserva
		this.tablaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/*
		//para cuando seleccinonemos una reserva el evento que queremos q suceda
		this.tablaReservas.getSelectionModel().addListSelectionListener(e -> {
			if (tablaReservas.getSelectedRow() != -1) { //sin contar -1 porque eso no es una reserva, es porque hay q darle un valor cuando no hay nada seleccionado
				this.loadPersonajes(this.comics.get((int) tablaComics.getValueAt(tablaComics.getSelectedRow(), 0) - 1));
			}
		});
		*/
		

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	private void filtroR(String string) {
		// TODO Auto-generated method stub	
	}


    public static void main(String[] args) {
        // Crear algunas reservas de ejemplo
        ArrayList<Reserva> reservas = new ArrayList<>();
        reservas.add(new Reserva("Anton Martin", "Guts Tour"));
        reservas.add(new Reserva("Berta Nogal", "Eras Tour"));
        reservas.add(new Reserva("Cecilia Olabarria", "Guts Tour"));
        reservas.add(new Reserva("David Perez", "Eras Tour"));
        

        // Mostrar la ventana
        SwingUtilities.invokeLater(() -> {
            VentanaReservas ventana = new VentanaReservas(reservas);
            ventana.setVisible(true);
        });
    }
	

}
