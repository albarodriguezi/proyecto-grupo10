package es.deusto.ingenieria.prog3.grupodiez.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.gui.DisponibilidadTicket;
import es.deusto.ingenieria.prog3.grupodiez.gui.DisponibilidadticketRenderer;
import es.deusto.ingenieria.prog3.grupodiez.gui.TicketBookingDialog;

public class MainDisponibilidadTicket extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Concert concerts;
	
	private JTable jTableFechas =  new JTable();//creamos una nueva JTable para las fehcas de los conciertos
	private JLabel jLabelInfo = new JLabel(" Selecciona un concierto");//definimos el titulo de la JTable 
	private JComboBox<String> jComboConcerts = new JComboBox<>();//no la necesitamos
	private JButton jBtnSearch = new JButton("Busqueda de fechas por concierto");//no lo necesitamos

	
	public MainDisponibilidadTicket(Concert c) {
		
		this.concerts = c;//definimos la lista de los conciertos
		
		//jComboConcerts.setPrototypeDisplayValue("Selecciona un concierto");
		List<Fecha> fechas = new ArrayList<>(); // Este es el lugar donde deberías añadir las fechas
        DefaultTableModel model = new DisponibilidadTicket(concerts).getModeloDatosFechas();
        //System.out.println(model.getRowCount());
        //System.out.println(model.getRowCount());
        jTableFechas.setModel(model);
        jTableFechas.setVisible(true);;

        // Asignar renderers a las columnas necesarias
        jTableFechas.getColumnModel().getColumn(0).setCellRenderer(new DisponibilidadticketRenderer());
        jTableFechas.getColumnModel().getColumn(4).setCellRenderer(new DisponibilidadticketRenderer());
        jTableFechas.getColumnModel().getColumn(5).setCellRenderer(new DisponibilidadticketRenderer());

        // Configurar la tabla
        jTableFechas.setRowHeight(40);
        jTableFechas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((DefaultTableCellRenderer) jTableFechas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        // Configurar el JPanel para la búsqueda
        JPanel pSearch = new JPanel();
        pSearch.setBorder(new TitledBorder("Busqueda de fechas"));
        pSearch.setLayout(new GridLayout(1,1));

        
        KeyListener refresh = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_G && e.isControlDown()) {
					System.out.println("a");
					DefaultTableModel newModel=new DisponibilidadTicket(concerts).getModeloDatosFechas();
					jTableFechas.setModel(newModel);
					setRenderer(jTableFechas);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		this.jTableFechas.addKeyListener(refresh);
		
		this.jTableFechas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detecta doble clic
                    int selectedRow = jTableFechas.getSelectedRow();
                    int selectedColumn = jTableFechas.getSelectedColumn();
                    if (selectedColumn == 5) {
                        //int idConcierto = (int) tablaConcert.getValueAt(selectedRow, 1); // Obtiene el ID de la fila seleccionada
                    	TicketBookingDialog tbd=new TicketBookingDialog((Fecha) jTableFechas.getModel().getValueAt(selectedRow, selectedColumn));
	        		    tbd.setVisible(true);
                    }
                }
            }
        });
        
        // Añadir componentes al JFrame
        add(pSearch, BorderLayout.NORTH);
        add(new JScrollPane(jTableFechas), BorderLayout.CENTER);
        add(jLabelInfo, BorderLayout.SOUTH);
        
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//definimos que se cierre al pesataña al salir de ella
        setTitle("Fechas conciertos");//definimos el titulo de la ventana
        setSize(1400,800);//definimos el tamaño de la ventana
        setLocationRelativeTo(null);//definimos que la ventana se encuentre en el centro de la pantalla del ordenador
        setVisible(true);//definimos que se pueda ver la pantalla
    }
	
	
	public void setRenderer(JTable t) {
		
	     t.setVisible(true);
	     t.getColumnModel().getColumn(0).setCellRenderer(new DisponibilidadticketRenderer());
	     t.getColumnModel().getColumn(4).setCellRenderer(new DisponibilidadticketRenderer());
	     t.getColumnModel().getColumn(5).setCellRenderer(new DisponibilidadticketRenderer());
	     t.setRowHeight(40);
	     t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     ((DefaultTableCellRenderer) t.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

	}
	
		/*jComboConcerts.addActionListener((e) -> {
			Object fromItem = ((JComboBox<?>) e.getSource()).getSelectedItem();
			
			if (fromItem != null && !fromItem.toString().isEmpty()) {
				final String concert = fromItem.toString().substring(0, fromItem.toString().indexOf("-"));
			}
			
			jLabelInfo.setText("selecciona concierto");
		});
		
		/*jBtnSearch.setEnabled(false);
		jBtnSearch.addActionListener((e) -> {
			Object item = jComboConcerts.getSelectedItem();
			String concert = item.toString().substring(0, item.toString().indexOf("-"));
			
			List<concerts> listconcerts = new ArrayList<>();
			
			new Thread(()->{
				listconcerts.addAll(search(concert));
				DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("%02d Itinerarios", listconcerts.size()));
				DefaultMutableTreeNode itineraryNode;
				
				float price = 0;
				int duration = 0;
				 for (int i=0, i<listconcerts.size(); i++) {
					 itineraryNode = new DefaultMutableTreeNode();
					 rootNode.add(itineraryNode);
					 
					 for (Concert c : listconcerts.get(i)) {
						 itineraryNode.add(new DefaultMutableTreeNode(c));
						 duration += c.getDuration();
						 price += c.getPrice();
					 }
					 
					 itineraryNode.setUserObject(String.format("%2d fecha, %2d min., %.2f €", 
							 listconcerts.get(i).size(), 
							 duration, 
							 price));
				 }
				 JScrollPane scrollPane = new JScrollPane(new JTree(rootNode));
				 scrollPane.sPreferredSize(new Dimension(600.300));
				 JOptionPane.showMessageDialog(this,
				 		scrollPane,
				 		String.format("itinerarios en %2", concert),
				 		JOptionPane.PLAIN_MESSAGE,
			}).start();
		});
		
		
		jTableFechas.setRowHeight(40);
		jTableFechas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		((DefaultTableCellRenderer) jTableFechas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		jLabelInfo.setHorizontalAlignment(JLabel.RIGHT);
		
		JPanel pSearch = new JPanel();
		pSearch.setBorder(new TitledBorder("Busqueda de fechas"));
		pSearch.setLayout(new GridLayout(1,1));
		
		add(pSearch, BorderLayout.NORTH);
		add(new JScrollPane(jTableFechas), BorderLayout.CENTER);
		add(jLabelInfo, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Fechas conciertos");
		setSize(1400,800);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	*/
		
		
		public static void main(String[] args) {
			//lista de las fechas de los conciertos
			
			/*Fecha fecha1 = new Fecha(1, 1, 2024,Concert.Logo.ADELELIVE, 92567 );
			Fecha fecha2 = new Fecha(2,2,2024,Concert.Logo.BELIEVETOUR, 92567 );
			Fecha fecha3 = new Fecha(3,3,2024, Concert.Logo.BORNTODIE,92567 );
			Fecha fecha4 = new Fecha(4,4,2024, Concert.Logo.ERASTOUR, 92567);
			Fecha fecha5 = new Fecha(5,5,2024, Concert.Logo.FUTURENOSTALGIA, 92567);
			Fecha fecha6 = new Fecha(6,6,2024,Concert.Logo.GUTSWORLTOUR, 92567);
			Fecha fecha7 = new Fecha(7,7,2024,Concert.Logo.LOVEONTOUR, 92567);
			Fecha fecha8 = new Fecha(8,8,2024,Concert.Logo.MUSICOFTHESPHERE, 92567);
			Fecha fecha9 = new Fecha(9,9,2024,Concert.Logo.ONTHEROADAGAIN, 92567);
			Fecha fecha10 = new Fecha(10,10,2024,Concert.Logo.THEMATHEMATICSTOUR, 92567);	
			
			List<Fecha> fechas = new ArrayList<>();//creamos la lista de los conciertos
			
			fechas.add(fecha1);//añadimos a la lista de la fechas cada una de las fehcas de los conciertos
			fechas.add(fecha2);
			fechas.add(fecha3);
			fechas.add(fecha4);
			fechas.add(fecha5);
			fechas.add(fecha6);
			fechas.add(fecha7);
			fechas.add(fecha8);
			fechas.add(fecha9);
			fechas.add(fecha10);*/
			
					
			SwingUtilities.invokeLater(() -> {
				new MainDisponibilidadTicket(new Concert("123456"));
				
		    });
		}
}
	
