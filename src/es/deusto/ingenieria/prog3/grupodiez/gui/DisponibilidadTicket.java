package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;

//TAREA 4.A: Modifica el renderer de la tabla de vuelos
//Añade una nueva columna a la tabla de aviones para mostrar el porcentaje de 
//asientos disponibles en el vuelo. La nueva columna se llamará DISPONIBILIDAD 
//y se ubicará justo a la derecha de la columna ASIENTOS LIBRES. Los asientos 
//libres se representan mediante un valor decimal que resulta de dividir el 
//número de asientos ocupados entre el número total de asientos del vuelo.
public class DisponibilidadTicket extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Concert> concerts;
	private final List<String> headers = Arrays.asList(
			"CÓDIGO", //código de cada concierto
			"NOMBRE", //nombre del concierto
			"DURACIÓN", //duración aproximada del concierto
			"ASIENTOS", //asientos libres
			"PRECIO", //precio del concierto
			"DISPONIBILIDAD", //disponibilidad para añadir
			"RESERVAR" 
			);

	public DisponibilidadTicket (List<Concert> concerts) {
		this.concerts = concerts;
	}
	
	@Override
	public String getColumnName(int column) { //obtener el nombre de cada columna
		return headers.get(column);
	}

	@Override
	public int getRowCount() { //cuantas filas tiene la tabla (número de conciertos
		if (concerts != null) { //si no está vacia
			return concerts.size(); //devuelve la cantidad de elementos
		} else { 
			return 0; //no aparece nada
		}
	}

	@Override
	public int getColumnCount() {
		return headers.size(); 
	}
	
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //Hay que modificar este método para que la columna del botón sea editable.
    	return (columnIndex == headers.size()-1);
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int column) {    	
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Flight flight = flights.get(rowIndex);
		
		switch (columnIndex) {
			case 0: return flight.getAirline();
			case 1: return flight.getCode();
			case 2: return flight.getOrigin();
			case 3: return flight.getDestination();
			case 4: return Integer.valueOf(flight.getDuration());
			case 5: return Float.valueOf(flight.getPrice());
			case 6: return Integer.valueOf(flight.getReservations().size());
			case 7: return Integer.valueOf(flight.getRemainingSeats());
			//La disponibilidad se calcula como el cociente entre RemainingSeats y Seats
			case 8: return Float.valueOf((float) flight.getRemainingSeats()/flight.getSeats());
			case 9: return flight;
			default: return null;
		}
	}
}