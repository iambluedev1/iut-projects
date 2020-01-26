package server.exceptions;

public class DocumentNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DocumentNotFoundException(int numero) {
		super("Aucun document ne porte le numéro " + numero);
	}

}
