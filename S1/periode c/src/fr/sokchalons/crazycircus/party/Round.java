package fr.sokchalons.crazycircus.party;

import java.util.ArrayList;
import java.util.List;

import fr.sokchalons.crazycircus.Game;
import fr.sokchalons.crazycircus.entities.EnumAnimal;
import fr.sokchalons.crazycircus.entities.Player;
import fr.sokchalons.crazycircus.structure.Part;

/**
 * La classe Tour
 */
public class Round {
	
	/** La situation de départ */
	private Part start;
	
	/** La situation d'arrivée */
	private Part end;
	
	/** Tableau contenant les joueurs qui ont fait une erreur durant le tour */
	private List<Player> errors;
	
	/** Le gagnant du tour */
	private Player winner;
	
	/**
	 * Constructeur de la classe Tour permet d'instancier un nouveau tour
	 */
	public Round() {
		this.errors = new ArrayList<Player>();
	}
	
	/**
	 * Marquer un joueur comme ayant fait une erreur
	 *
	 * @param p : le joueur en question
	 */
	public void addError(Player p) {
		this.errors.add(p);
	}
	
	/**
	 * Determiner si un joueur peut jouer
	 *
	 * @param p : le joueur en question
	 * @return true, s'il peut jouer
	 */
	public boolean canPlay(Player p) {
		return !this.errors.contains(p);
	}
	
	/**
	 * Vérifier que la séquence de mouvements permet de solutionner le tour
	 *
	 * @param mvs : la séquence de mouvements
	 * @param game : l'object jeu
	 * @return true, si la séquence se trouve être une solution
	 */
	public boolean isAGoodSequence(List<EnumMovement> mvs, Game game) {
		Part copy = new Part(this.start);
	
		
		for(int i = 0; i < mvs.size(); i++) {
			EnumMovement mv = mvs.get(i);
			switch(mv) {
				case KI:
					EnumAnimal last = copy.getLeftPodium().getLast();
					copy.getRightPodium().addAnimal(last);
					copy.getLeftPodium().remove(last);
					break;
				case LO:
					EnumAnimal last1 = copy.getRightPodium().getLast();
					copy.getLeftPodium().addAnimal(last1);
					copy.getRightPodium().remove(last1);
					break;
				case MA:
					copy.getRightPodium().inverse();
					break;
				case NI:
					copy.getLeftPodium().inverse();
					break;
				case SO:
					EnumAnimal last2 = copy.getLeftPodium().getLast();
					EnumAnimal last3 = copy.getRightPodium().getLast();
					
					copy.getLeftPodium().remove(last2);
					copy.getLeftPodium().addAnimal(last3);
					
					copy.getRightPodium().remove(last3);
					copy.getRightPodium().addAnimal(last2);
					
					break;
				default:
					break;
			}
		}
		
		Part tmp = new Part(this.start);
		
		this.start = copy;
		
		if(this.start.isEqualsTo(this.end)) {
			this.start = tmp;
			return true;
		}else {
			this.start = tmp;
			return false;
		}
	}
	
	/**
	 * Désigner un gagnant au tour
	 *
	 * @param p : le joueur qui a gagné le tour
	 */
	public void setWinner(Player p) {
		this.winner = p;
		p.addScore();
	}
	
	/**
	 * Récupère le dernier joueur en vie
	 *
	 * @param players : liste de tous les joueurs
	 * @return dernier joueur en vie
	 */
	public Player getLast(List<Player> players) {
		Player p = null;
		
		for(Player tmp : players) {
			if(!this.errors.contains(tmp)) {
				p = tmp;
			}
		}
		
		return p;
	}
	
	/**
	 * Définir la situation de départ
	 *
	 * @param start : situation de départ
	 */
	public void setStart(Part start) {
		this.start = start;
	}
	
	/**
	 * Définir la situation d'arrivée
	 *
	 * @param end : situation d'arrivée
	 */
	public void setEnd(Part end) {
		this.end = end;
	}
	
	/**
	 * Récupérer le gagnant
	 *
	 * @return le gagnant
	 */
	public Player getWinner() {
		return this.winner;
	}
	
	/**
	 * Récupérer la situation d'arrivée
	 *
	 * @return situation d'arrivée
	 */
	public Part getEnd() {
		return this.end;
	}
	
	/**
	 * Récupérer la situation de départ
	 *
	 * @return situation de départ
	 */
	public Part getStart() {
		return this.start;
	}
	
	/**
	 * Récupérer le nombre total de joueur ne pouvant pas jouer ce tour
	 *
	 * @return le nombre de joueur
	 */
	public int countErrors() {
		return this.errors.size();
	}
}
