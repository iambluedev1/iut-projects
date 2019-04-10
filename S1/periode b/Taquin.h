#pragma once
#ifndef _TAQUIN_
#define _TAQUIN_

#include "Tableau2D.h"
#include "Liste.h"
#include "Position.h"

struct Taquin {
	Tab2D initial; //Damier initial (enregistré par entrée clavier)
	Liste listeEtatsExplores; //Correspond à la liste des Etats que l'algorithme a explorés
	Liste listeEtatsAExplorer; //Correspond à la liste des Etats que l'algoithme doit explorer

	Tab2D *etatsbuts; //Tableau contenant les différentes solutions du Taquin
	Etat *solutionTrouvée; //Pointeur vers la solution qui a été trouvé via l'algorithme

	/*
	 * @brief Initialisation du Taquin
	 * @param [in-out] : instance du Taquin actuel
	 */
	void initialiser();

	/*
	 * @brief Fonction contenant l'algorithme de résolution du Taquin
	 * @param [in-out] : instance du Taquin actuel
	 */
	void jouer();

	/*
	 * @brief Afficher le Taquin
	 * Affiche le contenu des listes
	 * @deprecated N'est plus utilisé dans le Sprint 5
	 * @param [in-out] : instance du Taquin actuel
	 */
	void afficher();

	/*
	 * @brief Fonction permettant de générer les Etats fils d'un état
	 * @param [int] parent : Etat du quel on souhaite générer les états fils
	 * @param [in-out] : instance du Taquin actuel
	 * @return un tableau des différents états fils
	 */
	//Etat *genererEtatsFils(Etat &parent);

	/*
	 * @brief Détermine si un état est déjà présente dans une Liste
	 * @param [in] e : Etat pour lequel on souhaite vérifier la présence
	 * @param [in] l : Liste dans laquelle on souhaite effectuer la vérification
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