package server.exceptions;

public class MembreNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MembreNotFoundException(int numero) {
		super("Aucun membre ne porte le numéro " + numero);
	}

}
