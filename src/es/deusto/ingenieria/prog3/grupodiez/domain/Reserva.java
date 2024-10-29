package es.deusto.ingenieria.prog3.grupodiez.domain;

import java.io.Serializable;
import java.util.List;


public class Reserva implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String locator;
	private Flight flight;
	private long date;
	private List<String> passengers;
	
}
