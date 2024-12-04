package es.deusto.ingenieria.prog3.grupodiez.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;
import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.main.MainDisponibilidadTicket;
import es.deusto.ingenieria.prog3.grupodiez.persistence.GestorBD;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert.Logo;


public class ConcertsListRenderer extends JFrame {
    
	private static final long serialVersionUID = 1L;

    private List<Concert> concerts;
    private JTable tablaConcert;
    private DefaultTableModel modeloDatosConcerts;
    private JTextField txtFiltro;
    private GestorBD gestorBD;
    
    
    
    public ConcertsListRenderer(List<Concert> concerts,GestorBD gbd) {
        this.concerts = concerts;
        this.gestorBD = gbd;
        initTables();
        loadConcert();
        initGUI();
        setLayout(new BorderLayout());
        

    }

    private void initGUI() {
        JScrollPane scrollPaneConcerts = new JScrollPane(this.tablaConcert);
        scrollPaneConcerts.setBorder(new TitledBorder("Concerts"));
        this.tablaConcert.setFillsViewportHeight(true);

        this.txtFiltro = new JTextField(20);
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterConcerts(txtFiltro.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterConcerts(txtFiltro.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterConcerts(txtFiltro.getText());
            }
        };
        this.txtFiltro.getDocument().addDocumentListener(documentListener);

        JPanel panelFiltro = new JPanel();
        panelFiltro.add(new JLabel("Filtrar por nombre: "));
        panelFiltro.add(txtFiltro);

        JPanel panelConcert = new JPanel();
        panelConcert.setLayout(new BorderLayout());
        panelConcert.add(BorderLayout.CENTER, scrollPaneConcerts);
        panelConcert.add(BorderLayout.SOUTH, new JLabel("Todas las imagenes pertenecen a Ticketmaster"));
        
        this.getContentPane().add(panelConcert);
        this.setTitle("Concerts");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(2000, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    KeyListener refresh = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode() == KeyEvent.VK_G && e.isControlDown()) {
				Thread ref=new Thread() {
					public void run(){
						loadConcert();
						
					}
				};
				ref.run();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	KeyListener admin = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			// TODO Auto-generated method stub
			Thread adm=new Thread() {
				public void run(){
			if(e.getKeyCode() == KeyEvent.VK_A && e.isControlDown()&&e.isAltDown()) {
				System.out.println("b");
				AdminChoice cal = new AdminChoice(gestorBD);
	    		cal.setVisible(true);
			}
				}
			};
			adm.run();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	KeyListener reserve = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			// TODO Auto-generated method stub
			Thread reser=new Thread() {
				public void run(){
			if(e.getKeyCode() == KeyEvent.VK_T && e.isControlDown()) {
				VentanaReservas res = new VentanaReservas(gestorBD);
	    		res.setVisible(true);
			}
				}
			};
			reser.run();
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
    // Método para redimensionar las imágenes

    private ImageIcon redimensionarImagen (ImageIcon icono, int ancho, int alto) {

        Image imagen = icono.getImage(); // Obtener la imagen del ImageIcon

        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH); // Redimensionar la imagen

        return new ImageIcon(imagenRedimensionada); // Devolver la imagen redimensionada como ImageIcon

    }

    private void initTables() {
    	try {
			new FileWriter("resources\\data\\Reservas.csv",false).close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Vector<String> cabeceraConcert = new Vector<>(Arrays.asList("LOGO","CODIGO", "NOMBRE", "DURACION", "TICKETS", "PRECIO"));
        this.modeloDatosConcerts = new DefaultTableModel(new Vector<>(), cabeceraConcert){
        	
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        
        this.tablaConcert = new JTable(this.modeloDatosConcerts);

		//Se define un CellRenderer para las celdas de las dos tabla usando una expresión lambda
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());
			
			//Si el valor es de tipo Logo: se renderiza con la imagen centrada
			
				
				
			

			
			    
			if (column == 0) {
				result.setIcon(new ImageIcon(value.toString()));
				
			//Si el valor es numérico se renderiza centrado
			} else if (value instanceof Number) {
				result.setHorizontalAlignment(JLabel.CENTER);
			} else {
				//Si el valor es texto pero representa un número se renderiza centrado
				try {
					Integer.parseInt(value.toString());
					result.setHorizontalAlignment(JLabel.CENTER);				
				} catch(Exception ex) {
					result.setText(value.toString());
				}		
			}
			
			//La filas pares e impares se renderizan de colores diferentes de la tabla		
			if (table.equals(tablaConcert)) {
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
		DefaultCellEditor editorialEditor = new DefaultCellEditor(jComboEditorial);
		
		this.tablaConcert.addKeyListener(refresh);
		this.tablaConcert.addKeyListener(admin);
		this.tablaConcert.addKeyListener(reserve);
		this.tablaConcert.setRowHeight(70);//altira de las fila
		this.tablaConcert.getTableHeader().setReorderingAllowed(false);		//Se deshabilita la reordenación de columnas
		this.tablaConcert.getTableHeader().setResizingAllowed(false);//Se deshabilita el redimensionado de las columna
		
		this.tablaConcert.setAutoCreateRowSorter(true);		//Se definen criterios de ordenación por defecto para cada columna
		//Se establecen los renderers al la cabecera y el contenido	
		this.tablaConcert.setDefaultRenderer(Object.class, cellRenderer);
		//Se define la anchura de la columna Título
		this.tablaConcert.getColumnModel().getColumn(2).setPreferredWidth(500);
		
//-----------------------------------------------------------------------------como al seleccionar una fila se va a la pagina de fechas-----------------------------
		this.tablaConcert.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tablaConcert.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detecta doble clic
                    int selectedRow = tablaConcert.getSelectedRow();
                    if (selectedRow != -1) {
                        //int idConcierto = (int) tablaConcert.getValueAt(selectedRow, 1); // Obtiene el ID de la fila seleccionada
                        MainDisponibilidadTicket maindisponibilidad = new MainDisponibilidadTicket((new Concert((tablaConcert.getValueAt(selectedRow, 1)).toString())),gestorBD);
                        maindisponibilidad.setVisible(true);
                    }
                }
            }
        });
    }

   
    
    private void loadConcert() {
		//Se borran los datos del modelo de datos
    	ArrayList<Concert> conciertos = new ArrayList<Concert>();
    	conciertos = new ArrayList<>(gestorBD.obtenerConciertos());
    	/*try {
			Scanner sc = new Scanner(new File("resources\\data\\Concerts.csv"));
			while(sc.hasNextLine()){
		        String linea=sc.nextLine();
		        String[] campos=linea.split(";");
		        String logo = campos[0];
		        String code = campos[1];
		        String name = campos[2];
		        Integer duration = Integer.parseInt(campos[3]);
		        Float price = Float.parseFloat(campos[5]);
		        conciertos.add(new Concert(logo,code,name,duration,92000,price));
		        
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		this.modeloDatosConcerts.setRowCount(0);
		//Se añaden los comics uno a uno al modelo de datos
		conciertos.forEach(c -> this.modeloDatosConcerts.addRow(
				new Object[] {c.getImagen(), c.getCode(), c.getName(), c.getDuration(), c.getSeats(), c.getPrice()} )
		);
    }

    //hacemos que se puedan filtrar(creamos el buscador)
    private void filterConcerts(String text) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(this.modeloDatosConcerts);
        this.tablaConcert.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
    }
    
    
} 

