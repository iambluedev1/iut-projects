package server.documents;

import server.exceptions.EmpruntException;
import server.types.Abonne;

public class DVD extends AbstractDocument {

	private Integer ageMinimumRequis;
	
	public DVD(int numero, String titre, Integer ageMinimumRequis) {
		super(numero, titre);
		this.ageMinimumRequis = ageMinimumRequis;
	}
	
	/**
	 * Constructeur utilisé pour pouvoir appeller la fonction serialize
	 * Ce constructeur ne doit être utilisé que dans ce but.
	 */
	public DVD() { super(); }

	@Override
	public DVD serialize(String line) {
		String[] parts = line.split(";");
		return new DVD(Integer.parseInt(parts[0].trim()), parts[1].trim(), Integer.parseInt(parts[2].trim()));
	}
	
	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMinimumRequis)
			throw new EmpruntException("Vous n'avez pas l'age requis pour emprunter ce dvd.");
		
		super.emprunter(ab);
	}
	
	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMinimumRequis)
			throw new EmpruntException("Vous n'avez pas l'age requis pour reserver ce dvd.");
		
		super.reserver(ab);
	}

	@Override
	public String toString() {
		return "DVD [ageMinimumRequis=" + ageMinimumRequis + ", numero=" + numero + ", titre=" + titre + "]";
	}
}
