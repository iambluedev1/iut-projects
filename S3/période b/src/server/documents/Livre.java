package server.documents;

public class Livre extends AbstractDocument {
	
	public Livre(int numero, String titre) {
		super(numero, titre);
	}
	
	/**
	 * Constructeur utilisé pour pouvoir appeller la fonction serialize
	 * Ce constructeur ne doit être utilisé que dans ce but.
	 */
	public Livre(){ super(); }
	
	@Override
	public Livre serialize(String line) {
		String[] parts = line.split(";");
		return new Livre(Integer.parseInt(parts[0].trim()), parts[1].trim());
	}

	@Override
	public String toString() {
		return "Livre [numero=" + numero + ", titre=" + titre + "]";
	}
}
