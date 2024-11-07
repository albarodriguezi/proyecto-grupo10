package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;

public class DisponibilidadticketRenderer implements TableCellRenderer{
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = new JLabel();
		label.setBackground(table.getBackground());
		label.setHorizontalAlignment(JLabel.CENTER);
		
		/*if (value.getClass().equals(Concert.class)) {
			label.setIcon(new ImageIcon(String.format("resources/images/%s.png", ((Concert) value).getImagen())));
		}*/
		
		if (column == 1 || column == 4) {
			label.setText(value.toString());
		}
		
		if (column == 2) {
			label.setText(String.format("%s m.", value.toString()));
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
			JButton reserva = new JButton((String)value);
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