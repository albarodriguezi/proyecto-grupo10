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

	private Reserva reserva;
	private Concert concert;
	private Main main;
	private Fecha fecha;
	
	public TicketBookingRenderer(Main main) {
		this.main = main;
	}
	
	private JButton prepare(JTable table, Object value, boolean isSelected, int row, int column) {
		concert = (Concert) value;
		
		JButton button = new JButton("Reservar");
		button.setEnabled(true);
		button.setToolTipText(String.format("Reservar - %s", concert.getCode()));				
		button.setBackground(table.getBackground());
		
		if (isSelected) {
			button.setBackground(table.getSelectionBackground());
		}
		
		button.addActionListener((e) -> {
			//Se crea el cuadro de diálogo para confirmar la reserva
			TicketBookingDialog dialog = new TicketBookingDialog(concert, fecha);
			//////
			//Si hay datos de personas
			if (dialog.getAttendees() != null && !dialog.getAttendees().isEmpty()) {
				//Se realiza la reserva a través del servicio de la alianza de aerolíneas
				String locator = main.getService(concert).book(concert.getCode(), dialog.getPassengers());
				
				JOptionPane.showMessageDialog(main, 
						String.format("El localizador de la reserva es: %s", locator),
						String.format("Confirmación de la reserva del vuelo %s", main.getCode()),
						JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon("resources/images/confirm.png"));
								
				//Se actualiza la lista de vuelos en la ventana principal
				main.updateConcerts();
			} else {
			//Si no hay datos de personas se muestra un mensaje de error
				JOptionPane.showMessageDialog(main, 
						"No se ha realizado la reserva. Faltan los datos de alguna persona.",
						String.format("Reserva del vuelo %s no confirmada", concert.getCode()),
						JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon("resources/images/confirm.png"));
			}
			
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