package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.domain.Reserva;
import es.deusto.ingenieria.prog3.grupodiez.main.Main;


public class TicketBookingRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;

	private Concert concert;
	private Main main;
	private Fecha fecha;

	public TicketBookingRenderer(Main main) {
		this.main = main;
	}
	
	private JButton prepare(JTable table, Object value, boolean isSelected, int row, int column) {
		concert = (Concert) value;

		// Create the "Reserve" button
		JButton button = new JButton("Reservar");
		button.setEnabled(true);
		button.setToolTipText(String.format("Reservar - %s", concert.getCode()));
		button.setBackground(table.getBackground());
		
		button.addActionListener((e) -> {
			// Create and show the booking dialog
			TicketBookingDialog dialog = new TicketBookingDialog(concert, fecha);

			// Get the attendees if the user provided the data
			if (dialog.getAttendees() != null && !dialog.getAttendees().isEmpty()) {
				// Process the booking with the main method
				String locator = main.bookConcertTickets(concert.getCode(), dialog.getAttendees());
				
				// Show a confirmation message
				JOptionPane.showMessageDialog(main, 
						String.format("El localizador de la reserva es: %s", locator),
						String.format("Confirmación de la reserva del concierto %s", concert.getCode()),
						JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon("resources/images/confirm.png"));
				
				// Update the concert list in the main window
				main.updateConcerts();
			} else {
				// If attendee data is missing, show an error message
				JOptionPane.showMessageDialog(main, 
						"No se ha realizado la reserva. Faltan los datos de alguna persona.",
						String.format("Reserva del concierto %s no confirmada", concert.getCode()),
						JOptionPane.ERROR_MESSAGE,
						new ImageIcon("resources/images/error.png"));
			}
			
			dialog.dispose();  // Close the dialog after processing
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