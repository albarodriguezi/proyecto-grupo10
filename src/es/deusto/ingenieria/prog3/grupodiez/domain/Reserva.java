package es.deusto.ingenieria.prog3.grupodiez.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class Reserva implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String locator; 
	private Concert concert; 
	private Fecha fecha;
	private List<String> attendees;
	private String nombreConcierto;
	
	private int cantidadAsientos; 
	
	
	public Reserva(String locator, Concert concert, Fecha fecha, List<String> attendees) {
		super();
		this.locator = locator;
		this.concert = concert;
		this.fecha = fecha;
		this.attendees = attendees;
	}
	


	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public Concert getConcert() {
		return concert;
	}

	public void setConcert(Concert concert) {
		this.concert = concert;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public List<String> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<String> atendees) {
		this.attendees = atendees;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attendees, concert, locator);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(attendees, other.attendees) && Objects.equals(concert, other.concert)
				&& Objects.equals(locator, other.locator);
	}

	public String getNombreConcierto() {
		return nombreConcierto;
	}

	public void setNombreConcierto(String nombreConcierto) {
		this.nombreConcierto = nombreConcierto;
	}


	//por si se necesita para restarlos a los asientos totales de un concierto, para ver cuantos quedan
	public int getCantidadAsientos() {
		return attendees.size();
		
	}
	

	

	
	
	
}
