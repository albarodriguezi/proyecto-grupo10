package es.deusto.ingenieria.prog3.grupodiez.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import es.deusto.ingenieria.prog3.grupodiez.db.GestorBD;
import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;

import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;
import es.deusto.ingenieria.prog3.grupodiez.gui.ConcertsList;




public class Main{
	
	public static void main(String[] args) {
		//se crean 10 conciertos 
		
		/*Concert AdeleLive = new Concert(Concert.Logo.ADELELIVE, "123456", "Adele Live", 3, 92567, 150);
		Concert BelieveTour = new Concert(Concert.Logo.BELIEVETOUR, "456789", "Believe Tour", 3, 92567, 150);
		Concert BornToDie = new Concert(Concert.Logo.BORNTODIE, "789123", "Born To Die", 3, 92567, 150);
		Concert ErasTour = new Concert(Concert.Logo.ERASTOUR, "789456", "Eras Tour", 3, 92567, 150);
		Concert FutureNostalgia = new Concert(Concert.Logo.FUTURENOSTALGIA, "123123", "Future Nostalgia", 3, 92567, 150);
		Concert GutsWorldTour = new Concert(Concert.Logo.GUTSWORLTOUR, "456456", "Guts World Tour", 3, 92567, 150);
		Concert LoveOnTour = new Concert(Concert.Logo.LOVEONTOUR, "789789", "Love on Tour", 3, 92567, 150);
		Concert MusicOfTheSphere = new Concert(Concert.Logo.MUSICOFTHESPHERE, "147369", "Music Of Thw Sphere", 3, 92567, 150);
		Concert OnTheRoadAgain = new Concert(Concert.Logo.ONTHEROADAGAIN, "258147", "On the Road Again", 3, 92567, 150);
		Concert TheMathematicsTour = new Concert(Concert.Logo.THEMATHEMATICSTOUR, "369258", "The Mathematics Tour", 3, 92567, 150);
		Concert OnTheRoadAgain = new Concert(Concert.Logo.ONTHEROADAGAIN, "258147", "on the Road Again", 3, 92567, 150);
		Concert TheMathematicsTour = new Concert(Concert.Logo.THEMATHEMATICSTOUR, "369258", "the Mathematics Tour", 3, 92567, 150);
		*/
	
		//se crean dos o tres fecha por 
		
		/*Fecha fechau1 = new Fecha(10,10,2024);
		Fecha fecha2 = new Fecha(8,11,2024);
		Fecha fecha3 = new Fecha(23,12,2024);
		Fecha fecha4 = new Fecha(18,1,2024);
		Fecha fecha5 = new Fecha(20,2,2024);
		Fecha fecha6 = new Fecha(30,3,2024);
		Fecha fecha7 = new Fecha(14,4,2024);
		Fecha fecha9 = new Fecha(19,5,2024);
		Fecha fecha10 = new Fecha(15,6,2024);
		Fecha fecha11= new Fecha(12,7,2024);
		Fecha fecha12= new Fecha(10,9,2024);
		Fecha fecha13= new Fecha(11,1,2024);
		Fecha fecha14= new Fecha(20,2,2024);
		Fecha fecha15= new Fecha(22,3,2024);
		Fecha fecha16 = new Fecha(15,4,2024);
		Fecha fecha26 = new Fecha(9,5,2024);
		Fecha fecha17 = new Fecha(7,6,2024);
		Fecha fecha18 = new Fecha(23,7,2024);
		Fecha fecha19 = new Fecha(25,8,2024);
		Fecha fecha20 = new Fecha(30,9,2024);
		Fecha fecha21  = new Fecha(6,10,2024);
		Fecha fecha22 = new Fecha(8,11,2024);
		Fecha fecha23 = new Fecha(19,12,2024);
		Fecha fecha24 = new Fecha(24,1,2024);
		Fecha fecha25 = new Fecha(14,2,2024);*/

		List<Concert> concerts = new ArrayList<>();
		List<Fecha> fechas = new ArrayList<>();
		GestorBD gbd = new GestorBD();
		gbd.crearBBDD();
		//gbd.borrarBBDD();
		/*concerts.add(AdeleLive);
		concerts.add(BelieveTour);
		concerts.add(BornToDie);
		concerts.add(ErasTour);
		concerts.add(FutureNostalgia);
		concerts.add(GutsWorldTour);
		concerts.add(LoveOnTour);
		concerts.add(MusicOfTheSphere);
		concerts.add(OnTheRoadAgain);
		concerts.add(TheMathematicsTour);*/
		
		SwingUtilities.invokeLater(() -> new ConcertsList(concerts,gbd));
		
		//gbd.borrarBBDD();
	}	
}