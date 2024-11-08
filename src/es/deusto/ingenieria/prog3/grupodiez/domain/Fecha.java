package es.deusto.ingenieria.prog3.grupodiez.domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Logo;

public class Fecha {
	private LocalDate fecha;
	private String code;
	private int seats;
	private ArrayList<Reserva> reserva=new ArrayList<Reserva>();

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
		return "Fecha [fecha=" + fecha + "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSeats() {
		return seats;
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
}