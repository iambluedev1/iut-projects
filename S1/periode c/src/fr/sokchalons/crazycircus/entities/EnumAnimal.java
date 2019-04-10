package fr.sokchalons.crazycircus.entities;

/**
 * L'énumération Animal
 */
public enum EnumAnimal {
	
	LION,
	OURS,
	ELEPHANT;
	
	/**
	 * Récupérer la longueur du nom de l'animal
	 *
	 * @return la longueur
	 */
	public int length() {
		return this.toString().length();
	}
	
	
	/**
	 * Récupérer la longueur du plus long nom entre tous les animaux
	 *
	 * @return la longueur maximal
	 */
	public static int getMaxLength() {
		int max = 0;
		
		for(EnumAnimal animal : EnumAnimal.values()) {
			if(max < animal.length()) {
				max = animal.length();
			}
		}
		
		return max;
	}
}
