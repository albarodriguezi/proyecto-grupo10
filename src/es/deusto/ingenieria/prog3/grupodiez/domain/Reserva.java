package es.deusto.ingenieria.prog3.grupodiez.domain;

import java.io.Serializable;
import java.util.List;


public class Reserva implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String locator;
	private Concert concert;
	private Fecha fecha;
	private List<String> atendees;
	
	public Reserva(String locator, Concert concert, Fecha fecha, List<String> atendees) {
		super();
		this.locator = locator;
		this.concert = concert;
		this.fecha = fecha;
		this.atendees = atendees;
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

	public List<String> getAtendees() {
		return atendees;
	}

	public void setAtendees(List<String> atendees) {
		this.atendees = atendees;
	}
	
	
	
	

	
	
	
}
