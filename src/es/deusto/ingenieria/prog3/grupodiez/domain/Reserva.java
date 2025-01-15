package es.deusto.ingenieria.prog3.grupodiez.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


/*
Definimso la clase de las reservas. en la que definimso el codigo localizador de las reservas, la fecha de esta misma, las personas que han hecho la reserva, 
el nombre del concierto para el cual se ha hecho la reserva, el codigo del concierto, el numero de asientos que hay para el concierto
*/
public class Reserva implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String locator; 
	private LocalDate fecha;
	private List<String> attendees;
	private String nombreConcierto;
	private int ucode;
	private static int unique= 010203;
	private int cantidadAsientos; 
	
	
	public Reserva(String locator, Concert concert, LocalDate fecha, List<String> attendees) {
		super();
		this.locator = locator;
		this.nombreConcierto = concert.getName();
		this.fecha = fecha;
		this.attendees = attendees;
		this.ucode=unique;
		unique++;
	}
	
	public Reserva(String locator, String concert, LocalDate fecha, List<String> attendees) {
		super();
		this.nombreConcierto = concert;
		this.locator = locator;
		this.fecha = fecha;
		this.attendees = attendees;
		this.ucode=unique;
		unique++;
	}
	


	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}


	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
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
		return Objects.hash(attendees,  locator);
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
		return Objects.equals(attendees, other.attendees) 
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



	public static int getUnique() {
		return unique;
	}



	public int getUcode() {
		return ucode;
	}




	

	

	
	
	
}
