package es.deusto.ingenieria.prog3.grupodiez.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Concert {

	private String code; // codigo del concierto
	private String name; //nombre del conciert
	private List<Reserva> reservations; //lista de reservas del concierto
	private int duration; //duracion del concierto
	private int seats; //asientos del concierto
	private float price; //precio de los tickets del conierto
	private double disponibilidad; //disponibilidad del concierto
	
	public Concert(String code, String name, List<Reserva> reservations,
				  int duration, int seats, float price, double disponibilidad) {
		this.code = code;
		this.name = name;
		this.duration = duration;		
		this.seats = seats;
		this.price = price;
		this.reservations = new ArrayList<>();
		this.disponibilidad = disponibilidad;
	}


	public String getCode() {
		return code;
	}

	public String name() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public int getSeats() {
		return seats;
	}
	
	public int getRemainingSeats() {
		int occupied = 0;
		
		for(Reservation r : reservations) {
			occupied += r.getPassengers().size();
		}
		
		return (seats - occupied);
	}
	
	public float getPrice() {
		return price;
	}
	
	public double getDiponibilidad() {
		return disponibilidad;
		
	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}
	
	
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	

	public void addReservation(Reservation reservation) {
		if (reservation != null && !reservations.contains(reservation)) {
			reservations.add(reservation);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, disponibilidad, duration, name, price, seats);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concert other = (Concert) obj;
		return Objects.equals(code, other.code)
				&& Double.doubleToLongBits(disponibilidad) == Double.doubleToLongBits(other.disponibilidad)
				&& duration == other.duration && Objects.equals(name, other.name)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price) && seats == other.seats;
	}


	@Override
	public String toString() {
		return "Concert [code=" + code + ", name=" + name + ", duration=" + duration + ", seats=" + seats + ", price="
				+ price + ", disponibilidad=" + disponibilidad + "]";
	}


}
