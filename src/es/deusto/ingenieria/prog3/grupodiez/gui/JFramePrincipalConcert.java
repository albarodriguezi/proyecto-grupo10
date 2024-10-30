package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;

public class JFramePrincipalConcert extends JFrame{
	private List<Concert> concerts;//definimos una lista de los conciertos que va a aparecer
	private JTable tablaConcerts;
	private DefaultTableModel  modeloDatosConcerts;
	private JTextField txtFiltro;
	
	public JFramePrincipalConcert(List<Concert> concerts) {
		this.concerts = concerts;
		this.initTableS();
		this.loadConcerts();
		
		JScrollPane scrollPaneConcerts = new JScrollPane(this.tablaConcerts);
		scrollPaneConcerts.setBorder(new TitleBorder("Concerts"));
		this.tablaConcerts.setFillsViewportHeight(true);
		
		this.txtFiltro = new JTextField(20);
		
		
	}
	
	
}