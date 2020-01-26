package server;

import java.util.Arrays;

import server.documents.DVD;
import server.documents.Livre;
import server.documents.Manga;
import server.serveurs.Serveur;
import server.serveurs.services.ServiceEmprunt;
import server.serveurs.services.ServiceReservation;
import server.serveurs.services.ServiceRetour;

public class Appli {

	private static final Integer PORT_RESERVER = 2500;
	private static final Integer PORT_EMPRUNT = 2600;
	private static final Integer PORT_RETOUR = 2700;
	
	public static void main(String[] args) {

		Bibliotheque b = Bibliotheque.getInstance();

		b.setTypes(Arrays.asList(Manga.class, DVD.class, Livre.class));

		b.populate();
		
		System.out.println(b);
		
		System.out.println("Lancement des services...");
		
		new Thread(new Serveur(PORT_EMPRUNT, ServiceEmprunt.class)).start();
		new Thread(new Serveur(PORT_RESERVER, ServiceReservation.class)).start();
		new Thread(new Serveur(PORT_RETOUR, ServiceRetour.class)).start();
	}
}
