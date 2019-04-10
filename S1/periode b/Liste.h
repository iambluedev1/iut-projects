#pragma once
#ifndef LISTE_H
#define LISTE_H

#include "Chaine.h"
#include "Etat.h"

struct Liste {
	Chaine ch;	// Stockage des �l�ments de liste dans une cha�ne d'items
	unsigned int nb;    // nombre d'�l�ments dans la liste
	unsigned int index;	// index de la position courante dans la cha�ne    

	/**
	 * @brief Initialiser une liste vide
	 * la liste est allou�e en m�moire dynamique
	 * @see detruire, elle est � d�sallouer en fin d�utilisation
	 * @param[out] : instance actuelle de la structure Liste (vide)
	 */
	void initialiser();

	/**
	 * @brief D�sallouer une liste
	 * @see initialiser, la liste a d�j� �t� allou�e en m�moire dynamique
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
	 * @brief Lire un �l�ment de liste
	 * @param[in] : instance actuelle de la structure Liste
	 * @return l'item lu
	 * @pre 0<=i<=longueur(l)
	 */
	Etat lire(unsigned int i);

	/**
	 * @brief Ins�rer un �l�ment dans la liste
	 * @param[in,out] : instance actuelle de la structure Liste
	 * @param[in] i : la position avant laquelle l'�l�ment est ins�r�
	 * @param[in] it : l'�l�ment ins�r�
	 * @pre 0<=i<=longueur(l)+1
	 */
	void inserer(unsigned int i, const Etat& it);

	/**
	 * @brief Supprimer un �l�ment dans la liste
	 * @param[in,out] : instance actuelle de la structure Liste
	 * @param[in] i : la position de l'�l�ment � supprimer
	 * @pre 0<=i<=longueur(l)
	 */
	void supprimer(unsigned int i);

	/*
	 * @brief Determine si un �lement existe dans la liste
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
