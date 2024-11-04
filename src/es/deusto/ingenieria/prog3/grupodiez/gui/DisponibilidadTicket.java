package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Logo;


public class DisponibilidadTicket extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private List<Concert> concerts;
	private JTable tablaFechas;
    private DefaultTableModel modeloDatosFechas;
    private JTextField txtFiltro;
	private Fecha fecha;
	private final List<String> headers = Arrays.asList(
			"FECHA", //fecha del concierto
			"DISPONIBILIDAD", //AÑADIR DISPONIBILIDAD DE LOS TICKETS EN CADA FECHA
			"TICKETS RESTANTES", //número de asientos libres
			"DURACIÓN", //duración del concierto
			"PRECIO", //precio del concierto
			"RESERVAR" //botón de reservar
			);

	//para añadir la disponibilidad:
	
	//constructor con acceso a la lista de conciertos
	public DisponibilidadTicket (List<Concert> concerts) {
		this.concerts = concerts;
	}
	
	public DisponibilidadTicket (Fecha fecha) {
		this.fecha = fecha;
	}

	@Override
	public String getColumnName(int column) { //obtener el nombre de cada columna
		return headers.get(column);
	}

	@Override
	public int getRowCount() { //cuantas filas tiene la tabla (número de conciertos)
		if (concerts != null) { //si no está vacia
			return concerts.size(); //devuelve la cantidad de elementos
		} else { 
			return 0; //no aparece nada
		}
	}

	@Override
	public int getColumnCount() {
		return headers.size(); //devuelve el número de columnas (títulos de arriba)
	}
	
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	return (columnIndex == 5); //para que DISPONIBILIDAD (índice 2) sea editable
    }
    
    @Override 
    public void setValueAt(Object aValue, int row, int column) { //para cambiar los valores
    	if (column == 5 && aValue.equals("Reservar")) { 
            Concert concert = concerts.get(row);
            // Lógica de reserva del concierto, e.g., reducir el número de asientos
            concert.getRemainingSeats();
            fireTableDataChanged(); // Notificar a la tabla de los cambios
        }
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Concert concert = concerts.get(rowIndex);
		
		switch (columnIndex) {
			case 0: return fecha.getFecha(); //la fecha de la clase Fecha
			//disponibilidad --> número de tickets --> número máximo de personas que entran en el recinto (nº seats)
			case 1: return Float.valueOf((float) concert.getRemainingSeats()/concert.getSeats()); //calcular disponibilidad
			case 2: return Integer.valueOf(concert.getRemainingSeats()); //asientos libres
			case 3: return Integer.valueOf(concert.getDuration()); //duración
			case 4: return Float.valueOf(concert.getPrice()); //precio
			case 5: return concerts; //conciertos
			default: return null;
		}
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	//Se define un CellRenderer para las cabeceras de las dos tabla usando una expresión lambda
	private void initTables() {
        Vector<String> cabeceraConcert = new Vector<>(Arrays.asList("FECHA","DISPONIBILIDAD", "TICKETS RESTANTES", "DURACION", "PRECIO", "RESERVCAR"));
        this.modeloDatosFechas = new DefaultTableModel(new Vector<>(), cabeceraConcert);
        this.tablaFechas = new JTable(this.modeloDatosFechas);

		//Se define un CellRenderer para las celdas de las dos tabla usando una expresión lambda
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());

			
			//La filas pares e impares se renderizan de colores diferentes de la tabla de comics			
			if (table.equals(tablaFechas)) {
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
		
		
		this.tablaFechas.setRowHeight(40);//altira de las fila
		this.tablaFechas.getTableHeader().setReorderingAllowed(false);		//Se deshabilita la reordenación de columnas
		this.tablaFechas.getTableHeader().setResizingAllowed(false);		//Se deshabilita el redimensionado de las columna
		this.tablaFechas.setAutoCreateRowSorter(true);		//Se definen criterios de ordenación por defecto para cada columna
		//Se establecen los renderers al la cabecera y el contenido	
		this.tablaFechas.setDefaultRenderer(Object.class, cellRenderer);
		//Se define la anchura de la columna Título
		this.tablaFechas.getColumnModel().getColumn(2).setPreferredWidth(500);
		
//-----------------------------------------------------------------------------como al seleccionar una fila se va a la pagina de fechas-----------------------------
		this.tablaFechas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tablaFechas.getSelectionModel().addListSelectionListener(e -> {
		    // Verifica que la selección no esté vacía
		    if (tablaFechas.getSelectedRow() != -1) {
		        // Obtiene el ID o el objeto necesario de la fila seleccionada
		        int selectedRow = tablaFechas.getSelectedRow();
		        int idConcierto = (int) tablaFechas.getValueAt(selectedRow, 1); // Ejemplo: obtiene el ID desde la primera columna

		        // Crea y muestra la ventana de DisponibilidadTocket pasando el ID del concierto
		        DisponibilidadTicket disponibilidadTicket = new DisponibilidadTicket(concerts); 
		        disponibilidadTicket.setVisible(true);
		    }
		});
    } 
   
    
    private void loadFechas() {
		//Se borran los datos del modelo de datos
		this.modeloDatosFechas.setRowCount(0);
		//Se añaden los comics uno a uno al modelo de datos
		this.fechas.forEach(f -> this.modeloDatosFechas.addRow(
				new Object[] {f.getFecha(), f.getRemainingSeats(), f.getSeats(), f.getDuration(), f.Price(), f.getReserva()} )
		);
    }

    //hacemos que se puedan filtrar(creamos el buscador)
    private void filterConcerts(String text) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(this.modeloDatosFechas);
        this.tablaFechas.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
    }
    
    
} 

