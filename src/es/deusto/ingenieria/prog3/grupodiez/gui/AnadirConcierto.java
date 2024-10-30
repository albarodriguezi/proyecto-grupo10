package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class AnadirConcierto extends JFrame {
			
			private JComboBox<String> jComboCategorias = new JComboBox<>();
			private JButton jButtonConfirm = new JButton("Confirmar");
			private JButton jButtonCancel = new JButton("Cancelar");
			
			
			public AnadirConcierto() {
				
				
				
				setTitle("Concierto");
				
				
				JPanel jPanelAddConcert = new JPanel();
				jPanelAddConcert.setBackground(new Color(255,150,200));
				jPanelAddConcert.setBorder(new TitledBorder("Datos del concierto"));
				jPanelAddConcert.setLayout(new GridLayout(5, 1));
				jPanelAddConcert.setSize(500, 500);
				
				JPanel iconPanel = new JPanel(new BorderLayout());
				JLabel iconText = new JLabel("Icon (source)");
				JTextField icono = new JTextField();
				icono.setEditable(true);
				iconPanel.add(iconText,BorderLayout.NORTH);
				iconPanel.add(icono,BorderLayout.CENTER);
				icono.setSize(500, 40);
				icono.setBackground(new Color(255,233,244));
				jPanelAddConcert.add(iconPanel);
				
				
				JPanel codePanel = new JPanel(new BorderLayout());
				JLabel codeText = new JLabel("Code");
				JTextField codigo = new JTextField();
				codigo.setEditable(true);
				codePanel.add(codeText,BorderLayout.NORTH);
				codePanel.add(codigo,BorderLayout.CENTER);
				codigo.setBackground(new Color(255,233,244));
				jPanelAddConcert.add(codePanel);
				
				
				JPanel namePanel = new JPanel(new BorderLayout());
				JLabel nameText = new JLabel("Nombre");
				JTextField nombre = new JTextField();
				nombre.setEditable(true);
				namePanel.add(nameText,BorderLayout.NORTH);
				namePanel.add(nombre,BorderLayout.CENTER);
				nombre.setBackground(new Color(255,233,244));
				jPanelAddConcert.add(namePanel);
				
				JPanel pricePanel = new JPanel(new BorderLayout());
				JLabel priceText = new JLabel("Precio");
				JTextField precio = new JTextField();
				precio.setEditable(true);
				pricePanel.add(priceText,BorderLayout.NORTH);
				pricePanel.add(precio,BorderLayout.CENTER);
				precio.setBackground(new Color(255,233,244));
				jPanelAddConcert.add(pricePanel);
				

				
				
				jPanelAddConcert.add(jButtonConfirm);
				add(jPanelAddConcert);
				
				setSize(500,400);
				
				
				
				
			}
			
			public static void main(String[] args) {
		        // Crear la ventana en el hilo de eventos de Swing para no bloquear
		    	// el hilo de ejecución principal
		    	SwingUtilities.invokeLater(() -> {
		    		// Crear una instancia de EjemploLayouts y hacerla visible
		    		
		    		AnadirConcierto add = new AnadirConcierto();
		    		add.setVisible(true);
		    		
		        });
		    }
			
		}


