package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;

<<<<<<< HEAD
public class DisponibilidadTicket extends DefaultTableModel {

=======
public class DisponibilidadTicket extends DefaultTableModel{
	
>>>>>>> branch 'master' of https://github.com/albarodriguezi/proyecto-grupo10.git
	private static final long serialVersionUID = 1L;
<<<<<<< HEAD
	private List<Concert> concerts;
	private Fecha fecha;
=======
	
	private List<Fecha> fechas;
	private Concert concerts;
>>>>>>> branch 'master' of https://github.com/albarodriguezi/proyecto-grupo10.git
	private final List<String> headers = Arrays.asList(
			"FECHA",
			"DURACION",
			"PRECIO",
			"TICKETS RESTANTES",
			"DISPONIBILIDAD",
			"RESERVAR");
	
<<<<<<< HEAD
	//constructor con acceso a la lista de conciertos
	public DisponibilidadTicket (List<Concert> concerts) {
		this.concerts = concerts;
=======
	public DisponibilidadTicket(List<Fecha> fechas) {
		this.fechas = fechas;
>>>>>>> branch 'master' of https://github.com/albarodriguezi/proyecto-grupo10.git
	}
	
	@Override
	public String getColumnName(int column) {
		return headers.get(column);
	}
	
	@Override
<<<<<<< HEAD
	public int getRowCount() { //cuantas filas tiene la tabla (número de conciertos)
		if (concerts != null) { //si no está vacia
			return concerts.size(); //devuelve la cantidad de elementos
		} else { 
			return 0; //no aparece nada
=======
	public int getRowCount() {
		if (fechas != null) {
			return fechas.size();
		}else {
			return 0;
>>>>>>> branch 'master' of https://github.com/albarodriguezi/proyecto-grupo10.git
		}
	}
	
	@Override
	public int getColumnCount() {
		return headers.size();
	}
	
<<<<<<< HEAD
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
	
=======
>>>>>>> branch 'master' of https://github.com/albarodriguezi/proyecto-grupo10.git
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex ==5);
	}
<<<<<<< HEAD
=======
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
	}
	
	@Override
	public Object getValueAt(int rowindex, int columnIndex) {
		Fecha fecha = fechas.get(rowindex);
		
		switch (columnIndex) {
			case 0: return fecha.getFecha();
			case 1: return Integer.valueOf(concerts.getDuration());
			case 2:return Float.valueOf(concerts.getPrice());
			case 3: return Integer.valueOf(concerts.getRemainingSeats());
			case 4: return Double.valueOf(concerts.getRemainingSeats()/Double.valueOf(concerts.getSeats())*100);
			case 5: return fechas;
			default: return null;
			
		}
	}
>>>>>>> branch 'master' of https://github.com/albarodriguezi/proyecto-grupo10.git
}