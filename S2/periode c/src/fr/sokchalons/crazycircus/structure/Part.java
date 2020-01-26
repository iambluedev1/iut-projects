package fr.sokchalons.crazycircus.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Partie
 */
public class Part {

	/** Tableau contenant les podiums */
	private List<Podium> podiums;
	
	/**
	 * Constructeur de la classe Partie permet d'instancier une nouvelle Partie
	 *
	 * @param left : situation de départ
	 * @param right : situation d'arrivée
	 */
	public Part(Podium left, Podium right) {
		this.podiums = new ArrayList<Podium>();
		this.podiums.add(left);
		this.podiums.add(right);
	}
	
	/**
	 * Constructeur de la classe Partie permet d'instancier une nouvelle Partie
	 *
	 * @param part : La partie à copier
	 */
	public Part(Part part) {
		this.podiums = new ArrayList<Podium>(part.podiums);
	}
	
	/**
	 * Déterminer si deux parties sont les mêmes
	 *
	 * @param p2 : la partie avec laquelle on souhaite vérifier
	 * @return true, si elles sont les mêmes
	 */
	public boolean isEqualsTo(Part p2) {
		if(this.podiums.get(0).getAnimals().size() != p2.getLeftPodium().getAnimals().size()) {
			return false;
		}
		
		if(this.podiums.get(1).getAnimals().size() != p2.getRightPodium().getAnimals().size()) {
			return false;
		}
		
		for(int i = 0; i < this.podiums.get(0).getAnimals().size(); i++) {
			if(this.podiums.get(0).getAnimals().get(i) != p2.getLeftPodium().getAnimals().get(i)) {
				return false;
			}
		}
		
		for(int i = 0; i < this.podiums.get(1).getAnimals().size(); i++) {
			if(this.podiums.get(1).getAnimals().get(i) != p2.getRightPodium().getAnimals().get(i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Récupérer la liste des podiums
	 *
	 * @return les podiums
	 */
	public List<Podium> getPodiums(){
		return this.podiums;
	}
	
	/**
	 * Récupérer le podium de gauche (ici c'est le podium bleu)
	 *
	 * @return podium de gauche
	 */
	public Podium getLeftPodium() {
		return this.podiums.get(0);
	}
	
	/**
	 * Récupérer le podium de droite (ici c'est le podium rouge)
	 *
	 * @return podium de droite
	 */
	public Podium getRightPodium() {
		return this.podiums.get(1);
	}
}
