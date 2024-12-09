package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import es.deusto.ingenieria.prog3.grupodiez.main.MainDisponibilidadTicket;

public class ProgressBar extends JFrame {

    private static final long serialVersionUID = 1L;
    
    // Valor máximo a contar
    // Botones de Iniciar y Parar
    // Progress Bar
    private JProgressBar progressBar = new JProgressBar(0, 100);
    
    // Clase que implementa el hilo contador
 
    
    public ProgressBar(MainDisponibilidadTicket maindisponibilidad) {      
    	setAlwaysOnTop(true);
    	// Configuración del estado inicial de los botones
    	Contador contador = new Contador(maindisponibilidad);
    	// Visualización del % en la Progress Bar
    	progressBar.setStringPainted(true);
    	progressBar.setBackground(new Color(255, 233, 244));;
     
        this.setLayout(new BorderLayout());

        this.add(progressBar, BorderLayout.CENTER);
        progressBar.setForeground(new Color(248, 190, 255));
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 100);
        this.setTitle("Cargando..");
        this.setLocationRelativeTo(null);
        setVisible(true);
        contador.start();
    }
    
    private class Contador extends Thread {
    	private MainDisponibilidadTicket maindisponibilidad;
    	public Contador(MainDisponibilidadTicket maindisponibilidad) {
			this.maindisponibilidad = maindisponibilidad;
		}

		public void run() {
			maindisponibilidad.setVisible(false);
    		int progreso;
    		setVisible(true);
    		for (int i=0; i <= 100; i++) {
    			// Comprobar si hay que parar el hilo
				if (Thread.currentThread().isInterrupted()) {
					
					break;}
				
    			try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			// Valor de progreso
    			progreso = (int) (i);
    			// Imprimir en consola   			
    			// Actualizar la Progress Bar
    			updateProgressBar(progreso);
    		}
    		maindisponibilidad.setVisible(true);
    		setVisible(false);

    		
    		
    	}
    }
    


    // Actualización de la Progress Bar usando SwingUtilities
    private void updateProgressBar(final int value) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(value));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProgressBar(null)); 
    }
}

