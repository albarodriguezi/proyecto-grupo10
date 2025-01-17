package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import es.deusto.ingenieria.prog3.grupodiez.db.GestorBD;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;


/*
En esta clase definimos como el 
*/
public class AnadirConcierto extends JDialog {
			
			/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			//private JComboBox<String> jComboCategorias = new JComboBox<>();

			//definimos dos botones para confirmar la creacion del nuevo concirto, o cancelar y no añdirlo
			private JButton jButtonConfirm = new JButton("Confirmar");
			private JButton jButtonCancel = new JButton("Cancelar");

			//definimos el gestor de la base de datos
			private GestorBD gestorBD;
			
			public AnadirConcierto(GestorBD gbd) {
				
				gestorBD = gbd;
				
				setTitle("Concierto");
				setFont(new Font("DIN",Font.BOLD,12));

				//definimos el panel principal de la pantalla donde habra que rellenar toda la informacion para poder añadir un concierto
				JPanel jPanelAddConcert = new JPanel(new GridLayout(5, 1));
				
				getContentPane().setBackground(new Color(255,150,200));
				//setBorder(new TitledBorder("Datos del concierto"));
				setLayout(new BorderLayout());
				setSize(300, 200);

				
				//Creo y anado los paneles de cada campo a rellenar,con sus modificaciones de color,etc
				//panel en el cual se define la imagen del concierto
				JPanel iconPanel = new JPanel(new BorderLayout());
				iconPanel.setBorder(new TitledBorder("Icono"));
				JTextField icono = new JTextField();
				
				icono.setEditable(true);
				//iconPanel.add(iconText,BorderLayout.NORTH);
				
				//icono.setSize(200, 40);
				((TitledBorder)iconPanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,14));
				((TitledBorder)iconPanel.getBorder()).setTitleColor(new Color(255,150,200));
				icono.setBackground(new Color(255,233,244));
				iconPanel.add(icono,BorderLayout.CENTER);
				//añadimso el panel de la imagen del concierto al panel principal
				jPanelAddConcert.add(iconPanel);
				

				//panel en el que se define el codigo del concierto
				JPanel codePanel = new JPanel(new BorderLayout());
				codePanel.setBorder(new TitledBorder("Codigo"));
				JTextField codigo = new JTextField();
				codePanel.setOpaque(true);
				codePanel.setBackground(getBackground());
				((TitledBorder)codePanel.getBorder()).setTitleColor(new Color(255,150,200));
				((TitledBorder)codePanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,14));
				codigo.setEditable(true);
				codePanel.add(codigo,BorderLayout.CENTER);
				codigo.setBackground(new Color(255,233,244));
				//añadimos el panel del codigo al panel principal
				jPanelAddConcert.add(codePanel);
				

				//panel en el que se define el nombre del concierto
				JPanel namePanel = new JPanel(new BorderLayout());
				namePanel.setBorder(new TitledBorder("Nombre"));
				JTextField nombre = new JTextField();
				nombre.setEditable(true);
				((TitledBorder)namePanel.getBorder()).setTitleColor(new Color(255,150,200));
				((TitledBorder)namePanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,14));
				namePanel.add(nombre,BorderLayout.CENTER);
				nombre.setBackground(new Color(255,233,244));
				//añadimos el panel del nombre del concierto al panel principal
				jPanelAddConcert.add(namePanel);

				//panel en el que se define el precio del panel
				JPanel pricePanel = new JPanel(new BorderLayout());
				pricePanel.setBorder(new TitledBorder("Precio"));
				((TitledBorder)pricePanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,14));
				JTextField precio = new JTextField();
				((TitledBorder)pricePanel.getBorder()).setTitleColor(new Color(255,150,200));
				precio.setEditable(true);
				pricePanel.add(precio,BorderLayout.CENTER);
				precio.setBackground(new Color(255,233,244));
				//añadimos el panel del precio del concierto al panel principal
				jPanelAddConcert.add(pricePanel);
				
				//panel en el que se define la duracin del concierto
				JPanel durationPanel = new JPanel(new BorderLayout());
				durationPanel.setBorder(new TitledBorder("Duracion"));
				JTextField duracion = new JTextField();
				((TitledBorder)durationPanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,14));
				duracion.setEditable(true);
				((TitledBorder)durationPanel.getBorder()).setTitleColor(new Color(255,150,200));
				durationPanel.add(duracion,BorderLayout.CENTER);
				duracion.setBackground(new Color(255,233,244));
				duracion.setSize(WIDTH, 30);
				//añadimos el panel de la duracion del concierto al panel principal
				jPanelAddConcert.add(durationPanel);

				//a la ventana le añaimos el panel principal el el centro
				add(jPanelAddConcert,BorderLayout.CENTER);
				

				//definimos un panle en el que se van a encontrar los botones de "Confirmar" y "Cancelar"
				JPanel buttonPanel = new JPanel(new GridLayout(1,2));
				//añadimos los botones
				buttonPanel.add(jButtonConfirm);
				buttonPanel.add(jButtonCancel);

				//añadimos este panel a la ventana, en la parte inferior
				add(buttonPanel,BorderLayout.SOUTH);
				
				
				setSize(600,300);
				setLocationRelativeTo(null);

				//definimos el color de los botones
				jButtonConfirm.setBackground(new Color(255,233,244));
				jButtonConfirm.setForeground(Color.black);
		        jButtonCancel.setBackground(new Color(255,233,244));
		        jButtonCancel.setForeground(Color.black);
				//Pone invisible la ventana
				jButtonCancel.addActionListener(new ActionListener() { 
		        	  public void actionPerformed(ActionEvent e) { 
		        		    
		        		    setVisible(false);
		        		    } 
		        		} );
				//Lectura de archivo csv en un hilo
				jButtonConfirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) { 
						Thread add=new Thread() {
							public void run(){
								
								String icon = icono.getText();
								String code = codigo.getText();
								String name = nombre.getText();
								String duration = duracion.getText();
								String price = precio.getText();
								Concert c = new Concert("resources/images/"+icon,code,name,Integer.parseInt(duration),92000,Float.parseFloat(price));
								Concert[] cs = new Concert[1];
								cs[0] = c;
								gestorBD.insertarDatos(cs);
								JOptionPane.showMessageDialog(null, "Successfull import");
								/*
								try {
									FileWriter fw=new FileWriter("resources\\data\\Concerts.csv",true);
									fw.append("\nresources/images/"+icon+";"+code+";"+name+";"+duration+";92000;"+price+";");
									fw.close();
									JOptionPane.showMessageDialog(null, "Successfull import");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								*/
							}
						};

						add.run();
					}

				} );



			}

			/*public static void main(String[] args) {
		        // Crear la ventana en el hilo de eventos de Swing para no bloquear
		    	// el hilo de ejecución principal
		    	SwingUtilities.invokeLater(() -> {
		    		// Crear una instancia de EjemploLayouts y hacerla visible
		    		
		    		AnadirConcierto add = new AnadirConcierto(new GestorBD());
		    		add.setVisible(true);
		    		
		        });
		    }*/
			
		}


