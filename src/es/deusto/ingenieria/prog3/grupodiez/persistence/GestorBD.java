package es.deusto.ingenieria.prog3.grupodiez.persistence;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.domain.Reserva;

public class GestorBD {

	protected static final String DRIVER_NAME = "org.sqlite.JDBC";
	protected static final String DATABASE_FILE = "resources/db/database.db";
	protected static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;
	
	public GestorBD() {		
		try {
			//Cargar el diver SQLite
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException ex) {
			System.err.format("\n* Error al cargar el driver de BBDD: %s", ex.getMessage());
			ex.printStackTrace();
		}
	}
		
	public void crearBBDD() {
		//Se abre la conexión y se obtiene el Statement
		//Al abrir la conexión, si no existía el fichero, se crea la base de datos
		//if (properties.get("createBBDD").equals("true")) {
			//La base de datos tiene 3 tablas: Personaje, Comic y Personajes_Comic
			String sql1 = "CREATE TABLE IF NOT EXISTS CONCIERTO (\n"
					+ " IMAGE TEXT NOT NULL,\n"
	                + " ID TEXT PRIMARY KEY ,\n"
	                + " NAME TEXT NOT NULL,\n"
	                + " DURATION INT NOT NULL,\n"
	                + " TICKETS INT NOT NULL,\n"
	                + " PRICE FLOAT NOT NULL,\n"
	                + " UNIQUE(ID));";
	
			String sql2 = "CREATE TABLE IF NOT EXISTS FECHA (\n"
	                + " DAY INTEGER NOT NULL,\n"
	                + " MONTH INTEGER NOT NULL,\n"
	                + " YEAR INTEGER NOT NULL,\n"
	                + " CONCERTID TEXT NOT NULL,\n"
	                + " SEATSLEFT INT NOT NULL\n"
	                + " FOREIGN KEY(CONCERTID));";
	
			String sql3 = "CREATE TABLE IF NOT EXISTS RESERVA (\n"
					+ " CONCERTID TEXT NOT NULL,\n"
					+ " CONCERTNAME TEXT NOT NULL,\n"
	                + " FECHA DATE NOT NULL,\n"
	                + " FOREIGN KEY(CONCERTID) REFERENCES Comic(id) ON DELETE CASCADE\n"
	                + " ATTENDEES TEXT NOT NULL,\n"
	                + ");";
			
	        //Se abre la conexión y se crea un PreparedStatement para crer cada tabla
			//Al abrir la conexión, si no existía el fichero por defecto, se crea.
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3)) {
				
				//Se ejecutan las sentencias de creación de las tablas
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute()) {
		        	System.out.println("Se han creado las tablas");
		        }
			} catch (Exception ex) {
				System.out.println(String.format("Error al crear las tablas: %s", ex.getMessage()));
			}
		//}
	}
	
	public void borrarBBDD() {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("DROP TABLE IF EXISTS CONCIERTO");
			 PreparedStatement stmt2 = con.prepareStatement("DROP TABLE IF EXISTS FECHA");
			 PreparedStatement stmt3 = con.prepareStatement("DROP TABLE IF EXISTS RESERVA"))
		
