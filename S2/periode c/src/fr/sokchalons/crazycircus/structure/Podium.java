package fr.sokchalons.crazycircus.structure;

import java.util.ArrayList;
import java.util.List;

import fr.sokchalons.crazycircus.entities.EnumAnimal;

/**
 * La classe Podium
 */
public class Podium {

	/** Couleur associée au podium */
	private String color;
	
	/** Tableau des animaux contenu dans le podium */
	private List<EnumAnimal> animals;
	
	/**
	 * Constructeur de la classe Podium, permet d'instancier un nouveau Podium
	 *
	 * @param color : la couleur du podium
	 */
	public Podium(String color) {
		this.color = color;
		this.animals = new ArrayList<EnumAnimal>();
	}
	
	/**
	 * Ajouter un animal à la fin du podium
	 *
	 * @param a : l'animal
	 */
	public void addAnimal(EnumAnimal a) {
		if(!this.animals.contains(a)) {
			this.animals.add(a);
		}
	}
	
	/**
	 * Récupérer le dernier animal du podium
	 *
	 * @return le dernier animal
	 */
	public EnumAnimal getLast() {
		return (this.animals.size() > 1) ? this.animals.get(this.getAnimals().size() - 1) : this.getFirst();
	}
	
	/**
	 * Récupérer le premier animal du podium
	 *
	 * @return le premier animal
	 */
	public EnumAnimal getFirst() {
		return (this.animals.size() > 0) ? this.animals.get(0) : null;
	}
	
	/**
	 * Inverser le podium
	 */
	public void inverse() {
		EnumAnimal first = this.getFirst();
		EnumAnimal last = this.getLast();
	
		this.animals.set(0, last);
		this.animals.set(this.animals.size() - 1, first);
	}
	
	/**
	 * Retirer un animal du podium
	 *
	 * @param a : l'animal a retirer
	 */
	public void remove(EnumAnimal a) {
		this.animals.remove(a);
	}
	
	/**
	 * Récupérer la couleur du podium
	 *
	 * @return la couleur
	 */
	public String getColor() {
		return this.color;
	}
	
	/**
	 * Récupérer la liste des animaux du podium
	 *
	 * @return la liste des animaux
	 */
	public List<EnumAnimal> getAnimals(){
		return this.animals;
	}
}
