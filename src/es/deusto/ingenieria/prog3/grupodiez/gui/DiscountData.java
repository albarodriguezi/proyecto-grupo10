package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.swing.border.TitledBorder;

import es.deusto.ingenieria.prog3.grupodiez.db.GestorBD;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;


public class DiscountData extends JDialog {
	private static final long serialVersionUID = 1L;
	private JComboBox<String> jComboConcerts = new JComboBox<>();
	private JSpinner jSpinnerTickets = new JSpinner(); //spinner para el numero de tickets
	private JComboBox<String> jComboAttendees = new JComboBox<>(); //elegir el numero de asistentes
	private JTextField jTextAmount = new JTextField(); //etiqueta
	private JButton jButtonConfirm = new JButton("Confirmar"); //boton de confirmar
	private JButton jButtonCancel = new JButton("Cancelar"); //boton de cancelar
	private GestorBD gestorBD;
	private int tickets = 1; //numero de asientos (tickets) por default 1
	private List<String> attendees = null;



public DiscountData(GestorBD gbd) {
	setBackground(new Color(255, 233, 244));
	this.gestorBD = gbd;
	HashMap<String,Concert> indice=AnadirFecha.readConcert();
	JPanel jPanelConcert = new JPanel(); //panel en la ventana
	jPanelConcert.setBorder(new TitledBorder("aa")); //borde para añadir nombre
	jPanelConcert.setLayout(new GridLayout(3, 1)); //gridlayout para 2 elementos uno debajo del otro
	jPanelConcert.setBackground(new Color(255, 233, 244));
	
	jComboConcerts.addItem("3 conciertos - 10% de descuento");
	jComboConcerts.addItem("4 conciertos - 15% de descuento");
	jComboConcerts.addItem("5 conciertos - 20% de descuento");
	
	JPanel jPanelDiscount = new JPanel(new FlowLayout(FlowLayout.LEFT));
	jPanelDiscount.add(new JLabel("Tipo de descuento: "));
	jPanelDiscount.add(jComboConcerts);
	
	JPanel jPanelAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
	jPanelAmount.add(new JLabel("Presupuesto: "));
	jTextAmount.setEditable(true);
	jTextAmount.setPreferredSize(new Dimension(200,20));
	jPanelAmount.add(jTextAmount);
	jPanelConcert.add(jPanelAmount);
	jPanelConcert.add(jPanelDiscount);
	
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
	 //calcular los tickets restantes para que no puedan elegir mas tickets de los que quedan	
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

			
			//paneles para nombrar los elementos
			JPanel jPanelTickets = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
			jPanelTickets.add(new JLabel("N.º billetes (1 - 4): "));
			jPanelTickets.add(jSpinnerTickets);
			
			JPanel jPanelNames = new JPanel(new FlowLayout(FlowLayout.LEFT));
			jPanelNames.add(new JLabel("Personas: "));
			jPanelNames.add(jComboAttendees);
			
			
			
			//JPanel jPanelAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
			//jPanelAmount.add(jLabelAmount);
			jPanelAttendees.setBackground(new Color(255, 233, 244));
			jPanelAttendees.add(jPanelTickets);
			jPanelAttendees.add(jPanelNames);
			
			//Eventos de los botones
			jButtonCancel.addActionListener((e) -> setVisible(false));
			jButtonConfirm.addActionListener((e) -> {
				setVisible(false);
			});
			
			jButtonCancel.addActionListener((e) -> setVisible(false));
			jButtonConfirm.addActionListener((e) -> {
				DiscountData tbd= this;
				Thread add=new Thread() {
					public void run(){
						List<List<Fecha>> combinaciones = generarRutinas(gbd.obtenerFecha(),Integer.parseInt(jTextAmount.getText()),jComboConcerts.getSelectedIndex()+3);
						updatePassengers();
						
						List<String> att = tbd.getAttendees();
						String atts ="";
						for (String s:att) {
							atts=atts+s+":";
							System.out.println(att.size());
						}
						DiscountFrame df = new DiscountFrame(combinaciones,attendees,gbd);
						
						df.setVisible(true);
					}
				};
				this.setVisible(false);
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
			setIconImage(new ImageIcon("resources/images/tickets.png").getImage());		
			setSize(500, 350);
			setModalityType(ModalityType.APPLICATION_MODAL);
			setLocationRelativeTo(null);
			setVisible(true);
}


public static List<List<Fecha>> generarRutinas(List<Fecha> fechas, int presupuesto, int cantidad) {
	//Creo la lista del resultado y la lista auxiliar
	List<List<Fecha>> total = new ArrayList<>();
	List<Fecha> auxiliar = new ArrayList<>();
	List<String> concierto = new ArrayList<>();
	List<List<String>> conciertotot = new ArrayList<>();
	//Llamo a la funcion auxiliar con una duracion auxiliar de 0 
	generarRutinasAux(fechas,total,auxiliar,presupuesto,cantidad,0,concierto,conciertotot);
	//Compruebo resultados
	System.out.println(total.size());
	System.out.println(total);
	return total;
}


//Creo una rutina auxiliar 
public static void generarRutinasAux(List<Fecha> fechas,List<List<Fecha>> result,List<Fecha> aux, int presupuesto, int cantidad ,float gastoaux,List<String> conciertosaux,List<List<String>> conciertos) {
	//CASO BASE 1 Comprueba que la duracion de la rutina auxiliar se menor que la que marca la conndicion
	if (presupuesto < gastoaux) {
		System.out.println(gastoaux);
		return;
	}
	//CASO BASE 2 Comprueba que la duracion total sea la debide)
	else if (aux.size()==cantidad) {
		List<Fecha> auxcopia = new ArrayList<>(aux);
		List<String> auxcopiacon = new ArrayList<>(conciertosaux);
		//Hago un ordenado para evitar los repetidos
		Collections.sort(auxcopia);
		Collections.sort(auxcopiacon);
		if (!result.contains(auxcopia)&&!conciertos.contains(auxcopiacon)) {
			result.add(auxcopia);
			conciertos.add(auxcopiacon);
		}
	}else {
	// CASO RECURSIVO
		//Para cada ejercicio
		for (Fecha f:fechas) {
			//Compruebo que el nivel sea correcto,que el grupo muscular sea uno de los adecuados y que no tenga repetidos
			if (!conciertosaux.contains(f.getConcert().getName()) &&
				!aux.contains(f)) { 
				aux.add(f);
				Float c;
				if(cantidad == 3) {
					c=(float) 0.1;
				}else if(cantidad == 4) {
					c=(float) 0.15;
				}else{
					c=(float) 0.2;
				}
				//sumo la duracion a las duraciones antiguas
				conciertosaux.add(f.getConcert().getName());
				generarRutinasAux(fechas,result,aux,presupuesto,cantidad,gastoaux+f.getConcert().getPrice()*(1-c),conciertosaux,conciertos);
				//retiro el ultimo ejercicio
				conciertosaux.remove(conciertosaux.size()-1);
				aux.remove(aux.size()-1);
			}
		}
}
}

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
}