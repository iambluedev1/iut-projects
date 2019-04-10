#pragma once
#ifndef _ETAT_
#define _ETAT_

#include "Tableau2D.h"
#include "Mouvements.h"

struct Etat {
	Tab2D damierCourant; //Damier résultant du mouvement depuis le damier parent
	Mouvement mouvement; //Mouvement effectué pour avoir le damier courant
	int indexParent; //Index de l'état parent dans la liste des états explorés

	unsigned int g; //Nombre de coups depuis l'état initial
	unsigned int h; //Nombre de case minimum de différence avec les états buts

	/*
	 * @brief Initialisation du damier
	 * @param [in] damierCourant : Damier résultant
	 * @param [in] mouvement : Mouvement effectué
	 * @param [in] etatParent : pointeur vers l'état parent
	 * @param [in] tabs : tableau contenant les solutions du taquin permettant de calculer l'heuristique
	 * @param [in-out] : instance actuelle de l'état 
	 *
	 */
	void initialiser(Tab2D damierCourant, Mouvement mouvement, int indexParent, Etat *e, Tab2D *tabs);

	/*
	 * @brief Affichage de l'état
	 * @param [in-out] : instance actuelle de l'état 
	 */
	void afficher();

	/*
	 * @brief Determine si l'état est l'une des solutions du Taquin
	 * @param [in] tabs : tableau contenant les solutions du taquin 
	 * @param [in-out] : instance actuelle de l'état
	 * @return true si oui, false sinon
	 */
	bool estBut(const Tab2D *tabs);

	/*
	 * @brief Determine si deux états sont égaux
	 * @param[in] etat : Etat avec lequel on souhaite faire une comparaison
	 * @param [in-out] : instance actuelle de l'état
	 * @return true si oui, false sinon
	 */
	bool estEgaleA(const Etat &etat);

	/*
	 * @brief Permet de calculer l'heuristique d'un Etat
	 * L'heuristique correspond au nombre de case minimum de différence avec les solutions du Taquin
	 * @param [in] tabs : tableau contenant les solutions du taquin
	 * @param [in-out] : instance actuelle de l'état
	 * @return nombre minimum de case différentes
	 */
	unsigned int heuristique(const Tab2D *tabs);
};


#endif