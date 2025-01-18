package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.grupodiez.db.GestorBD;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;

public class DisponibilidadticketRenderer implements TableCellRenderer{
	private GestorBD gestorBD;
	public DisponibilidadticketRenderer(GestorBD gbd) {
		this.gestorBD = gbd;
	}
	@Override
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = new JLabel();
		label.setBackground(table.getBackground());
		label.setHorizontalAlignment(JLabel.CENTER);
		
		// Colores alternos para las filas
		if (row % 2 == 0) {
			label.setBackground(new Color(255, 233, 244));
		} else {
			label.setBackground(new Color(248, 190, 255));
		}

		// Texto centrado
		if (column == 1 || column == 2 || column == 0) {
			label.setText(value.toString());
			label.setFont(new Font("DIN",Font.BOLD,12));
		}
		
		//Minutos de concierto alineadoa la derecha
		
		if (column == 3) {
			label.setText(String.format("%s min", value));
			label.setHorizontalAlignment(JLabel.RIGHT);
			
		}
		
		//Precio alineadoa la derecha
		if (column == 4) {
			label.setText(String.format("%.2f €", value));
			label.setHorizontalAlignment(JLabel.RIGHT);
			
		}

		if (column == 5) {
			
			// Boton con colores alternos
			JButton reserva = new JButton("+");
			reserva.setFont(new Font("DIN",Font.BOLD,24));
			if (row % 2 != 0) {
				reserva.setBackground(new Color(255, 233, 244));
			} else {
				reserva.setBackground(new Color(248, 190, 255));
			}

			reserva.addActionListener(new ActionListener() { 
	        	  public void actionPerformed(ActionEvent e) { 
	        		  	System.out.println(value);
	        		    TicketBookingDialog tbd=new TicketBookingDialog((Fecha) value,gestorBD);
	        		    tbd.setVisible(true);
	        		    } 
	        		} );

			reserva.setEnabled(true);
			
			return reserva;
		}
		
		if (isSelected) {
			label.setBackground(table.getSelectionBackground());
			label.setForeground(table.getSelectionForeground());
		}
		
		label.setOpaque(true);
		
		return label;
	}
}