package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.prog3.swing.p4.domain.Comic;
import es.deusto.prog3.swing.p4.domain.Personaje;

public class ConcertsListRenderer extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private List<Concert> concerts;
	private JTable tablaConcert;
	private DefaultTableModel modeloDatosConcenrts;
	private JTextField txtFiltro;
	
	
	public ConcertsListRenderer(List<Concert> concert) {
		//Asignamos la lista de comics a la varaible local
		this.concerts = concert;

		//Se inicializan las tablas y sus modelos de datos
		this.initTables();
		//Se cargan los comics en la tabla de comics
		this.loadConcerts();
		
		//La tabla de comics se inserta en un panel con scroll
		JScrollPane scrollPaneConcerts = new JScrollPane(this.tablaConcert);
		scrollPaneConcerts.setBorder(new TitledBorder("Concerts"));
		this.tablaConcert.setFillsViewportHeight(true);
		
		//Se define el comportamiento del campo de texto del filtro
		this.txtFiltro = new JTextField(20);
		
		
		
		DocumentListener documentListener = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				filterConcert(txtFiltro.getText());
					
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filterConcert(txtFiltro.getText());
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("Texto modificados" + txtFiltro.getText());
			}
		};
		
		
		
		this.txtFiltro.getDocument().addDocumentListener(documentListener);
		
		
		
		
		JPanel panelFiltro = new JPanel();
		panelFiltro.add(new JLabel("Filtro por título: "));
		panelFiltro.add(txtFiltro);
		
		JPanel panelConcert = new JPanel();
		panelConcert.setLayout(new BorderLayout());
		panelConcert.add(BorderLayout.CENTER, scrollPaneConcerts);
		panelConcert.add(BorderLayout.NORTH, panelFiltro);
				
		//El Layout del panel principal es un matriz con 2 filas y 1 columna
		this.getContentPane().setLayout(new GridLayout(2, 1));
		this.getContentPane().add(panelConcert);
		
		this.setTitle("Ventana principal de Concerts");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);		
	}
	
	
	private void initTables() {
		//Cabecera del modelo de datos
		Vector<String> cabeceraConcert = new Vector<String>(Arrays.asList( "CODIGO", "NOMBRE", "DURACION", "ASIENTO", "PRECIO", "DISPONIBILIDAD", "RESERVA"));
		//Se crea el modelo de datos para la tabla de comics sólo con la cabecera
		this.modeloDatosComics = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraComics);
		//Se crea la tabla de comics con el modelo de datos
		this.tablaComics = new JTable(this.modeloDatosComics);
		
		
		KeyListener keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_C && e.isControlDown()) {
					System.out.println("Se ha pulsado la C");
					//crear un  nuevo Comic
					//tambien se puede crear en el main
					Comic comicX = new Comic(11, Personaje.Editorial.DC, "Batman and Talia");
				
					//añadirlo a la lista de comics
					//tambien se puede hacer en el main
					comics.add(comicX);
		        
					//cargar la tabla d comics
		        	//tambien se puede hacer en el main
					loadComics();
					
					
					JComponent[] components = new JComponent[4];
					
					JOptionPane.showConfirmDialog(null,
								components,
								"Dialogo de creacion de Comic",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE);
							
					}
				
				
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			
	}

	
	
	
	
}