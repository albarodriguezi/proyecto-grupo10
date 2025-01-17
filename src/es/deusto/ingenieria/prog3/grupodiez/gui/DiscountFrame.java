package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.grupodiez.db.GestorBD;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.domain.Reserva;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Logo;

public class DiscountFrame extends JFrame{

	private List<List<Fecha>> combinaciones;
    private JTable tablaCombinaciones;
    private DefaultTableModel modeloDatosCombinaciones;
    private List<String> attendees;
    private GestorBD gestorBD;
	private static final long serialVersionUID = 1L;
	
	public DiscountFrame(List<List<Fecha>> combinaciones,List<String> attendees,GestorBD gbd) {
        this.combinaciones = combinaciones;
        this.attendees = attendees;
        this.gestorBD = gbd;
        initTables();
        //loadCombinaciones();
        initGUI();
        setLayout(new BorderLayout());
        
        this.tablaCombinaciones.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Detecta doble clic
                    int selectedRow = tablaCombinaciones.getSelectedRow();
                    int selectedColumn = tablaCombinaciones.getSelectedColumn();
                    if (selectedColumn == 2) {
                    	JOptionPane.showMessageDialog(null, "Successfull import");
	        		  	@SuppressWarnings("unchecked")
						List<Fecha> comb = (List<Fecha>) tablaCombinaciones.getValueAt(selectedRow, 0);
	        		  	for (Fecha f:comb) {
	        		  		System.out.println(f);
	        		  		Reserva r = new Reserva(f.getConcert().getCode(),f.getConcert(),f.getFecha(),attendees);
	        		  		gestorBD.insertarDatos(r);
	        		  	}
                    }
                }
            }
        });
    }
	
	
    private void initGUI() {
        JScrollPane scrollPaneCombinaciones = new JScrollPane(this.tablaCombinaciones);
        scrollPaneCombinaciones.setBorder(new TitledBorder("Concerts"));
        this.tablaCombinaciones.setFillsViewportHeight(true);
        //scrollPaneCombinaciones.setSize(2000,800);
        JPanel panelConcert = new JPanel();
        //panelConcert.setSize(2000,800);
        panelConcert.add(scrollPaneCombinaciones);

        
	    
	    

        this.getContentPane().add(scrollPaneCombinaciones);
        this.setTitle("Options");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(2000, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    
    private void initTables() {
        Vector<String> cabeceraConcert = new Vector<>(Arrays.asList("CONCIERTOS","PRECIO","RESERVAR"));
        this.modeloDatosCombinaciones = new DefaultTableModel(new Vector<>(), cabeceraConcert){
        	
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
			   //if (column == 2) {
				//   return true;
			  // }else {
				   return false;
			  // }
               
            }
        };
        loadCombinaciones();
        this.tablaCombinaciones = new JTable(this.modeloDatosCombinaciones);
        System.out.println(tablaCombinaciones.getValueAt(1, 0));
		//Se define un CellRenderer para las celdas de las dos tabla usando una expresión lambda
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());
			if (column == 0){
				String concerts = "";
				@SuppressWarnings("unchecked")
				List<Fecha> comb =(List<Fecha>) value;
				for (Fecha f:comb) {
					concerts = concerts + f.getConcert().getName().toUpperCase() + ": "+ f.getFecha().toString()+ "   +   \n";
				}
				result.setText(concerts);
			}
			else if (column == 1) {
				result.setHorizontalAlignment(JLabel.CENTER);
			}else if (column == 2){
				//Si el valor es texto pero representa un número se renderiza centrado
				JButton reserva = new JButton("+");
				reserva.setFont(new Font("DIN",Font.BOLD,24));
				if (row % 2 != 0) {
					reserva.setBackground(new Color(255, 233, 244));
				} else {
					reserva.setBackground(new Color(248, 190, 255));
				}

				reserva.addActionListener(new ActionListener() { 
		        	  public void actionPerformed(ActionEvent e) { 
		        		  	JOptionPane.showMessageDialog(null, "Successfull import");
		        		  	@SuppressWarnings("unchecked")
							List<Fecha> comb = (List<Fecha>) tablaCombinaciones.getValueAt(row, 0);
		        		  	/*List<String> att = attendees;
							String atts ="";
							for (String s:att) {
								atts=atts+s+":";
								System.out.println(att.size());
							}*/
		        		  	for (Fecha f:comb) {
		        		  		System.out.println(f);
		        		  		Reserva r = new Reserva(f.getCode(),f.getConcert(),f.getFecha(),attendees);
		        		  		gestorBD.insertarDatos(r);
		        		  	}
		        		    } 
		        		} );
				reserva.setEnabled(true);
				return reserva;
			}
			
			//La filas pares e impares se renderizan de colores diferentes de la tabla		
			if (table.equals(tablaCombinaciones)) {
				if (row % 2 == 0) {
					result.setBackground(new Color(255, 233, 244));
				} else {
					result.setBackground(new Color(248, 190, 255));
				}
			} 
			
			//Si la celda está seleccionada se renderiza con el color de selección por defecto
			if (isSelected) {
				result.setBackground(table.getSelectionBackground());
				result.setForeground(table.getSelectionForeground());			
			}
			
			result.setOpaque(true);
			
			return result;
		};
		//Se crea un CellEditor a partir de un JComboBox()
		JComboBox<Logo> jComboEditorial = new JComboBox<>(Logo.values());		
		//DefaultCellEditor editorialEditor = new DefaultCellEditor(jComboEditorial);
		
		this.tablaCombinaciones.setFillsViewportHeight(true);
		this.tablaCombinaciones.setRowHeight(70);//altira de las fila
		this.tablaCombinaciones.getTableHeader().setReorderingAllowed(false);		//Se deshabilita la reordenación de columnas
		this.tablaCombinaciones.getTableHeader().setResizingAllowed(false);//Se deshabilita el redimensionado de las columna
		
		this.tablaCombinaciones.setAutoCreateRowSorter(true);		//Se definen criterios de ordenación por defecto para cada columna
		//Se establecen los renderers al la cabecera y el contenido	
		this.tablaCombinaciones.setDefaultRenderer(Object.class, cellRenderer);
		//Se define la anchura de la columna Título
		this.tablaCombinaciones.getColumnModel().getColumn(0).setPreferredWidth(1000);
		
//-----------------------------------------------------------------------------como al seleccionar una fila se va a la pagina de fechas-----------------------------
		this.tablaCombinaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void loadCombinaciones() {

		this.modeloDatosCombinaciones.setRowCount(0);
		//Se añaden los comics uno a uno al modelo de datos
		combinaciones.forEach(c -> {
		String concerts = "";
		for (Fecha f:c) {
			concerts = concerts + f.getConcert().getName().toUpperCase() + ": "+ f.getFecha().toString()+ "     ";
		}
		Float price = (float) 0;
		for (Fecha f:c) {
			price+=f.getConcert().getPrice();
		}
		switch (c.size())	{
		case 3:
			price= (float) (price*0.9);
		case 4:
			price= (float) (price*0.85);
		case 5:
			price= (float) (price*0.8);
		}
		System.out.println(c);
		System.out.println("A");
		this.modeloDatosCombinaciones.addRow(
		
		new Object[] {c, price, attendees} );}
		);
    }
    
		
	};


