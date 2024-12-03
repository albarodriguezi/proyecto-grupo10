package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

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
import es.deusto.ingenieria.prog3.grupodiez.persistence.GestorBD;

public class DisponibilidadticketRenderer implements TableCellRenderer{
	private GestorBD gestorBD;
	@Override
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = new JLabel();
		label.setBackground(table.getBackground());
		label.setHorizontalAlignment(JLabel.CENTER);
		
		/*if (value.getClass().equals(Concert.class)) {
			label.setIcon(new ImageIcon(String.format("resources/images/%s.png", ((Concert) value).getImagen())));
		}*/
		
		if (row % 2 == 0) {
			label.setBackground(new Color(255, 233, 244));
		} else {
			label.setBackground(new Color(248, 190, 255));
		}

		
		if (column == 1 || column == 4) {
			label.setText(value.toString());
			label.setFont(new Font("DIN",Font.BOLD,12));
		}
		
		if (column == 2) {
			label.setText(String.format("%s m.", value.toString()));
			label.setHorizontalAlignment(JLabel.RIGHT);
			label.setFont(new Font("DIN",Font.BOLD,12));
		}
		
		if (column == 3) {
			label.setText(String.format("%s min", value));
			label.setHorizontalAlignment(JLabel.RIGHT);
			
		}
		
		if (column == 4) {
			label.setText(String.format("%.2f €", value));
			label.setHorizontalAlignment(JLabel.RIGHT);
			
		}
		if (column == 0) {
			label.setText(value.toString());
			label.setHorizontalAlignment(JLabel.CENTER);
		}
		if (column == 5) {
			
			//double disponibilidad = (Double)value;
			//JProgressBar pb = new JProgressBar(0,100);
			//pb.setValue((int) Math.round(disponibilidad));
			//pb.setStringPainted(true);
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
	        		    TicketBookingDialog tbd=new TicketBookingDialog((Fecha) value);
	        		    tbd.setVisible(true);
	        		    } 
	        		} );
			/*if (isSelected) {
				System.out.println(value);
				/*TicketBookingDialog tbd=new TicketBookingDialog((Fecha) value);
    		    tbd.setVisible(true);
				reserva.doClick();
			}
			*/
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