package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;

public class DisponibilidadTicket extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private List<Fecha> fechas;
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        Fecha fecha = fechas.get(rowIndex);
        
        
        switch (columnIndex) {
        case 0: return fecha.getFecha(); // Mostramos la fecha.
        case 1: return 120; // Si la duración no está en Fecha, usar un valor fijo por ahora, o modificar según la lógica.
        case 2: return 50.0f; // Si el precio no está en Fecha, usar un valor fijo por ahora, o modificar según la lógica.
        case 3: return fecha.getSeats(); // Asumimos que 'seats' es el número de asientos disponibles.
        case 4: return (double) fecha.getSeats() / 100; // Calculamos la disponibilidad como porcentaje.
        case 5: return "Reservar"; // Podemos personalizar con un botón de reserva si se necesita.
        default: return null;
    }
	}
}