package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;

public class DisponibilidadTicket extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private List<Concert> concerts;
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
}