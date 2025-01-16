package es.deusto.ingenieria.prog3.grupodiez.domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Logo;

/*
Definimos la clase de las fechas en las que se pueden reservar los conciertos, definimos la fecha, el concierto del cual queremos reservar la fecha, 
el codigo del concierto, el numero de asientos que tiene el estadio del concierto y una lista de las reservas que se han hecho para esta fecha
*/
public class Fecha implements Comparable<Fecha>{
	private LocalDate fecha;
	private Concert concert;
	private String code;
	private int seats;
	private ArrayList<Reserva> reserva=new ArrayList<Reserva>();

	
	public Fecha (int dia, int mes, int ano, Concert concert, int seats) {
		this.fecha = LocalDate.of(dia, mes, ano);
		this.concert = concert;
		this.seats = seats;
	}
	
	public Fecha (LocalDate fecha, Concert concert, int seats) {
		this.fecha = fecha;
		this.concert = concert;
		this.seats = seats;
		
	}

	public Fecha (int dia, int mes, int ano, String code, int seats) {
	this.fecha = LocalDate.of(ano, mes, dia);
	this.code = code;
	this.setSeats(seats);
	}



	public LocalDate getFecha() {
		return fecha;
	}
	
	public int getDia() {
		return fecha.getDayOfMonth();
	}
	
	public int getMes() {
		return fecha.getMonthValue();
	}
	
	public int getAno() {
		return fecha.getYear();
	}
	
	public Concert getConcert() {
		return concert;
	}
	
	public int getSeats() {
		return seats;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fecha other = (Fecha) obj;
		return Objects.equals(fecha, other.fecha);
	}

	@Override
	public String toString() {
		return "Fecha [fecha=" + fecha + concert.getName() +"]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public void setSeats(int seats) {
		this.seats = seats;
	}

	public ArrayList<Reserva> getReserva() {
		return reserva;
	}

	public void setReserva(ArrayList<Reserva> reserva) {
		this.reserva = reserva;
	}

	@Override
	public int compareTo(Fecha o) {
		// TODO Auto-generated method stub
		return fecha.compareTo(o.getFecha());
	}
}
