package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;





	public class AdminChoice extends JFrame {

	    private static final long serialVersionUID = 1L;

		public AdminChoice() {
			
			
	        // Crear un panel principal con un GridLayout para organizar los otros paneles
	        // El panel tiene 2 filas y 2 columnas, con 10 píxeles de separación horizontal y vertical
	        JPanel mainPanel = new JPanel(new GridLayout(8,3,10,10));
	        JPanel subPanel = new JPanel(new BorderLayout());
	        
	        JButton addConcert =new JButton("Añadir Concierto");
	        JButton addDate =new JButton("Añadir Fecha");
	        
	        addConcert.setBackground(Color.black);
	        addConcert.setForeground(Color.white);
	        addDate.setBackground(Color.black);
	        addDate.setForeground(Color.white);
	        
	        
	        mainPanel.setBackground(new Color(228, 157, 237));
	        for (int i=1;i<8;i++) {
	        	JLabel fondo=new JLabel("");
	        	mainPanel.add(fondo);
	        }
	        
	        mainPanel.add(addConcert);
	        
	        for (int i=1;i<6;i++) {
	        	JLabel fondo=new JLabel("");
	        	mainPanel.add(fondo);
	        }
	        
	        mainPanel.add(addDate);
	    
	        for (int i=1;i<8;i++) {
	        	JLabel fondo=new JLabel("");
	        	mainPanel.add(fondo);
	        }
	        
	        AnadirConcierto addC = new AnadirConcierto();
	        addC.setVisible(false);
	        
	        AnadirFecha addD = new AnadirFecha();
	        addD.setVisible(false);
	        
	        
	      
	        addConcert.addActionListener(new ActionListener() { 
	        	  public void actionPerformed(ActionEvent e) { 
	        		      
	        		    addC.setVisible(true);
	        		    } 
	        		} );
	        
	        addDate.addActionListener(new ActionListener() { 
	        	  public void actionPerformed(ActionEvent e) { 
	        		      
	        		    addD.setVisible(true);
	        		    } 
	        		} );
	        	
	        
	        
	        add(mainPanel);
	        
	        // Definir el título
	        setTitle("AdminChoise");
	        // Definir la operación por defecto al cerrar la ventana (terminar la aplicación)
	        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        // Ajustar el tamaño de la ventana (ancho, alto en píxeles)
	        setSize(800, 600);
	        // Definir el tamaño mínimo de la ventana (ancho, alto en píxeles)
	        setMinimumSize(new Dimension(500, 300));
	        // Definir el tamaño máximo de la ventana (ancho, alto en píxeles)
	        // Ajustar este valor de acuerdo a la resolución de la pantalla
	        setMaximumSize(getToolkit().getScreenSize()); 
	        
	        // Centrar la ventana en la pantalla
	        setLocationRelativeTo(null);
	        
	        
	        
			
	        
	    }
		
		
		
		
		
		
		public static void main(String[] args) {
	        // Crear la ventana en el hilo de eventos de Swing para no bloquear
	    	// el hilo de ejecución principal
	    	SwingUtilities.invokeLater(() -> {
	    		// Crear una instancia de EjemploLayouts y hacerla visible
	    		AdminChoice cal = new AdminChoice();
	    		cal.setVisible(true);
	    		
	    		//AnadirConcierto add = new AnadirConcierto();
	    		//add.setVisible(true);
	    		
	        });
	    }
	
}