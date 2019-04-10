package fr.sokchalons.crazycircus.entities;

/**
 * La classe Joueur
 */
public class Player {

	/** Identifiant du joueur */
	private String id;
	
	/** Le score du joueur */
	private int score;
	
	/**
	 * Constructor pour instancier un nouveau joueur
	 *
	 * @param name : nom du joueur (en l'occurence ici son identifiant)
	 */
	public Player(String name) {
		this.id = name;
		this.score = 0;
	}
	
	/**
	 * Incr�menter le score du joueur
	 */
	public void addScore() {
		this.score++;
	}
	
	/**
	 * R�cup�rer l'identifiant du joueur
	 *
	 * @return l'identifiant
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * R�cup�rer le score du joueur
	 *
	 * @return le score
	 */
	public int getScore() {
		return this.score;
	}
	
}
