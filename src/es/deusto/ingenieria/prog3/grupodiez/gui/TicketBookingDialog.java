package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.persistence.GestorBD;

public class TicketBookingDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Fecha fecha;
	private Concert concert;
	private JSpinner jSpinnerTickets = new JSpinner(); //spinner para el numero de tickets
	private JComboBox<String> jComboAttendees = new JComboBox<>(); //elegir el numero de asistentes
	private JLabel jLabelAmount = new JLabel(); //etiqueta
	private JButton jButtonConfirm = new JButton("Confirmar"); //boton de confirmar
	private JButton jButtonCancel = new JButton("Cancelar"); //boton de cancelar
	private GestorBD gestorBD;
	
	private int tickets = 1; //numero de asientos (tickets) por default 1
	
	private List<String> attendees = null; //lista de asistentes vacia por default
	
	public TicketBookingDialog(Fecha fecha) {
		setBackground(new Color(255, 233, 244));
		this.setFecha(fecha);
		HashMap<String,Concert> indice=AnadirFecha.readConcert();
		this.concert = indice.get(fecha.getCode());
		System.out.println(concert);
		JPanel jPanelConcert = new JPanel(); //panel en la ventana
		jPanelConcert.setBorder(new TitledBorder("Datos del concierto")); //borde para añadir nombre
		jPanelConcert.setLayout(new GridLayout(4, 1)); //gridlayout para 5 elementos uno debajo del otro
		jPanelConcert.setBackground(new Color(255, 233, 244));

		JLabel jLabelConcert = new JLabel(String.format(concert.getName())); //etiqueta con el nombre del concierto
		jLabelConcert.setIcon(new ImageIcon(String.format("resources/images/%s.png", concert.getName()))); //poner la imagen del concierto en la etiqueta segun el nombre del mismo
		
		jPanelConcert.add(jLabelConcert); //añade la etiqueta creada al panel
		jPanelConcert.add(new JLabel(String.format("Fecha: %s / %s / %s", fecha.getDia(), fecha.getMes(), fecha.getAno()))); //añade al panel label para la fecha
		jPanelConcert.add(new JLabel(String.format("Duración: %d m.", concert.getDuration()))); //etiqueta de duracion
		jPanelConcert.add(new JLabel(String.format("Precio: %.2f €", concert.getPrice()))); //etiqueta de precio

		jButtonCancel.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) { 
      		    
      		    setVisible(false);
      		    } 
      		} );
		
		jButtonConfirm.setBackground(new Color(255,233,244));
		jButtonConfirm.setForeground(Color.black);
        jButtonCancel.setBackground(new Color(255,233,244));
        jButtonCancel.setForeground(Color.black);
		
		JPanel jPanelAttendees = new JPanel(); //otro panel para datos personales
		jPanelAttendees.setBorder(new TitledBorder("Datos personales")); //borde nombre
		jPanelAttendees.setLayout(new GridLayout(3, 1));//3 elementos
		jPanelAttendees.setBackground(new Color(255, 233, 244));
		
		int remainingTickets = concert.getSeats()- fecha.getReserva().size();  //calcular los tickets restantes para que no puedan elegir mas tickets de los que quedan	
		jSpinnerTickets = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1)); //spinner para que puedan elegir el numero de tickets
		//formato --> (valor inicial = 1, valor minimo = 1, valor maximo = 4, incremento = 1)
	
		
				jSpinnerTickets.addChangeListener((e) -> { //listener para cada vez que se cambia el numero de tickets en jspinner
					int newTicketValue = Integer.valueOf(((SpinnerNumberModel) jSpinnerTickets.getModel()).getValue().toString()); //coge el valor nuevo del numero de tickets si lo cambian para compararlo
					
		
					if (newTicketValue != tickets) { //si se cambia el numero de tickets(spinner), se cambia el numero de personas tambien(combobox)
						if (newTicketValue > tickets) { //si es mayor
							for (int i=tickets+1; i<=newTicketValue; i++) { //se añaden mas opciones al combobox (desde el antiguo ticket hasta el nuevo)
								jComboAttendees.addItem(String.format("%d - ¿?", i)); //añade nuevos elementos				
							}
						} else {
							for (int i=tickets; i>newTicketValue; i--) { //si es menor
								jComboAttendees.removeItemAt(i); //se elimina
							}
						}
						
						tickets = jComboAttendees.getItemCount()-1; //se actualiza el numero de tickets (se resta 1 porque jcombobox tiene un extra al final)
						
						jLabelAmount.setText(String.format("Importe total (%d x %.2f €): %.2f €", //etiqueta para el importe total
														   tickets,
														   concert.getPrice(),
														   (concert.getPrice()*tickets)));
					}
				});

				jComboAttendees.addItem("- Indique los datos de la personas -");//insertar elementos en combo
				jComboAttendees.addItem("1 - ¿?"); //por default se inserta 1 persona
				jComboAttendees.setBackground(new Color(255, 233, 244));
			
				jComboAttendees.addActionListener((e) -> { //cuando el usuario selecciona un item del combo
					int position = ((JComboBox<?>) e.getSource()).getSelectedIndex(); //posicion del item seleccionado
					String passenger = ((JComboBox<?>) e.getSource()).getSelectedItem().toString(); //obtiene el nombre del pasajero
					
					
					if (passenger.contains("¿?")) { //si se ha seleccionado ¿?, necesita introducir datos
						JTextField firstName = new JTextField(); //jtextfield para introducir datos (texto editable)
						firstName.setColumns(30); //nombre
						JTextField lastName = new JTextField();
						lastName.setColumns(30);//apellidos
						
						JComponent[] inputs = new JComponent[] { //visualizar nombre y apellidos
							new JLabel("Nombre: "),
							firstName,
							new JLabel("Apellidos: "),
							lastName,
						};
						
						//Se muestra un cuadro de diálogo para introducir nombre y apellidos
						int result = JOptionPane.showConfirmDialog(this, inputs, 
											String.format("Datos de la persona - %s", passenger.substring(0, passenger.indexOf(" "))), 
											JOptionPane.OK_CANCEL_OPTION,
											JOptionPane.PLAIN_MESSAGE, new ImageIcon("resources/images/passenger.png"));
						//Si se pulsa confirmar y los campos no están vacíos se actualiza el combo de personas
						if (result == JOptionPane.OK_OPTION && 
								(!firstName.getText().trim().isEmpty() || !lastName.getText().trim().isEmpty())) {
							String name;
						
							if (firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty()) {
								name = String.format("%s%s", lastName.getText().trim(), firstName.getText()).trim();
							} else {
								name = String.format("%s, %s", lastName.getText().trim(), firstName.getText()).trim();
							}
							
							//El nombre no puede contener el carácter "#"
							if ((!name.contains("#") || (!name.contains("*")))) {						
								name = String.format("%d - %s", position, name.toUpperCase());
								jComboAttendees.removeItemAt(position);
								jComboAttendees.insertItemAt(name, position);
								jComboAttendees.setSelectedIndex(position);
							}
						}
					}
				});	
				
				//Se actualiza el label del importe total teniendo en cuenta el número de asientos
				jLabelAmount.setText(String.format("Importe total (%d x %.2f €): %.2f €",
						tickets,
						concert.getPrice(),
						(concert.getPrice()*tickets)));
				
				//paneles para nombrar los elementos
				JPanel jPanelTickets = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
				jPanelTickets.add(new JLabel(String.format("N.º billetes (1 - 4): ", remainingTickets)));
				jPanelTickets.add(jSpinnerTickets);
				
				JPanel jPanelNames = new JPanel(new FlowLayout(FlowLayout.LEFT));
				jPanelNames.add(new JLabel("Personas: "));
				jPanelNames.add(jComboAttendees);
				
				JPanel jPanelAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
				jPanelAmount.add(jLabelAmount);
				jPanelAttendees.setBackground(new Color(255, 233, 244));
				jPanelAttendees.add(jPanelTickets);
				jPanelAttendees.add(jPanelNames);
				jPanelAttendees.add(jPanelAmount);
				
				//Eventos de los botones
				jButtonCancel.addActionListener((e) -> setVisible(false));
				jButtonConfirm.addActionListener((e) -> {
					updatePassengers();
					setVisible(false);
				});
				
				jButtonCancel.addActionListener((e) -> setVisible(false));
				jButtonConfirm.addActionListener((e) -> {
					TicketBookingDialog tbd= this;
					Thread add=new Thread() {
						public void run(){
							updatePassengers();
							System.out.println(tbd.getAttendees());
							List<String> att = tbd.getAttendees();
							System.out.println(att);
							String atts ="";
							for (String s:att) {
								atts=atts+s+":";
								System.out.println(att.size());
							}
							System.out.println(atts);
							String loc = concert.getCode();
							String fec = tbd.fecha.getFecha().toString();
							String con = tbd.concert.getName();

							try {
								FileWriter fw=new FileWriter("resources\\data\\Reservas.csv",true);
								fw.append(loc+";"+con+";"+fec+";"+atts+";\n");
								fw.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					};
					add.run();
				});
				
				JPanel jPanelButtons = new JPanel();
				jPanelButtons.add(jButtonCancel);
				jPanelButtons.add(jButtonConfirm);
				
				JPanel jPanelCenter = new JPanel();
				jPanelCenter.setLayout(new GridLayout(2, 1));
				jPanelCenter.add(jPanelConcert);
				jPanelCenter.add(jPanelAttendees);
				
				//this.setLayout(new BorderLayout(10, 10));
				add(new JPanel(), BorderLayout.NORTH);
				add(new JPanel(), BorderLayout.EAST);
				add(new JPanel(), BorderLayout.WEST);
				add(jPanelCenter, BorderLayout.CENTER);
				add(jPanelButtons, BorderLayout.SOUTH);
				
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				setTitle(String.format("Reserva del concierto '%s'", concert.getCode()));		
				setIconImage(new ImageIcon("resources/images/tickets.png").getImage());		
				setSize(500, 350);
				setModalityType(ModalityType.APPLICATION_MODAL);
				setLocationRelativeTo(null);
				setVisible(true);
			}
			
			
			 //Se actualiza la lista de nombres de personas a partir del combo de personas.
			 
			private void updatePassengers() {
				String item;
				
				attendees = new ArrayList<>();
				
				for (int i = 1; i <= tickets; i++) {
					item = jComboAttendees.getItemAt(i);
					
					if (!item.contains("¿?")) {
						attendees.add(item.substring(item.indexOf("-")+2, item.length()));
					} else {
						//Si faltan los datos de alguna persona se vacía la lista de pasajeros
						attendees.clear();
						break;
					}
				}		
			}
			
			public List<String> getAttendees() {
				return attendees;
			}
			
			public static void main(String[] args) {
		        // Crear la ventana en el hilo de eventos de Swing para no bloquear
		    	// el hilo de ejecución principal
		    	SwingUtilities.invokeLater(() -> {
		    		// Crear una instancia de EjemploLayouts y hacerla visible
		    		TicketBookingDialog cal = new TicketBookingDialog(new Fecha(4,5,2026,"123456",92000));
		    		cal.setVisible(true);
		    		
		    		//AnadirConcierto add = new AnadirConcierto();
		    		//add.setVisible(true);
		    		
		        });
		    }


			public Fecha getFecha() {
				return fecha;
			}


			public void setFecha(Fecha fecha) {
				this.fecha = fecha;
			}
		

	}