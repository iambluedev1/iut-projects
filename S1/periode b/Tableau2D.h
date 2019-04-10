#pragma once
#ifndef _TABLEAU2D_
#define _TABLEAU2D_

#include "Item.h"

struct Tab2D {
	Item** damier; //Tableau à double dimenssion permettant de structurer le damier
	int nbLignes; //Nombre de lignes constituant le damier
	int nbColonnes; //Nombre de colonnes constituant le damier

	/*
	 * @brief Initialisation du tableau
	 * @param [in] nbLignes : nombre de lignes constituant le damier
	 * @param [in] nbColonnes : nombre de colonnes constituant le damier
	 * @param [in-out] : instance du Tableau actuel
	 */
	void initialiser(unsigned int nbLignes, unsigned int nbColonnes);

	/*
	 * @brief Suppréssion en mémoire du Tableau
	 * @param [in-out] : instance du Tableau actuel
	 */
	void detruire();

	/*
	 * @brief Remplit via une entrée clavier le Tableau à double dimensions
	 * @param [in-out] : instance du Tableau actuel
	 */
	void lire();

	/*
	 * @brief Affiche le Tableau sous forme d'un damier
	 * @param [in-out] : instance du Tableau actuel
	 */
	void afficher();

	/*
	 * @brief Fonction permettant de copier le contenu du tableau passé en paramètre
	 * @param[in] tab : Tableau que l'on souhaite copier
	 * @param [in-out] : instance du Tableau actuel
	 */
	void copier(const Tab2D &tab);

	/*
	 * @brief Fonction permettant de ranger dans l'odre croissant le Tableau 
	 * @param [in-out] : instance du Tableau actuel
	 */
	void ordonner();
};

#endif