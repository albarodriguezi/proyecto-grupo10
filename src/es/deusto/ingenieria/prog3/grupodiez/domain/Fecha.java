package es.deusto.ingenieria.prog3.grupodiez.domain;
import java.time.LocalDate;
import java.util.Objects;

public class Fecha {
	private LocalDate fecha;
	
	public Fecha (int dia, int mes, int ano) {
		this.fecha = LocalDate.of(dia, mes, ano);
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
}