{
			
	        //String sql = "DROP TABLE IF EXISTS CLIENTE";
			
	        //Se ejecuta la sentencia de creación de la tabla Estudiantes
	        if (!stmt.execute()) {
	        	System.out.println("\n\n- Se ha borrado la tabla Cliente");
	        }
		} catch (Exception ex) {
			System.err.format("\n* Error al borrar la BBDD: %s", ex.getMessage());
			ex.printStackTrace();			
		}
		
		try {
			//Se borra el fichero de la BBDD
			Files.delete(Paths.get(DATABASE_FILE));
			System.out.println("\n- Se ha borrado el fichero de la BBDD");
		} catch (Exception ex) {
			System.err.format("\n* Error al borrar el archivo de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}
	}
	
	public void insertarDatos(Concert... conciertos) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("INSERT INTO CONCIERTO (IMAGE, ID, NAME,DURATION,TICKETS,PRICE) VALUES (?, ?, ?,?,?,?);")) {
			//Se define la plantilla de la sentencia SQL
			//String sql = "INSERT INTO CLIENTE (NAME, EMAIL, PASSWORD) VALUES ('%s', '%s', '%s');";
			
			System.out.print("\n- Insertando conciertos...");
			
			//Se recorren los clientes y se insertan uno a uno
			for (Concert c : conciertos) {
				stmt.setString(1,c.getImagen());
				stmt.setString(2,c.getCode());
				stmt.setString(3,c.getName());
				stmt.setInt(4,c.getDuration());
				stmt.setInt(5,c.getSeats());
				stmt.setFloat(6,c.getPrice());
				//stmt.toString();
				if (1 == stmt.executeUpdate()) {					
					System.out.format("\n - Concierto insertado: %s", c.toString());
				} else {
					System.out.format("\n - No se ha insertado el concierto: %s", c.toString());
				}
			}			
		} catch (Exception ex) {
			System.err.format("\n* Error al insertar datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}				
	}
	
	public void insertarDatos(Fecha... fechas) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("INSERT INTO FECHA (DAY, MONTH, YEAR,CONCIERTOID,SEATSLEFT) VALUES (?, ?, ?,?,?);")) {
			//Se define la plantilla de la sentencia SQL
			//String sql = "INSERT INTO CLIENTE (NAME, EMAIL, PASSWORD) VALUES ('%s', '%s', '%s');";
			
			System.out.print("\n- Insertando conciertos...");
			
			//Se recorren los clientes y se insertan uno a uno
			for (Fecha f : fechas) {
				stmt.setInt(1,f.getDia());
				stmt.setInt(2,f.getMes());
				stmt.setInt(3,f.getAno());
				stmt.setString(4,f.getCode());
				stmt.setInt(5,f.getSeats());
				//stmt.toString();
				if (1 == stmt.executeUpdate()) {					
					System.out.format("\n - Fecha insertado: %s", f.toString());
				} else {
					System.out.format("\n - No se ha insertado la fecha: %s", f.toString());
				}
			}			
		} catch (Exception ex) {
			System.err.format("\n* Error al insertar datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}				
	}
	
	public void insertarDatos(Reserva... reservas) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("INSERT INTO CONCIERTO (CONCERTID, CONCERTNAME, FECHA,ATTENDEES) VALUES (?, ?, ?,?);")) {
			//Se define la plantilla de la sentencia SQL
			//String sql = "INSERT INTO CLIENTE (NAME, EMAIL, PASSWORD) VALUES ('%s', '%s', '%s');";
			
			System.out.print("\n- Insertando conciertos...");
			
			//Se recorren los clientes y se insertan uno a uno
			for (Reserva r : reservas) {
				stmt.setString(1,r.getLocator());
				stmt.setString(2,r.getNombreConcierto());
				stmt.setDate(3,Date.valueOf(r.getFecha()));
				List<String> attendees = r.getAttendees();
				String atts ="";
				for (String s:attendees) {
					atts=atts+s+":";
				}
				stmt.setString(4,atts);
				//stmt.toString();
				if (1 == stmt.executeUpdate()) {					
					System.out.format("\n - Reserva insertado: %s", r.toString());
				} else {
					System.out.format("\n - No se ha insertado la reserva: %s", r.toString());
				}
			}			
		} catch (Exception ex) {
			System.err.format("\n* Error al insertar datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}				
	}
	
	public List<Concert> obtenerConciertos() {
		List<Concert> conciertos = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("SELECT * FROM CONCIERTO WHERE ID >= 0")) {
			//String sql = "SELECT * FROM CLIENTE WHERE ID >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery();			
			Concert concierto;
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				concierto = new Concert(rs.getString("IMAGE"), rs.getString("ID"), rs.getString("NAME"),rs.getInt("DURATION"),rs.getInt("TICKETS"),rs.getFloat("PRECIO"));
				
				
				//Se inserta cada nuevo cliente en la lista de clientes
				conciertos.add(concierto);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.format("\n\n- Se han recuperado %d conciertos...", conciertos.size());			
		} catch (Exception ex) {
			System.err.format("\n\n* Error al obtener datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
		
		return conciertos;
	}
	
	public List<Fecha> obtenerFechas() {
		List<Fecha> fechas = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("SELECT * FROM FECHA")) {
			//String sql = "SELECT * FROM CLIENTE WHERE ID >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery();			
			Fecha fecha;
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				fecha = new Fecha(rs.getInt("DAY"),rs.getInt("MONTH"),rs.getInt("YEAR"),rs.getString("CONCERTID"),rs.getInt("SEATSLEFT"));
				
				
				//Se inserta cada nuevo cliente en la lista de clientes
				fechas.add(fecha);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.format("\n\n- Se han recuperado %d fechas...", fechas.size());			
		} catch (Exception ex) {
			System.err.format("\n\n* Error al obtener datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
		
		return fechas;
	}
	
	public List<Reserva> obtenerReservas() {
		List<Reserva> reservas = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("SELECT * FROM FECHA")) {
			//String sql = "SELECT * FROM CLIENTE WHERE ID >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery();			
			Reserva reserva;
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				String att = rs.getString("ATTENDEES");
				String[] attdet= att.split(":");
		        ArrayList<String> nombre = new ArrayList<String>();
		        for (String s:attdet) {
		        	if (!s.equals("")) {
		        		nombre.add(s);
		        	}
		        }
				reserva = new Reserva(rs.getString("CONCERTID"),rs.getString("CONCERTNAME"),rs.getDate("FECHA").toLocalDate(),nombre);
				
				
				//Se inserta cada nuevo cliente en la lista de clientes
				reservas.add(reserva);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.format("\n\n- Se han recuperado %d reservas...", reservas.size());			
		} catch (Exception ex) {
			System.err.format("\n\n* Error al obtener datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
		
		return reservas;
	}

	/*public void actualizarPassword(Cliente cliente, String newPassword) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("UPDATE CLIENTE SET PASSWORD = ? WHERE ID = ?;")) {
			//Se ejecuta la sentencia de borrado de datos
			//String sql = "UPDATE CLIENTE SET PASSWORD = '%s' WHERE ID = %d;";
			;
			stmt.setString(1,newPassword);
			stmt.setLong(2,cliente.getId());
			stmt.execute();
			
			System.out.format("\n\n- Se han actualizado "+cliente.getId()+" clientes");
		} catch (Exception ex) {
			System.err.format("\n\n* Error actualizando datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
	}*/
	
	public void borrarDatos() {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("DELETE FROM CLIENTE;")) {
			//Se ejecuta la sentencia de borrado de datos
			//String sql = "DELETE FROM CLIENTE;";			
			int result = stmt.executeUpdate();
			
			System.out.format("\n\n- Se han borrado %d clientes", result);
		} catch (Exception ex) {
			System.err.format("\n\n* Error al borrar datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
	}	
}