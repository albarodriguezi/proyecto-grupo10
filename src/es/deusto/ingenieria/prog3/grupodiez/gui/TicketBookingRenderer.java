package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.main.MainDisponibilidadTicket;


public class TicketBookingRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;

	private Concert concert;
	/*private MainBooking mainbooking;
	
	public TicketBookingRenderer(MainBooking mainbooking) {
		this.mainbooking = mainbooking;
	}
	*/

	private JButton prepare(JTable table, Object value, boolean isSelected, int row, int column) {
		concert = (Concert) value;
		
		JButton button = new JButton("Reservar"); //boton de reservar
		button.setEnabled(true);
		button.setToolTipText(String.format("Reservar - %s", concert.getName()));				
		button.setBackground(table.getBackground());
		
		if (isSelected) {
			button.setBackground(table.getSelectionBackground());
		}
		
		button.addActionListener((e) -> {
			//Se crea el cuadro de diálogo para confirmar la reserva
			TicketBookingDialog dialog = new TicketBookingDialog(concert);
			
			/*
			//Si hay datos de personas
			if (dialog.getAttendees() != null && !dialog.getAttendees().isEmpty()) {
				//Se realiza la reserva
				String locator = "Reserva exitosa";
				
				JOptionPane.showMessageDialog(mainbooking, 
						String.format("El localizador de la reserva es: %s", locator),
						String.format("Confirmación de la reserva del concierto %s", concert.getCode()),
						JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon("resources/images/confirm.png"));
								
			
			} else {
			//Si no hay datos de personas se muestra un mensaje de error
				JOptionPane.showMessageDialog(mainbooking, 
						"No se ha realizado la reserva. Faltan los datos de alguna persona.",
						String.format("Reserva del concierto %s no confirmada", concert.getCode()),
						JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon("resources/images/confirm.png"));
			}
			*/
			dialog.dispose();
		});
		
		button.setOpaque(true);
		
		return button;
	}
	
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return prepare(table, value, isSelected, row, column);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		return prepare(table, value, isSelected, row, column);		
	}
	
	@Override
	public Object getCellEditorValue() {
		return concert;
	}
	
    @Override
    public boolean shouldSelectCell(EventObject event) {
        return true;
    }
}