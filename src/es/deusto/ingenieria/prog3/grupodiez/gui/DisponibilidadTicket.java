package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;

public class DisponibilidadTicket extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private List<Fecha> fechas;
	private Concert concerts;
	private final List<String> headers = Arrays.asList(
			"FECHA",
			"DURACION",
			"PRECIO",
			"TICKETS RESTANTES",
			"DISPONIBILIDAD",
			"RESERVAR");
	
	public DisponibilidadTicket(List<Fecha> fechas) {
		this.fechas = fechas;
	}
	
	@Override
	public String getColumnName(int column) {
		return headers.get(column);
	}
	
	@Override
	public int getRowCount() {
		if (fechas != null) {
			return fechas.size();
		}else {
			return 0;
		}
	}
	
	@Override
	public int getColumnCount() {
		return headers.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex ==5);
	}
	
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
}