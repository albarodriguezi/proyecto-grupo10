package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
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
import javax.swing.table.TableRowSorter;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Logo;


public class ConcertsListRenderer extends JFrame {
    
	private static final long serialVersionUID = 1L;

    private List<Concert> concerts;
    private JTable tablaConcert;
    private DefaultTableModel modeloDatosConcerts;
    private JTextField txtFiltro;

    public ConcertsListRenderer(List<Concert> concerts) {
        this.concerts = concerts;
        initTables();
        loadConcert();
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
        this.getContentPane().add(panelConcert);
        this.setTitle("Concerts");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(2000, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initTables() {
        Vector<String> cabeceraConcert = new Vector<>(Arrays.asList("LOGO","CODIGO", "NOMBRE", "DURACION", "TICKETS", "PRECIO"));
        this.modeloDatosConcerts = new DefaultTableModel(new Vector<>(), cabeceraConcert);
        this.tablaConcert = new JTable(this.modeloDatosConcerts);

		//Se define un CellRenderer para las celdas de las dos tabla usando una expresión lambda
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());
						
			//Si el valor es de tipo Logo: se renderiza con la imagen centrada
			if (value instanceof Logo) {
				Logo e = (Logo) value;
				
				
				result.setText(e.toString());		
				result.setToolTipText(e.toString());
				result.setHorizontalAlignment(JLabel.CENTER);
				if (column == 0) {
					switch (e) { 
						case ADELELIVE:
							result.setIcon(new ImageIcon("resources/images/adele live.jpg"));
							break;
						case BELIEVETOUR:
							result.setIcon(new ImageIcon("resources/images/Believe Tour.jpg"));
							break;
						case BORNTODIE:
							result.setIcon(new ImageIcon("resources/images/born to die.jpg"));
							break;
						case ERASTOUR:
							result.setIcon(new ImageIcon("resources/images/Eras Tour.jpg"));
							break;
						case FUTURENOSTALGIA:
							result.setIcon(new ImageIcon("resources/images/future nostalgia.jpg"));
							break;
						case GUTSWORLTOUR:
							result.setIcon(new ImageIcon("resources/images/guts world tour.jpg"));
							break;
						case MUSICOFTHESPHERE:
							result.setIcon(new ImageIcon("resources/images/music of the sphere.jpg"));
							break;
						case ONTHEROADAGAIN:
							result.setIcon(new ImageIcon("resources/images/on the road again.png"));
							break;
						case LOVEONTOUR:
							result.setIcon(new ImageIcon("resources/images/love on tour.png"));
							break;
						case THEMATHEMATICSTOUR:
							result.setIcon(new ImageIcon("resources/images/the mathematics tour.jpg"));
							break;
						default:
				}
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
			if (table.equals(tablaConcert)) {
				if (row % 2 == 0) {
					result.setBackground(new Color(230, 250, 250));
				} else {
					result.setBackground(new Color(190, 230, 220));
				}
			} 
			
			//Si la celda está seleccionada se renderiza con el color de selección por defecto
			if (isSelected) {
				result.setBackground(table.getSelectionBackground());
				result.setForeground(table.getSelectionForeground());			
			}
			
			result.setOpaque(true);
			
			return result;
		};
		//Se crea un CellEditor a partir de un JComboBox()
		JComboBox<Logo> jComboEditorial = new JComboBox<>(Logo.values());		
		DefaultCellEditor editorialEditor = new DefaultCellEditor(jComboEditorial);
		
		
		this.tablaConcert.setRowHeight(40);//altira de las fila
		this.tablaConcert.getTableHeader().setReorderingAllowed(false);		//Se deshabilita la reordenación de columnas
		this.tablaConcert.getTableHeader().setResizingAllowed(false);		//Se deshabilita el redimensionado de las columna
		this.tablaConcert.setAutoCreateRowSorter(true);		//Se definen criterios de ordenación por defecto para cada columna
		//Se establecen los renderers al la cabecera y el contenido	
		this.tablaConcert.setDefaultRenderer(Object.class, cellRenderer);
		//Se define la anchura de la columna Título
		this.tablaConcert.getColumnModel().getColumn(2).setPreferredWidth(500);
		
//-----------------------------------------------------------------------------como al seleccionar una fila se va a la pagina de fechas-----------------------------
		this.tablaConcert.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tablaConcert.getSelectionModel().addListSelectionListener(e -> {
		    // Verifica que la selección no esté vacía
		    if (tablaConcert.getSelectedRow() != -1) {
		        // Obtiene el ID o el objeto necesario de la fila seleccionada
		        int selectedRow = tablaConcert.getSelectedRow();
		        int idConcierto = (int) tablaConcert.getValueAt(selectedRow, 1); // Ejemplo: obtiene el ID desde la primera columna

		        // Crea y muestra la ventana de DisponibilidadTocket pasando el ID del concierto
		       // DisponibilidadTicket disponibilidadTicket = new DisponibilidadTicket(concerts); 
		        //disponibilidadTicket.setVisible(true);
		    }
		});
    } 

   
    
    private void loadConcert() {
		//Se borran los datos del modelo de datos
    	ArrayList<Concert> conciertos = new ArrayList<Concert>();
    	try {
			Scanner sc = new Scanner(new File("resources\\data\\Concerts.csv"));
			while(sc.hasNextLine()){
		        String linea=sc.nextLine();
		        String[] campos=linea.split(";");
		        Logo logo = Logo.valueOf(campos[0]);
		        String code = campos[1];
		        String name = campos[2];
		        Integer duration = Integer.parseInt(campos[3]);
		        Float price = Float.parseFloat(campos[4]);
		        conciertos.add(new Concert(logo,code,name,duration,92000,price));
		        
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.modeloDatosConcerts.setRowCount(0);
		//Se añaden los comics uno a uno al modelo de datos
		conciertos.forEach(c -> this.modeloDatosConcerts.addRow(
				new Object[] {c.getImagen(), c.getCode(), c.getName(), c.getDuration(), c.getSeats(), c.getPrice()} )
		);
    }

    //hacemos que se puedan filtrar(creamos el buscador)
    private void filterConcerts(String text) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(this.modeloDatosConcerts);
        this.tablaConcert.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
    }
    
    
} 

