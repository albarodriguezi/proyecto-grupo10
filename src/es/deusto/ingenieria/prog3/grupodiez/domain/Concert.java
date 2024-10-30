package es.deusto.ingenieria.prog3.grupodiez.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class Concert implements Comparable<Concert>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public enum Nombre{
		ADELELIVE, BELIEVETOUR, BORNTODIE, ERASTOUR, FUTURENOSTALGIA, GUTSWORLTOUR, LOVEONTOUR, MUSICOFTHESPHERE, ONTHEROADAGAIN, THEMATHEMATICSTOUR;
	}

	private String code; // codigo del concierto
	private Nombre name; //nombre del conciert
	private int duration; //duracion del concierto
	private int seats; //asientos del concierto
	private float price; //precio de los tickets del conierto

	
	public Concert(String code, Nombre name,int duration, int seats, float price) {
		this.code = code;
		this.name = name;
		this.duration = duration;		
		this.seats = seats;
		this.price = price;
	}


	public String getCode() {
		return code;
	}

	public Nombre name() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public int getSeats() {
		return seats;
	}

	
	public float getPrice() {
		return price;
	}



	@Override
	public int hashCode() {
		return Objects.hash(code, duration, name, price, seats);
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
		return Objects.equals(code, other.code) && duration == other.duration && name == other.name
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price) && seats == other.seats;
	}



	@Override
	public String toString() {
		return "Concert [code=" + code + ", name=" + name + ", duration=" + duration + ", seats=" + seats + ", price="
				+ price + "]";
	}


	@Override
	public int compareTo(Concert o) {
		// TODO Auto-generated method stub
		return 0;
	}


}
