#ifndef CHAINE_H_
#define CHAINE_H_

/**
 * @file Chaine.h
 * @brief Projet sem06-tp-Cpp1
 * @author l'équipe pédagogique
 * @version 1 25/01/05
 * Composant de chaîne d'items avec simple chaînage
 * Structures de données et algorithmes - DUT1 Paris 5
 */

#include "Etat.h"
#include "Chaine.h"

/** Type Maillon avec le principe du simple chaînage (maillon suivant)
 */
struct Maillon {
    Etat elem;     // Elément (du maillon) de la liste chaînée
    Maillon* suiv; // Pointeur sur le maillon suivant
};

/** Type Chaine 
 *  Repères du début de chaîne et du  maillon courant
 */
struct Chaine {
    Maillon* tete;     // Pointeur sur le maillon de tête
    Maillon** courant; // Superpointeur (pointeur du pointeur) 
    				   // désignant le maillon courant	


	/**
	 * brief Initialisation d'une chaîne vide
	 * la liste est allouée en mémoire dynamique
	 * @see detruire, la liste est à désallouer en fin d’utilisation
	 * @param[in,out] : instance actuelle de la Chaine
	 */
	void initialiser();

	/**
	 * brief Désallocation d'une chaîne
	 * @see initialiser, la liste a déjà été allouée en mémoire dynamique
	 * @param[in,out] : instance actuelle de la Chaine
	 */
	void detruire();

	/**
	 * @brief Test de chaîne vide
	 * @param[in] : instance actuelle de la Chaine
	 * @return true si c est vide, false sinon
	 */
	bool estVide();

	/**
	 * @brief Insertion d'un élément avant le maillon courant
	 * Le nouveau maillon devient le maillon courant
	 * @param[in,out] : instance actuelle de la Chaine
	 * @param[in] elem : l'élément inséré
	 */
	void inserer(const Etat& elem);

	/**
	 * @brief Suppression du maillon courant
	 * @param[in,out] : instance actuelle de la Chaine
	 * @pre Le maillon courant existe et ce n'est pas la fin de la chaîne
	 */
	void supprimer();

	/**
	 * @brief Modification de l'élément stocké dans le maillon courant
	 * @param[in,out] : instance actuelle de la Chaine
	 * @param[in] elem : l'élément à écrire
	 * @pre Le maillon courant existe et ce n'est pas la fin de la chaîne
	 */
	void ecrire(const Etat& elem);

	/**
	 * @brief Lecture de l'élément stocké dans le maillon courant
	 * @param[in] : instance actuelle de la Chaine
	 * @return l'élément lu
	 * @pre Le maillon courant existe et ce n'est pas la fin de la chaîne
	 */
	Etat lire();

	/**
	 * @brief Positionnement du maillon courant en début de chaîne
	 * @param[in,out] : instance actuelle de la Chaine
	 */
	void debut();

	/**
	 * @brief Test de debut de chaine
	 * @param[in] : instance actuelle de la Chaine
	 * @return true si le maillon courant est le début de chaîne, false sinon
	 */
	bool estDebut();

	/**
	 * @brief Positionnement du maillon courant en fin de chaîne
	 * @param[in,out] : instance actuelle de la Chaine
	 * @return true si le maillon courant est la fin de chaîne, false sinon
	 */
	void fin();

	/**
	 * @brief Test de fin de chaîne
	 * @param[in] : instance actuelle de la Chaine
	 * @return true si le maillon courant est la fin de chaîne, false sinon.
	 */
	bool estFin();

	/**
	 * @brief Positionnement du maillon courant au maillon suivant
	 * @param[in,out] : instance actuelle de la Chaine
	 * @pre Le maillon courant existe et ce n'est pas la fin de la chaîne
	 */
	void suivant();
};

#endif /*CHAINE_H_*/
