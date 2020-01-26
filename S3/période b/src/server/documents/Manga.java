package server.documents;

public class Manga extends AbstractDocument {

	public Manga(int numero, String titre) {
		super(numero, titre);
	}
	
	/**
	 * Constructeur utilisé pour pouvoir appeller la fonction serialize
	 * Ce constructeur ne doit être utilisé que dans ce but.
	 */
	public Manga() { super(); }

	@Override
	public Manga serialize(String line) {
		String[] parts = line.split(";");
		return new Manga(Integer.parseInt(parts[0].trim()), parts[1].trim());
	}

	@Override
	public String toString() {
		return "Manga [numero=" + numero + ", titre=" + titre + "]";
	}
}
