/**
 * @file Chaine.cpp
 * Projet sem06-tp-Cpp1
 * @author l'équipe pédagogique
 * @version 1 25/01/05
 * @brief Composant de chaîne d'items avec simple chaînage
 * Structures de données et algorithmes - DUT1 Paris 5
 */

#include <iostream>
#include <cassert>
using namespace std;

#include "Chaine.h"
#include "Etat.h"

void Chaine::initialiser() {
	tete = NULL;
	courant = &tete;
}

void Chaine::detruire() {
	debut();
	while (!estVide())
		supprimer();
}

bool Chaine::estVide() {
	return tete == NULL;
}

void Chaine::inserer(const Etat& elem) {
	Maillon* m = new Maillon;
	m->elem = elem;
	m->suiv = *(courant);
	*(courant) = m;
}

void Chaine::supprimer() {
	assert(!estFin());
	Maillon* m = *(courant);
	*(courant) = (*(courant))->suiv;
	delete m;
}

void Chaine::ecrire(const Etat& elem) {
	assert(!estFin());
	(*(courant))->elem = elem;
}

Etat Chaine::lire() {
	assert(!estFin());
	return (*(courant))->elem;
}

void Chaine::debut() {
	courant = &(tete);
}

bool Chaine::estDebut() {
	return courant == &(tete);
}

void Chaine::fin() {
	while (!estFin())
		suivant();
}

bool Chaine::estFin() {
	return *(courant) == NULL;
}

void Chaine::suivant() {
	assert(!estFin());
	courant = &((*(courant))->suiv);
}
