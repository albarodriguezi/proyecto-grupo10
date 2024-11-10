package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
	private Fecha fecha;
	private List<Fecha> fechas;
	private JTable tablaFechas;
    private DefaultTableModel modeloDatosFechas ;
    private JTextField txtFiltro;

    private Concert concierto;
    private final List<String> headers = Arrays.asList(
    		"FECHA", //fecha del concierto
    		"DISPONIBILIDAD", //AÑADIR DISPONIBILIDAD DE LOS TICKETS EN CADA FECHA
    		"TICKETS RESTANTES", //número de asientos libres
    		"DURACIÓN", //duración del concierto
    		"PRECIO",//precio del concierto //
    		"RESERVAR"
    		);

   
    //para añadir la disponibilidad:
    public DisponibilidadTicket (List<Fecha> concerts) {
    	this.fechas = concerts;
    	loadFechas();
    	}


    //constructor con acceso a la lista de conciertos
    public DisponibilidadTicket (Concert fecha) {
    	this.concierto = fecha;
    	initTables();
    	loadFechas();
    	}

    @Override
    public String getColumnName(int column) {
    	return headers.get(column);
    	}

    @Override
    public int getRowCount() { //cuantas filas tiene la tabla (número de conciertos)
    	if (fechas != null) { //si no está vacia
    		return fechas.size(); //devuelve la cantidad de elementos
    	} else {
    		return 0; //no aparece nada

    	}
    }

    @Override
    public int getColumnCount() {
    	return headers.size();
    }


   
    @Override
    public void setValueAt(Object aValue, int row, int column) { //para cambiar los valores
    if (column == 5) {
            Fecha concert = fechas.get(row);
            // Lógica de reserva del concierto, e.g., reducir el número de asientos
            concert.getSeats();
            fireTableDataChanged(); // Notificar a la tabla de los cambios
        }
    }


    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	return (columnIndex ==5);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
    	Fecha concert = fechas.get(rowIndex);
    	switch (columnIndex) {

    	case 0: return concert.getFecha(); //la fecha de la clase Fecha
    	//disponibilidad --> número de tickets --> número máximo de personas que entran en el recinto (nº seats)
    	case 1: return Float.valueOf((float) concert.getSeats()/concierto.getSeats()); //calcular disponibilidad
    	case 2: return Integer.valueOf(concert.getSeats()); //asientos libres
    	case 3: return Integer.valueOf(concierto.getDuration()); //duración
    	case 4: return Float.valueOf(concierto.getPrice()); //precio
    	case 5: return concert;
    	default: return null;

    	}
    }

    public void setVisible(boolean b) {
    	// TODO Auto-generated method stub

    }


    //Se define un CellRenderer para las cabeceras de las dos tabla usando una expresión lambda
    private void initTables() {
        Vector<String> cabeceraConcert = new Vector<>(headers);
        this.modeloDatosFechas = new DefaultTableModel(new Vector<>(), cabeceraConcert){
        /**
         *
         */
        	private static final long serialVersionUID = 1L;

        	@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
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
        this.tablaFechas.getTableHeader().setReorderingAllowed(false); //Se deshabilita la reordenación de columnas
        this.tablaFechas.getTableHeader().setResizingAllowed(false); //Se deshabilita el redimensionado de las columna
        this.tablaFechas.setAutoCreateRowSorter(true); //Se definen criterios de ordenación por defecto para cada columna
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
        DisponibilidadTicket disponibilidadTicket = new DisponibilidadTicket(fechas);
        disponibilidadTicket.setVisible(true);
        }
        });
    }
   
   
    public DefaultTableModel getModeloDatosFechas() {
    	return modeloDatosFechas;
    }

    public void setModeloDatosFechas(DefaultTableModel modeloDatosFechas) {
    	this.modeloDatosFechas = modeloDatosFechas;
    }

    public void loadFechas() {
   
    	ArrayList<Fecha> fechasc = new ArrayList<Fecha>();
    	try {
    		Scanner sc = new Scanner(new File("resources\\data\\Fecha.csv"));
    		while(sc.hasNextLine()){
    			//System.out.println(sc.nextLine());
    			String linea=sc.nextLine();
    			String[] campos=linea.split(";");
    			Integer dia = Integer.parseInt(campos[0]);
    			Integer mes = Integer.parseInt(campos[1]);
    			Integer ano = Integer.parseInt(campos[2]);
    			String code = campos[3];
    			Integer seats = Integer.parseInt(campos[4]);
    			if (code.equals(concierto.getCode())) {
    				//System.out.println("a");
    				fechasc.add(new Fecha(dia,mes,ano,code,seats));
    			}
       
       
    		}

    		sc.close();

    	} catch (FileNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
   
    	//Se borran los datos del modelo de datos
    	this.modeloDatosFechas.setRowCount(0);
	//System.out.println(fechasc);
    	//Se añaden los comics uno a uno al modelo de datos
    	for (Fecha f:fechasc) {
    	//System.out.println("b");
    		this.modeloDatosFechas.addRow(
    				new Object[] {f.getFecha(), f.getSeats(), f.getSeats(), concierto.getDuration(), concierto.getPrice(),f} );
    	}
    	//System.out.println(modeloDatosFechas.getRowCount());


    }

    //hacemos que se puedan filtrar(creamos el buscador)
    private void filterConcerts(String text) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(this.modeloDatosFechas);
        this.tablaFechas.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
    }
   
   
}


