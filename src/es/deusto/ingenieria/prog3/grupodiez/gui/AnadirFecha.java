package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Logo;
import es.deusto.ingenieria.prog3.grupodiez.persistence.GestorBD;

public class AnadirFecha extends JDialog {
			
			/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			private JButton jButtonConfirm = new JButton("Confirmar");
			private JButton jButtonCancel = new JButton("Cancelar");
			private GestorBD gestorBD;
			
			public AnadirFecha(GestorBD gbd) {
				
				gestorBD = gbd;
				
				setTitle("Conciertos");
				
				
				JPanel jPanelAddDate = new JPanel();
				jPanelAddDate.setBackground(new Color(230,215,220));
				//jPanelAddDate.setBorder(new TitledBorder("Datos de la fecha"));
				//((TitledBorder)jPanelAddDate.getBorder()).setTitleColor(new Color(255,150,200));
				jPanelAddDate.setLayout(new GridLayout(3,1));
				

				
				JPanel jPanelFecha = new JPanel(new GridLayout(1,3));
				
				JPanel dayPanel = new JPanel(new BorderLayout());
				dayPanel.setBorder(new TitledBorder("Dia"));
				JTextField dia = new JTextField();
				dia.setFont(new Font("Verdana",Font.BOLD,8));
				dia.setEditable(true);
				//dayPanel.add(dayText,BorderLayout.NORTH);
				((TitledBorder)dayPanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,12));
				((TitledBorder)dayPanel.getBorder()).setTitleColor(new Color(255,150,200));
				dayPanel.add(dia,BorderLayout.CENTER);
				dia.setSize(500, 40);
				dia.setBackground(new Color(255,233,244));
				dayPanel.setOpaque(true);
				jPanelFecha.add(dayPanel);
				
				
				JPanel monthPanel = new JPanel(new BorderLayout());
				monthPanel.setBorder(new TitledBorder("Mes"));
				JTextField mes = new JTextField();
				mes.setFont(new Font("Verdana",Font.BOLD,8));
				mes.setEditable(true);
				((TitledBorder)monthPanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,12));
				((TitledBorder)monthPanel.getBorder()).setTitleColor(new Color(255,150,200));
				//monthPanel.add(monthText,BorderLayout.NORTH);
				monthPanel.add(mes,BorderLayout.CENTER);
				mes.setSize(500, 40);
				mes.setBackground(new Color(255,233,244));
				jPanelFecha.add(monthPanel);
				
				
				JPanel yearPanel = new JPanel(new BorderLayout());
				yearPanel.setBorder(new TitledBorder("Año"));
				JTextField ano = new JTextField();
				ano.setFont(new Font("Verdana",Font.BOLD,8));
				ano.setEditable(true);
				((TitledBorder)yearPanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,12));
				((TitledBorder)yearPanel.getBorder()).setTitleColor(new Color(255,150,200));
				//yearPanel.add(yearText,BorderLayout.NORTH);
				yearPanel.add(ano,BorderLayout.CENTER);
				ano.setSize(500, 40);
				ano.setBackground(new Color(255,233,244));
				jPanelFecha.add(yearPanel);
				
				jPanelAddDate.add(jPanelFecha);
				
				JPanel codePanel = new JPanel(new BorderLayout());
				codePanel.setBorder(new TitledBorder("Codigo"));
				JTextField codigo = new JTextField();
				//codigo.setFont(new Font("Verdana",Font.BOLD,8));
				codigo.setEditable(true);
				//codePanel.add(codeText,BorderLayout.NORTH);
				codePanel.add(codigo,BorderLayout.CENTER);
				codigo.setBackground(new Color(255,233,244));
				((TitledBorder)codePanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,12));
				((TitledBorder)codePanel.getBorder()).setTitleColor(new Color(255,150,200));
				codePanel.setOpaque(true);
				jPanelAddDate.add(codePanel);
				

				JPanel seatPanel = new JPanel(new BorderLayout());
				seatPanel.setBorder(new TitledBorder("Asientos"));
				JTextField asiento = new JTextField();
				((TitledBorder)seatPanel.getBorder()).setTitleFont(new Font("DIN",Font.BOLD,12));
				//asiento.setFont(new Font("Verdana",Font.BOLD,8));
				((TitledBorder)seatPanel.getBorder()).setTitleColor(new Color(255,150,200));
				asiento.setEditable(true);
				//seatPanel.add(seatText,BorderLayout.NORTH);
				seatPanel.add(asiento,BorderLayout.CENTER);
				asiento.setBackground(new Color(255,233,244));
				jPanelAddDate.add(seatPanel);
				
				JPanel buttonPanel = new JPanel(new GridLayout(1,2));
				buttonPanel.add(jButtonConfirm);
				buttonPanel.add(jButtonCancel);
				add(buttonPanel,BorderLayout.SOUTH);
				add(jPanelAddDate,BorderLayout.CENTER);
				
				setSize(500,200);
				setLocationRelativeTo(null);
				
				jButtonConfirm.setBackground(new Color(255,233,244));
				jButtonConfirm.setForeground(Color.black);
		        jButtonCancel.setBackground(new Color(255,233,244));
		        jButtonCancel.setForeground(Color.black);
				
				jButtonCancel.addActionListener(new ActionListener() { 
		        	  public void actionPerformed(ActionEvent e) { 
		        		    
		        		    setVisible(false);
		        		    } 
		        		} );
				
				jButtonConfirm.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) { 
						Thread add=new Thread() {
							public void run(){
								String day = dia.getText();
								String code = codigo.getText();
								String month = mes.getText();
								String year = ano.getText();
								String seat = asiento.getText();
								if (readConcert().containsKey(code)) {
									try {
										FileWriter fw=new FileWriter("resources\\data\\Fecha.csv",true);
										fw.append("\n"+day+";"+month+";"+year+";"+code+";"+seat+";");
										fw.close();
										JOptionPane.showMessageDialog(null, "Succesfull import");
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}else {

									JOptionPane.showMessageDialog(null, "Code not found");

								}
							    }

						
						};
						add.run();
					};


				}
						);



			}
			
			public static HashMap<String,Concert> readConcert(){
				HashMap<String,Concert> conciertos = new HashMap<String,Concert>();
				Thread hash=new Thread() {
					public void run(){
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
						        conciertos.put(code,new Concert(logo,code,name,duration,92000,price));
						        
							}
							
							sc.close();
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
		    	hash.run();
		    	
		    	return conciertos;
			}
			
			public static void main(String[] args) {
		        // Crear la ventana en el hilo de eventos de Swing para no bloquear
		    	// el hilo de ejecución principal
		    	SwingUtilities.invokeLater(() -> {
		    		// Crear una instancia de EjemploLayouts y hacerla visible
		    		
		    		AnadirFecha add = new AnadirFecha(new GestorBD());
		    		add.setVisible(true);
		    		//System.out.println(AnadirFecha.readConcert());
		    		
		        });
		    }
			
		}