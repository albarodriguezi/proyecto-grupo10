package es.deusto.ingenieria.prog3.grupodiez.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.domain.Reserva;
import es.deusto.ingenieria.prog3.grupodiez.gui.AnadirFecha;

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
	                + " ID TEXT NOT NULL ,\n"
	                + " NAME TEXT NOT NULL,\n"
	                + " DURATION INT NOT NULL,\n"
	                + " TICKETS INT NOT NULL,\n"
	                + " PRICE FLOAT NOT NULL,\n"
	                + " PRIMARY KEY (ID),\n"
	                + " UNIQUE(ID));";
	
			String sql2 = "CREATE TABLE IF NOT EXISTS FECHA (\n"
					+ " FECHA DATE NOT NULL,\n"
	                + " CONCERTID TEXT NOT NULL,\n"
	                + " SEATSLEFT INT NOT NULL,\n"
	                + " FOREIGN KEY (CONCERTID) REFERENCES CONCIERTO(ID) ON DELETE CASCADE,\n"
	                + " PRIMARY KEY (FECHA,CONCERTID)"
					+ ");";
	
			String sql3 = "CREATE TABLE IF NOT EXISTS RESERVA (\n"
					+ " CONCERTID TEXT NOT NULL,\n"
					+ " CONCERTNAME TEXT NOT NULL,\n"
	                + " FECHA DATE NOT NULL,\n"
	                + " ATTENDEES TEXT NOT NULL,\n"
	                + " FOREIGN KEY (FECHA,CONCERTID) REFERENCES FECHA(FECHA,CONCERTID) ON DELETE CASCADE\n"
	                + " PRIMARY KEY (FECHA,ATTENDEES)"
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
			
			
			ArrayList<Concert> conciertos = new ArrayList<Concert>();
	    	try {
				Scanner sc = new Scanner(new File("resources\\data\\Concerts.csv"));
				while(sc.hasNextLine()){
			        String linea=sc.nextLine();
			        String[] campos=linea.split(";");
			        String logo = campos[0];
			        String code = campos[1];
			        String name = campos[2];
			        Integer duration = Integer.parseInt(campos[3]);
			        Float price = Float.parseFloat(campos[5]);
			        System.out.println(price);
			        conciertos.add(new Concert(logo,code,name,duration,92000,price));
			        
				}
				
				sc.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	insertarDatos(conciertos.toArray(new Concert[conciertos.size()]));
	    	
	    	ArrayList<Fecha> fechasc = new ArrayList<Fecha>();
	    	try {
	    		Scanner sc = new Scanner(new File("resources\\data\\Fecha.csv"));
	    		while(sc.hasNextLine()){
	    			//System.out.println(sc.nextLine());
	    			String linea=sc.nextLine();
	    			String[] campos=linea.split(";");
	    			Integer dia = Integer.parseInt(campos[0]);
	    			Integer mes = Integer.parseInt(campos[1]);
	    			Integer ano = Integer.parseInt(campos[2]);
	    			String code = campos[3];
	    			Integer seats = Integer.parseInt(campos[4]);
	    			//if (code.equals(concierto.getCode())) {
	    				System.out.println("a");
	    			fechasc.add(new Fecha(dia,mes,ano,code,seats));
	    			//}
	       
	       
	    		}

	    		sc.close();

	    	} catch (FileNotFoundException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	
	    	insertarDatos(fechasc.toArray(new Fecha[fechasc.size()]));
	    	
	    	ArrayList<Reserva> reservas=new ArrayList<Reserva>();
			try {
				Scanner sc = new Scanner(new File("resources\\data\\Reservas.csv"));
				while(sc.hasNextLine()){
			        String linea=sc.nextLine();
			        String[] campos=linea.split(";");
			        String code = campos[0];
			        String date = campos[2];
			        String[] datedet= date.split("-");
			        System.out.println(linea);
			        LocalDate ldate=LocalDate.of(Integer.parseInt(datedet[0]),Integer.parseInt(datedet[1]),Integer.parseInt(datedet[2]));
			        String strAtt = campos[3];
			        String[] attdet= strAtt.split(":");
			        ArrayList<String> nombre = new ArrayList<String>();
			        for (String s:attdet) {
			        	if (!s.equals("")) {
			        		nombre.add(s);
			        	}
			        }
			        
			       //List<String>
			        //Integer duration = Integer.parseInt(campos[3]);
			        Reserva r =new Reserva(code,AnadirFecha.readConcert().get(code),ldate,nombre);
			        reservas.add(r);
			        
			        
				}
				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	insertarDatos(reservas.toArray(new Reserva[conciertos.size()]));
			
	    }
		//}
	//}

	
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
		     PreparedStatement stmt = con.prepareStatement("INSERT INTO FECHA (FECHA,CONCERTID,SEATSLEFT) VALUES (?,?,?);")) {
			//Se define la plantilla de la sentencia SQL
			//String sql = "INSERT INTO CLIENTE (NAME, EMAIL, PASSWORD) VALUES ('%s', '%s', '%s');";
			
			System.out.print("\n- Insertando fechas...");
			
			//Se recorren los clientes y se insertan uno a uno
			for (Fecha f : fechas) {
				System.out.println(fechas.length);
				stmt.setDate(1,Date.valueOf(f.getFecha()));
				stmt.setString(2,f.getCode());
				stmt.setInt(3,f.getSeats());
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
		     PreparedStatement stmt = con.prepareStatement("INSERT INTO RESERVA (CONCERTID, CONCERTNAME, FECHA,ATTENDEES) VALUES (?, ?, ?,?);")) {
			//Se define la plantilla de la sentencia SQL
			//String sql = "INSERT INTO CLIENTE (NAME, EMAIL, PASSWORD) VALUES ('%s', '%s', '%s');";
			
			System.out.print("\n- Insertando reservas...");
			
			//Se recorren los clientes y se insertan uno a uno
			if (reservas.length!=0) {
			System.out.println(reservas.length);
			
			for (Reserva r : reservas) {
				if(r!=null) {
				stmt.setString(1,r.getLocator());
				stmt.setString(2,r.getNombreConcierto());
				stmt.setDate(3,Date.valueOf(r.getFecha()));
				List<String> attendees = r.getAttendees();
				String atts ="";
				for (String s:attendees) {
					if (s!= "") {
					atts=atts+s+":";
					}
				}
				System.out.println(r.getNombreConcierto());
				stmt.setString(4,atts);
				//stmt.toString();
				if (1 == stmt.executeUpdate()) {					
					System.out.format("\n - Reserva insertado: %s", r.toString());
				} else {
					System.out.format("\n - No se ha insertado la reserva: %s", r.toString());
				}
			}else {
				//System.out.println("a");
			}
			}
			}else {
				System.out.println("No reservations");
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
				concierto = new Concert(rs.getString("IMAGE"), rs.getString("ID"), rs.getString("NAME"),rs.getInt("DURATION"),rs.getInt("TICKETS"),rs.getFloat("PRICE"));
				
				
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
	
	public List<Fecha> obtenerFecha() {
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
				fecha = new Fecha(rs.getDate("FECHA").toLocalDate(), AnadirFecha.readConcert().get(rs.getString("CONCERTID")), rs.getInt("SEATSLEFT"));
				
				
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
				fecha = new Fecha(rs.getDate("FECHA").toLocalDate().getDayOfMonth(),rs.getDate("FECHA").toLocalDate().getMonthValue(),rs.getDate("FECHA").toLocalDate().getYear(),rs.getString("CONCERTID"),rs.getInt("SEATSLEFT"));
				
				
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
		     PreparedStatement stmt = con.prepareStatement("SELECT * FROM RESERVA")) {
			//String sql = "SELECT * FROM CLIENTE WHERE ID >= 0";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery();			
			Reserva reserva;
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				System.out.println(rs.toString());
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
	
	public void borrarReservas() {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("DELETE FROM RESERVA;")) {
			//Se ejecuta la sentencia de borrado de datos
			//String sql = "DELETE FROM CLIENTE;";			
			int result = stmt.executeUpdate();
			
			System.out.format("\n\n- Se han borrado %d reservas", result);
		} catch (Exception ex) {
			System.err.format("\n\n* Error al borrar datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
	}	
	
	public void borrarFechas() {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement stmt = con.prepareStatement("DELETE FROM FECHA;")) {
			//Se ejecuta la sentencia de borrado de datos
			//String sql = "DELETE FROM CLIENTE;";			
			int result = stmt.executeUpdate();
			
			System.out.format("\n\n- Se han borrado %d reservas", result);
		} catch (Exception ex) {
			System.err.format("\n\n* Error al borrar datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
	}	
	
	
/*	public static void main(String[] args) {
        // Crear la ventana en el hilo de eventos de Swing para no bloquear
    	// el hilo de ejecución principal
    	SwingUtilities.invokeLater(() -> {
    		// Crear una instancia de EjemploLayouts y hacerla visible
    		GestorBD bd = new GestorBD();
    		bd.crearBBDD();
    		bd.obtenerConciertos();
    		bd.obtenerFechas();
    		bd.obtenerReservas();
    		bd.borrarReservas();
    		bd.borrarBBDD();
    		
    		//AnadirConcierto add = new AnadirConcierto();
    		//add.setVisible(true);
    		
        });
    }*/
}

