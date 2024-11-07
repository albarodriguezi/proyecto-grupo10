package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
		
		
		this.getContentPane().setLayout(new GridLayout(2, 1));
		this.getContentPane().add(panelPrincipal);
		
		this.setTitle("Ventana principal de Comics");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
	}

    
	
	
	
	
	
	
	
    
    
	
	private void initTables() {
		Vector<String> cabecera = new Vector<String>(Arrays.asList( "locator", "concierto", ""));	
		
		
		this.tablaReservas = new JTable(this.modeloDatosReservas);
		
		
		//el table cell renderer
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());
			
			//las filas pares e impares estan renderizadas con distintos colore			
			if (table.equals(tablaReservas)) {
				if (row % 2 == 0) {
					result.setBackground(new Color(240, 249, 249));
				} else {
					result.setBackground(new Color(180, 227, 219));
				}
				
			//Se usan los colores por defecto de la tabla para las celdas de la tabla de personajes
			} else {
				result.setBackground(table.getBackground());
				result.setForeground(table.getForeground());
			}
			
			//Si la celda está seleccionada se renderiza con el color de selección por defecto
			if (isSelected) {
				result.setBackground(table.getSelectionBackground());
				result.setForeground(table.getSelectionForeground());			
			}
			
			
			/*
			 * EDITAR LUEGO PARA Q CUADNO TENGAS EL RATON ENCIMA D UNA RESERVA SE T RESALTE,
			 * HACER LO DEL MOUSE MOTION ADAPTER Y ESO
			if (table.equals(tablaReservas) && row == filaSeleccionada && filaSeleccionada != -1) {
				result.setBackground(Color.PINK);
			}
			*/
			
			
			result.setOpaque(true);
			
			return result;
		};
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	private void filtroR(String string) {
		// TODO Auto-generated method stub	
	}


    public static void main(String[] args) {
        // Crear algunas reservas de ejemplo
        ArrayList<Reserva> reservas = new ArrayList<>();
        reservas.add(new Reserva("Maria Garcia", "Festival Jazz"));
        

        // Mostrar la ventana
        SwingUtilities.invokeLater(() -> {
            VentanaReservas ventana = new VentanaReservas(reservas);
            ventana.setVisible(true);
        });
    }
	

}
