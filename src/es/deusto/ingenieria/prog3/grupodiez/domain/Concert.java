package es.deusto.ingenieria.prog3.grupodiez.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JOptionPane;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Logo;



public class Concert implements Comparable<Concert>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public enum Logo{
		ADELELIVE, BELIEVETOUR, BORNTODIE, ERASTOUR, FUTURENOSTALGIA, GUTSWORLTOUR, LOVEONTOUR, MUSICOFTHESPHERE, ONTHEROADAGAIN, THEMATHEMATICSTOUR;
	}

	private String imagen;//logo del tour
	private String code; // codigo del concierto
	private String name; //nombre del conciert
	private int duration; //duracion del concierto
	private int seats; //asientos del concierto
	private float price; //precio de los tickets del conierto
	private List<Reserva> reserva;


	
	public Concert(String imagen, String code, String name,int duration, int seats, float price) {
		this.imagen = imagen;
		this.code = code;
		this.name = name;
		this.duration = duration;		
		this.seats = seats;
		this.price = price;

	}
	
	public Concert(String code) {
		this.code = code;
		try {
			Scanner sc = new Scanner(new File("resources\\data\\Concerts.csv"));
			while(sc.hasNextLine()){
		        String linea=sc.nextLine();
		        String[] campos=linea.split(";");
		        String logo = campos[0];
		        String coder = campos[1];
		        String name = campos[2];
		        Integer duration = Integer.parseInt(campos[3]);
		        Float price = Float.parseFloat(campos[5]);
		        if (coder.equals(code)) {
		        	this.imagen = logo;
		        	this.name = name;
		        	this.duration = duration;
		        	this.seats= 92000;
		        	this.price= price;
		        }
		        
		        
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public String getImagen() {
		return imagen;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
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
		
		for(Reserva r : reserva) {
			occupied += r.getAttendees().size();
		}
		return (seats - occupied);
	}
	
	public void setRemainingSeats(int i) {
		// TODO Auto-generated method stub
		
	}

	
	public float getPrice() {
		return price;
	}
	


	@Override
	public String toString() {
		return "Concert [imagen=" + imagen + ", code=" + code + ", name=" + name + ", duration=" + duration + ", seats="
				+ seats + ", price=" + price + ", reserva=" + reserva + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, duration, imagen, name, price, reserva, seats);
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
		return Objects.equals(code, other.code) && duration == other.duration && imagen == other.imagen
				&& name == other.name && Float.floatToIntBits(price) == Float.floatToIntBits(other.price)
				&& Objects.equals(reserva, other.reserva) && seats == other.seats;
	}


	@Override
	public int compareTo(Concert o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*public void setValueAt(Object aValue, int row, int column) { 
        if (column == 5 && "Reservar".equals(aValue)) { 
            Concert concert = concert.get(row);
            if (concert.getRemainingSeats() > 0) {
                // Lógica para confirmar la reserva
                String attendeeInfo = showAttendeeDialog(concert); // Método para capturar información del asistente
                if (attendeeInfo != null) {
                    concert.setRemainingSeats(concert.getRemainingSeats() - 1);
                    fireTableDataChanged(); 
                    showConfirmationDialog(concert, attendeeInfo); // Mostrar la confirmación de la reserva
                }
            } else {
                JOptionPane.showMessageDialog(null, "No quedan asientos disponibles.");
            }
        }
    }
    */

    private String showAttendeeDialog(Concert concert) {
        // Aquí se puede implementar un cuadro de diálogo para capturar los detalles del asistente
        String attendeeName = JOptionPane.showInputDialog(null, "Ingrese el nombre del asistente:");
        return attendeeName; // Retornar el nombre ingresado
    }

    private void showConfirmationDialog(Concert concert, String attendeeInfo) {
        JOptionPane.showMessageDialog(null, 
            String.format("¡Reserva realizada exitosamente!\nAsistente: %s\nConcierto: %s", 
                attendeeInfo, concert.getCode()), 
            "Confirmación de Reserva", 
            JOptionPane.INFORMATION_MESSAGE);
    }


}
