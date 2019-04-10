#pragma once
#ifndef _TAQUIN_
#define _TAQUIN_

#include "Tableau2D.h"
#include "Liste.h"
#include "Position.h"

struct Taquin {
	Tab2D initial; //Damier initial (enregistr� par entr�e clavier)
	Liste listeEtatsExplores; //Correspond � la liste des Etats que l'algorithme a explor�s
	Liste listeEtatsAExplorer; //Correspond � la liste des Etats que l'algoithme doit explorer

	Tab2D *etatsbuts; //Tableau contenant les diff�rentes solutions du Taquin
	Etat *solutionTrouv�e; //Pointeur vers la solution qui a �t� trouv� via l'algorithme

	/*
	 * @brief Initialisation du Taquin
	 * @param [in-out] : instance du Taquin actuel
	 */
	void initialiser();

	/*
	 * @brief Fonction contenant l'algorithme de r�solution du Taquin
	 * @param [in-out] : instance du Taquin actuel
	 */
	void jouer();

	/*
	 * @brief Afficher le Taquin
	 * Affiche le contenu des listes
	 * @deprecated N'est plus utilis� dans le Sprint 5
	 * @param [in-out] : instance du Taquin actuel
	 */
	void afficher();

	/*
	 * @brief Fonction permettant de g�n�rer les Etats fils d'un �tat
	 * @param [int] parent : Etat du quel on souhaite g�n�rer les �tats fils
	 * @param [in-out] : instance du Taquin actuel
	 * @return un tableau des diff�rents �tats fils
	 */
	//Etat *genererEtatsFils(Etat &parent);

	/*
	 * @brief D�termine si un �tat est d�j� pr�sente dans une Liste
	 * @param [in] e : Etat pour lequel on souhaite v�rifier la pr�sence
	 * @param [in] l : Liste dans laquelle on souhaite effectuer la v�rification
	 * @param [in-out] : instance du Taquin actuel
	 * @return true si oui, false sinon
	 */
	bool appartient(Etat &e, Liste &l);

	/*
	 * @brief Affiche l'arbre des solutions du Taquin
	 * @param [in-out] : instance du Taquin actuel
	 */
	void afficherSolution();

	void genererFils(Etat &parent);
};

#endif