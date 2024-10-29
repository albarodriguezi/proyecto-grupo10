package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Nombre;
import es.deusto.prog3.swing.p4.domain.Comic;
import es.deusto.prog3.swing.p4.domain.Personaje;
import es.deusto.prog3.swing.p4.domain.Personaje.Editorial;

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
		
		
		//creamos el listener de los concierto
		DocumentListener documentListener = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				//filterConcert(txtFiltro.getText());
					
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				//filterConcert(txtFiltro.getText());
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("Texto modificados" + txtFiltro.getText());
			}
		};
		
		
		
		this.txtFiltro.getDocument().addDocumentListener(documentListener);
		
		
		
		//creamos el jpanel de los conciertos
		JPanel panelFiltro = new JPanel();//creamos en nuevo jpanel
		panelFiltro.add(new JLabel("Filtro por nombre: "));//definimos el label de la pagina al que vamos a llamar "filtro por nombre"
		panelFiltro.add(txtFiltro);//añadimos el filtro de text al panel
		
		JPanel panelConcert = new JPanel();//definimso le panel concierto donde se van a guardar los datos de los conciertos
		panelConcert.setLayout(new BorderLayout());//le definimos los border al panel de los conciertos
		panelConcert.add(BorderLayout.CENTER, scrollPaneConcerts);//en el centro del panel añadimos la lusta de los conciertos
		panelConcert.add(BorderLayout.NORTH, panelFiltro);//en la parte de arriba añadimos el filtro
				
		//El Layout del panel principal es un matriz con 2 filas y 1 columna
		this.getContentPane().setLayout(new GridLayout(2, 1));
		this.getContentPane().add(panelConcert);
		
		this.setTitle("Concerts");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//que se cierre la ventana

		this.setSize(1000, 800);//definimod el tamaño de la pestaña
		this.setLocationRelativeTo(null);//definimos que la pestaña se encuentre en el centro de la pantalla
		this.setVisible(true);//definimso que se pueda ver	
	}
	
	
	private void initTables() {
		//Cabecera del modelo de datos
		Vector<String> cabeceraConcert = new Vector<String>(Arrays.asList( "CODIGO", "NOMBRE", "DURACION", "ASIENTO", "PRECIO"));//definimos las cabeceras de lñas tablas
		//Se crea el modelo de datos para la tabla de comics sólo con la cabecera
		this.modeloDatosConcenrts = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraConcert);//se crean las partes de la tabla segun las cabeceras
		//Se crea la tabla de comics con el modelo de datos
		this.tablaConcert = new JTable(this.modeloDatosConcenrts);//definimos la tabña de conciertos
		
		
		KeyListener keyListener = new KeyListener() {//creamos un key listener

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			};
				
	//para que salga ha que poner al listener
			this.tablaConcert.addKeyListener(keyListener);
			
			//Se define un CellRenderer para las celdas de las dos tabla usando una expresión lambda
			TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
				JLabel result = new JLabel(value.toString());
							
				//Si el valor es de tipo Editorial: se renderiza con la imagen centrada
				if (value instanceof Nombre) {
					Nombre e = (Nombre) value;
					
					result.setText("");		
					result.setToolTipText(e.toString());
					result.setHorizontalAlignment(JLabel.CENTER);
					
					switch (e) { 
						case ERASTOUR:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/Erastour.png"));
							break;
						case ADELELIVE:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/AdeleLive.png"));
							break;
						case BELIEVETOUR:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/BelieveTour.png"));
							break;
						case BORNTODIE:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/BornToDie.png"));
							break;
						case FUTURENOSTALGIA:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/FutureNostalgia.png"));
							break;
						case GUTSWORLTOUR:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/GutsWorldTour.png"));
							break;
						case LOVEONTOUR:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/LoveOnTour.png"));
							break;
						case MUSICOFTHESPHERE:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/MusicOfTheSphere.png"));
							break;
						case ONTHEROADAGAIN:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/OnTheRoadAgain.png"));
							break;
						case THEMATHEMATICSTOUR:
							result.setIcon(new ImageIcon("/proyecto-grupo10/images/TheMathematicTour.png"));
							break;
						default:
					}
				//Si el valor es numérico se renderiza centrado
				} else if (value instanceof Number) {
					result.setHorizontalAlignment(JLabel.CENTER);
				} else {
					//Si el valor es texto pero representa un número se renderiza centrado
					try {
						Integer.parseInt(value.toString());
						result.setHorizontalAlignment(JLabel.CENTER);				
					} catch(Exception ex) {
						result.setText(value.toString());
					}		
				}
				
				//La filas pares e impares se renderizan de colores diferentes de la tabla de comics			
				
				//Se usan los colores por defecto de la tabla para las celdas de la tabla de personajes
				
					result.setBackground(table.getBackground());
					result.setForeground(table.getForeground());
			
				
				
				//Si la celda está seleccionada se renderiza con el color de selección por defecto
			};
			
			//Se define un CellRenderer para las cabeceras de las dos tabla usando una expresión lambda
			TableCellRenderer headerRenderer = (table, value, isSelected, hasFocus, row, column) -> {
				JLabel result = new JLabel(value.toString());			
				result.setHorizontalAlignment(JLabel.CENTER);
				
				switch (value.toString()) {
					case "CODIGO":
					case "NOMBRE":
					case "DURACION":
					case "ASIENTO":
					case "PRECIO":
						result.setHorizontalAlignment(JLabel.LEFT);
				}
				
				result.setBackground(table.getBackground());
				result.setForeground(table.getForeground());
				
				result.setOpaque(true);
				
				return result;
			}; 
			
			//Se crea un CellEditor a partir de un JComboBox()
			JComboBox<Nombre> jComboNombre = new JComboBox<>(Nombre.values());		
			DefaultCellEditor editorialEditor = new DefaultCellEditor(jComboNombre){
			
			//Se define la altura de las filas de la tabla de concert
			this.tablaConcerts.setRowHeight(26);
			
			//Se deshabilita la reordenación de columnas
			this.tablaConcerts.getTableHeader().setReorderingAllowed(false);
			//Se deshabilita el redimensionado de las columna
			this.tablaConcerts.getTableHeader().setResizingAllowed(false);
			//Se definen criterios de ordenación por defecto para cada columna
			this.tablaConcerts.setAutoCreateRowSorter(true);
			
			//Se establecen los renderers al la cabecera y el contenido
			this.tablaConcerts.getTableHeader().setDefaultRenderer(headerRenderer);		
			this.tablaConcerts.setDefaultRenderer(Object.class, cellRenderer);
			
			//Se establece el editor específico para la Editorial		
			//this.tablaConcerts.getColumnModel().getColumn(1).setCellEditor(editorialEditor);
			
			//Se define la anchura de la columna Título
			//this.tablaConcerts.getColumnModel().getColumn(2).setPreferredWidth(400);
			
			//Se modifica el modelo de selección de la tabla para que se pueda selecciona únicamente una fila
			//this.tablaConcerts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//Se define el comportamiento el evento de selección de una fila de la tabla
			//this.tablaConcerts.getSelectionModel().addListSelectionListener(e -> this.loadConcerts();(this.concerts.get((int) tablaConcert.getValueAt(tablaConcert.getSelectedRow(), 0) - 1));
				}
				
			
			MouseMotionAdapter mouseMoveAdapter = new MouseMotionAdapter() {
				public void mouseMoved(MouseEvent e){
					filaSeleccionadaConcert = tablaConcert.rowAtPoint(e.getPoint());
					tablaConcert.repaint();
				}}
			
			
			
			this.tablaConcert.addMouseMotionListener(mouseMoveAdapter); 
			
			//Se deshabilita la reordenación de columnas
			this.tablaConcert.getTableHeader().setReorderingAllowed(false);
			//Se deshabilita el redimensionado de las columna
			this.tablaConcerttablaConcert.getTableHeader().setResizingAllowed(false);
			//Se definen criterios de ordenación por defecto para cada columna
			this.tablaConcert.setAutoCreateRowSorter(true);
			
			this.tablaConcert.getTableHeader().setDefaultRenderer(headerRenderer);		
			this.tablaConcert.setDefaultRenderer(Object.class, cellRenderer);
			this.tablaConcert.setRowHeight(26);
			this.tablaConcert.getColumnModel().getColumn(2).setPreferredWidth(200);
			this.tablaConcert.getColumnModel().getColumn(3).setPreferredWidth(200);
		}
	}
	};
};

	
	
	
	
}