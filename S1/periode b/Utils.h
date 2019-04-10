#pragma once
#ifndef _UTILS_
#define _UTILS_

#include "Position.h"
#include "Etat.h"
#include "Taquin.h"

/*
 * @brief Fonction permettant de trouver la position de la case contenant un # (case nulle)
 * @param [in] tab : Damier dans lequel on souhaite trouver la case nulle
 * @return la Position de la case nulle
 */
Position chercherCaseNulle(const Tab2D &tab);

/*
 * @brief Fonction permettant de lister les différents mouvements possibles pour un damier
 * @param [int] tab : Damier pour lequel on souhaite chercher les mouvements
 * @param [in] pos : Position de la case nulle dans le damier
 * @return tableau contenant tout les mouvements réalisables
 */
Mouvement *chercherMouvementsPossible(const Tab2D &tab, const Position &pos);

/*
 * @brief Génére la nouvelle position de la case nulle en fonction d'un mouvement
 * @param [in] pos : Position de la case nulle
 * @param [in] mouvement : Mouvement que l'on souhaite effectuer
 * @param [in] nbc : Nombre de colonnes du damier
 * @return La nouvelle position de la case nulle
 */
Position nouvellePositionDepuisMouvement(const Position &pos, const Mouvement &mouvement, const unsigned int nbc);

/*
 * @brief Fonction permettant de générer les solutions du Taquin
 * @param [in-out] t : Taquin pour lequel on souhaite générer les solutions
 * @param [in] nbl : nombre de ligne du damier initial
 * param [in] nbc : nombre de colonne du damier initial
 */
void genererEtatsButs(Taquin &t, const unsigned int nbl, const unsigned int nbc);

/*
 * @brief Fonction permettant de retourner le minimum d'un tableau d'entier
 * @param [in] tab : Tableau contenant les entiers
 * @param [in] taille : taille du tableau
 * @return l'entier le plus petit
 */
int minimumTableau(int *tab, const unsigned int taille);

#endif