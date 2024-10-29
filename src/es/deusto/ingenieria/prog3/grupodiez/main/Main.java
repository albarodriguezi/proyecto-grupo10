package es.deusto.ingenieria.prog3.grupodiez.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.prog3.grupodiez.domain.Concert;

import es.deusto.ingenieria.prog3.grupodiez.domain.Fecha;

public class Main{
	
	public static void main(String[] args) {
		//se crean 10 conciertos 
		
		Concert AdeleLive = new Concert("123456", Concert.Nombre.ADELELIVE, 3, 92567, 150);
		Concert BelieveTour = new Concert("456789", Concert.Nombre.BELIEVETOUR, 3, 92567, 150);
		Concert BornToDie = new Concert("789123", Concert.Nombre.BORNTODIE, 3, 92567, 150);
		Concert ErasTour = new Concert("789456", Concert.Nombre.ERASTOUR, 3, 92567, 150);
		Concert FutureNostalgia = new Concert("123123", Concert.Nombre.FUTURENOSTALGIA, 3, 92567, 150);
		Concert GutSWorldTour = new Concert("456456", Concert.Nombre.GUTSWORLTOUR, 3, 92567, 150);
		Concert LoveOnTour = new Concert("789789", Concert.Nombre.LOVEONTOUR, 3, 92567, 150);
		Concert MusicOfTheSphere = new Concert("147369", Concert.Nombre.MUSICOFTHESPHERE, 3, 92567, 150);
		Concert OnTheRoadAgain = new Concert("258147", Concert.Nombre.ONTHEROADAGAIN, 3, 92567, 150);
		Concert TheMathematicsTour = new Concert("369258", Concert.Nombre.THEMATHEMATICSTOUR, 3, 92567, 150);
	
		//se crean dos o tres fecha por 
		
		Fecha fechau1 = new Fecha(10,10,2024);
		Fecha fecha2 = new Fecha(10,10,2024);
		Fecha fecha3 = new Fecha(10,10,2024);
		Fecha fecha4 = new Fecha(10,10,2024);
		Fecha fecha5 = new Fecha(10,10,2024);
		Fecha fecha6 = new Fecha(10,10,2024);
		Fecha fecha7 = new Fecha(10,10,2024);
		Fecha fecha9 = new Fecha(10,10,2024);
		Fecha fecha10 = new Fecha(10,10,2024);
		Fecha fecha11= new Fecha(10,10,2024);
		Fecha fecha12= new Fecha(10,10,2024);
		Fecha fecha13= new Fecha(10,10,2024);
		Fecha fecha14= new Fecha(10,10,2024);
		Fecha fecha15= new Fecha(10,10,2024);
		Fecha fecha16 = new Fecha(10,10,2024);
		Fecha fecha26 = new Fecha(10,10,2024);
		Fecha fecha17 = new Fecha(10,10,2024);
		Fecha fecha18 = new Fecha(10,10,2024);
		Fecha fecha19 = new Fecha(10,10,2024);
		Fecha fecha20 = new Fecha(10,10,2024);
		Fecha fecha21  = new Fecha(10,10,2024);
		Fecha fecha22 = new Fecha(10,10,2024);
		Fecha fecha23 = new Fecha(10,10,2024);
		Fecha fecha24 = new Fecha(10,10,2024);
		Fecha fecha25 = new Fecha(10,10,2024);

		List<Concert> concerts = new ArrayList<>();
		concerts.add(AdeleLive);
		concerts.add(BelieveTour);
		concerts.add(BornToDie);
		concerts.add(ErasTour);
		concerts.add(FutureNostalgia);
		concerts.add(GutSWorldTour);
		concerts.add(LoveOnTour);
		concerts.add(MusicOfTheSphere);
		concerts.add(OnTheRoadAgain);
		concerts.add(TheMathematicsTour);
	}
}