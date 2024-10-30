package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Nombre;


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
        panelConcert.add(BorderLayout.NORTH, panelFiltro);

        this.getContentPane().setLayout(new GridLayout(1, 1));
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
						
			//Si el valor es de tipo Nombre: se renderiza con la imagen centrada
			if (value instanceof Nombre) {
				Nombre e = (Nombre) value;
				
				result.setText("");		
				result.setToolTipText(e.toString());
				result.setHorizontalAlignment(JLabel.CENTER);
				
				switch (e) { 
					case ADELELIVE:
						result.setIcon(new ImageIcon("resources/images/adelelive.jpg"));
						break;
					case BELIEVETOUR:
						result.setIcon(new ImageIcon("resources/images/Believetour.jpg"));
						break;
					case BORNTODIE:
						result.setIcon(new ImageIcon("resources/images/borntodie.jpg"));
						break;
					case ERASTOUR:
						result.setIcon(new ImageIcon("resources/images/Erastour.jpg"));
						break;
					case FUTURENOSTALGIA:
						result.setIcon(new ImageIcon("resources/images/futurenostalgia.jpg"));
						break;
					case GUTSWORLTOUR:
						result.setIcon(new ImageIcon("/proyecto-grupo10/resources/images/gutsworldtour.jpg"));
						break;
					case MUSICOFTHESPHERE:
						result.setIcon(new ImageIcon("resources/images/loveontour.png"));
						break;
					case ONTHEROADAGAIN:
						result.setIcon(new ImageIcon("resources/images/musicofthesphere.jpg"));
						break;
					case LOVEONTOUR:
						result.setIcon(new ImageIcon("resources/images/ontheroadagain.png"));
						break;
					case THEMATHEMATICSTOUR:
						result.setIcon(new ImageIcon("resources/images/themathematicstour.jpg"));
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
			if (table.equals(tablaConcert)) {
				if (row % 2 == 0) {
					result.setBackground(new Color(250, 249, 249));
				} else {
					result.setBackground(new Color(190, 227, 219));
				}
			//Se usan los colores por defecto de la tabla para las celdas de la tabla de personajes
			} 
			
			//Si la celda está seleccionada se renderiza con el color de selección por defecto
			if (isSelected) {
				result.setBackground(table.getSelectionBackground());
				result.setForeground(table.getSelectionForeground());			
			}
			
			
			result.setOpaque(true);
			
			return result;
		};
        /*TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
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
        };*/
		//Se define un CellRenderer para las cabeceras de las dos tabla usando una expresión lambda
				TableCellRenderer headerRenderer = (table, value, isSelected, hasFocus, row, column) -> {
					JLabel result = new JLabel(value.toString());			
					result.setHorizontalAlignment(JLabel.CENTER);
					
					switch (value.toString()) {
						case "TÍTULO":
						case "NOMBRE":
						case "EMAIL":
							result.setHorizontalAlignment(JLabel.LEFT);
					}
					
					result.setBackground(table.getBackground());
					result.setForeground(table.getForeground());
					
					result.setOpaque(true);
					
					return result;
				};
				
				//Se crea un CellEditor a partir de un JComboBox()
				JComboBox<Editorial> jComboEditorial = new JComboBox<>(Editorial.values());		
				DefaultCellEditor editorialEditor = new DefaultCellEditor(jComboEditorial);
				
				//Se define la altura de las filas de la tabla de comics
				this.tablaComics.setRowHeight(26);
				
				//Se deshabilita la reordenación de columnas
				this.tablaComics.getTableHeader().setReorderingAllowed(false);
				//Se deshabilita el redimensionado de las columna
				this.tablaComics.getTableHeader().setResizingAllowed(false);
				//Se definen criterios de ordenación por defecto para cada columna
				this.tablaComics.setAutoCreateRowSorter(true);
				
				//Se establecen los renderers al la cabecera y el contenido
				this.tablaComics.getTableHeader().setDefaultRenderer(headerRenderer);		
				this.tablaComics.setDefaultRenderer(Object.class, cellRenderer);
				
				//Se establece el editor específico para la Editorial		
				this.tablaComics.getColumnModel().getColumn(1).setCellEditor(editorialEditor);
				
				//Se define la anchura de la columna Título
				this.tablaComics.getColumnModel().getColumn(2).setPreferredWidth(400);
				
				//Se modifica el modelo de selección de la tabla para que se pueda selecciona únicamente una fila
				this.tablaComics.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				//Se define el comportamiento el evento de selección de una fila de la tabla
				this.tablaComics.getSelectionModel().addListSelectionListener(e -> {
					//Se obtiene el ID del comic de la fila seleccionada si es distinta de -1
					if (tablaComics.getSelectedRow() != -1) {
						this.loadPersonajes(this.comics.get((int) tablaComics.getValueAt(tablaComics.getSelectedRow(), 0) - 1));
					}
				});

    private void loadConcert() {
		//Se borran los datos del modelo de datos
		this.modeloDatosConcerts.setRowCount(0);
		//Se añaden los comics uno a uno al modelo de datos
		this.concerts.forEach(c -> this.modeloDatosConcerts.addRow(
				new Object[] {c.getImagen(), c.getCode(), c.name(), c.getDuration(), c.getSeats(), c.getPrice()} )
		);
    }


    private void filterConcerts(String text) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(this.modeloDatosConcerts);
        this.tablaConcert.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
    }
}
}

