package server.types;

import server.exceptions.EmpruntException;
import server.exceptions.RetourException;

public interface Document {

	int numero();
	void reserver(Abonne ab) throws EmpruntException;
	void emprunter(Abonne ab) throws EmpruntException;
	void retour() throws RetourException;
	
}
