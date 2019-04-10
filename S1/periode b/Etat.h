#pragma once
#ifndef _ETAT_
#define _ETAT_

#include "Tableau2D.h"
#include "Mouvements.h"

struct Etat {
	Tab2D damierCourant; //Damier r�sultant du mouvement depuis le damier parent
	Mouvement mouvement; //Mouvement effectu� pour avoir le damier courant
	int indexParent; //Index de l'�tat parent dans la liste des �tats explor�s

	unsigned int g; //Nombre de coups depuis l'�tat initial
	unsigned int h; //Nombre de case minimum de diff�rence avec les �tats buts

	/*
	 * @brief Initialisation du damier
	 * @param [in] damierCourant : Damier r�sultant
	 * @param [in] mouvement : Mouvement effectu�
	 * @param [in] etatParent : pointeur vers l'�tat parent
	 * @param [in] tabs : tableau contenant les solutions du taquin permettant de calculer l'heuristique
	 * @param [in-out] : instance actuelle de l'�tat 
	 *
	 */
	void initialiser(Tab2D damierCourant, Mouvement mouvement, int indexParent, Etat *e, Tab2D *tabs);

	/*
	 * @brief Affichage de l'�tat
	 * @param [in-out] : instance actuelle de l'�tat 
	 */
	void afficher();

	/*
	 * @brief Determine si l'�tat est l'une des solutions du Taquin
	 * @param [in] tabs : tableau contenant les solutions du taquin 
	 * @param [in-out] : instance actuelle de l'�tat
	 * @return true si oui, false sinon
	 */
	bool estBut(const Tab2D *tabs);

	/*
	 * @brief Determine si deux �tats sont �gaux
	 * @param[in] etat : Etat avec lequel on souhaite faire une comparaison
	 * @param [in-out] : instance actuelle de l'�tat
	 * @return true si oui, false sinon
	 */
	bool estEgaleA(const Etat &etat);

	/*
	 * @brief Permet de calculer l'heuristique d'un Etat
	 * L'heuristique correspond au nombre de case minimum de diff�rence avec les solutions du Taquin
	 * @param [in] tabs : tableau contenant les solutions du taquin
	 * @param [in-out] : instance actuelle de l'�tat
	 * @return nombre minimum de case diff�rentes
	 */
	unsigned int heuristique(const Tab2D *tabs);
};


#endif