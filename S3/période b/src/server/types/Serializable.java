package server.types;

public abstract class Serializable {

	/**
	 * Fonction qui a partir d'une ligne de texte va générer et retourner une instance de document
	 * @param line
	 * @return Serializable
	 */
	public abstract Serializable serialize(String line);
	
}
