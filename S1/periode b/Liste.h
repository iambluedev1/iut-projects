#pragma once
#ifndef LISTE_H
#define LISTE_H

#include "Chaine.h"
#include "Etat.h"

struct Liste {
	Chaine ch;	// Stockage des éléments de liste dans une chaîne d'items
	unsigned int nb;    // nombre d'éléments dans la liste
	unsigned int index;	// index de la position courante dans la chaîne    

	/**
	 * @brief Initialiser une liste vide
	 * la liste est allouée en mémoire dynamique
	 * @see detruire, elle est à désallouer en fin d’utilisation
	 * @param[out] : instance actuelle de la structure Liste (vide)
	 */
	void initialiser();

	/**
	 * @brief Désallouer une liste
	 * @see initialiser, la liste a déjà été allouée en mémoire dynamique
	 * @param[out] : instance actuelle de la structure Liste
	 */
	void detruire();

	/**
	 * @brief Longueur de liste
	 * @param[in] : instance actuelle de la structure Liste
	 * @return la longueur
	 */
	unsigned int longueur();

	/**
	 * @brief Lire un élément de liste
	 * @param[in] : instance actuelle de la structure Liste
	 * @return l'item lu
	 * @pre 0<=i<=longueur(l)
	 */
	Etat lire(unsigned int i);

	/**
	 * @brief Insérer un élément dans la liste
	 * @param[in,out] : instance actuelle de la structure Liste
	 * @param[in] i : la position avant laquelle l'élément est inséré
	 * @param[in] it : l'élément inséré
	 * @pre 0<=i<=longueur(l)+1
	 */
	void inserer(unsigned int i, const Etat& it);

	/**
	 * @brief Supprimer un élément dans la liste
	 * @param[in,out] : instance actuelle de la structure Liste
	 * @param[in] i : la position de l'élément à supprimer
	 * @pre 0<=i<=longueur(l)
	 */
	void supprimer(unsigned int i);

	/*
	 * @brief Determine si un élement existe dans la liste
	 * @param[in,out] : instance actuelle de la structure Liste
	 * @param[in] etat : Element a rechercher
	 * return le status
	 */
	bool existe(const Etat &etat);

	/*
	 * @brief Retourne le plus petit element en fonction de la valeur g de la structure
	 * @param[in, out] : instance actuelle de la structure Liste
	 * @param[in] supprimer : indique si l'on souhaite supprimer automatiquement l'element
	 * @param[out] indexOfEtat : pointeur permettant d'obtenir l'index de l'etat
	 */
	Etat minimum(bool supprimer);






	/*

	Etat dernier();

	Etat minimum(bool supprimer, unsigned int *indexOfEtat);*/
};

#endif
