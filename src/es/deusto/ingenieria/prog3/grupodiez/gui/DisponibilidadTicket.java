package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;

//TAREA 4.A: Modifica el renderer de la tabla de vuelos
//Añade una nueva columna a la tabla de aviones para mostrar el porcentaje de 
//asientos disponibles en el vuelo. La nueva columna se llamará DISPONIBILIDAD 
//y se ubicará justo a la derecha de la columna ASIENTOS LIBRES. Los asientos 
//libres se representan mediante un valor decimal que resulta de dividir el 
//número de asientos ocupados entre el número total de asientos del vuelo.
public class DisponibilidadTicket extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private List<Concert> concerts;
	private Fecha fecha;
	private final List<String> headers = Arrays.asList(
			"FECHA", //fecha del concierto
			"DISPONIBILIDAD", //AÑADIR DISPONIBILIDAD DE LOS TICKETS EN CADA FECHA
			"ASIENTOS LIBRES", //número de asientos libres
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
    	return (columnIndex == 2); //para que DISPONIBILIDAD (índice 2) sea editable
    }
    
    @Override 
    public void setValueAt(Object aValue, int row, int column) { //para cambiar los valores
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Concert concert = concerts.get(rowIndex);
		
		switch (columnIndex) {
			case 0: return fecha.getFecha(); //la fecha de la clase Fecha
			//disponibilidad --> número de tickets --> número máximo de personas que entran en el recinto
			case 1: return Float.valueOf((float) concert.getRemainingSeats()/concert.getSeats()); 
			case 2: return Integer.valueOf(concert.getRemainingSeats());
			case 3: return Integer.valueOf(concert.getDuration());
			case 4: return Float.valueOf(concert.getPrice());
			case 5: return concerts;
			default: return null;
		}
	}
}