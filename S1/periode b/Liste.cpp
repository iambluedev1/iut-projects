#include <stdlib.h>
#include <stdio.h>
#include <cassert> 
#include <iostream>

#include "Liste.h"
#include "Chaine.h"
#include "Etat.h"

using namespace std;
 
void Liste::initialiser() {
	ch.initialiser();
	nb = 0;
	index = 0;
}

void Liste::detruire() {
	ch.detruire();
}

unsigned int Liste::longueur() {
	return nb;
}

static void deplacer(Liste& l, unsigned int i) {
	assert(i <= l.longueur());
	if (l.index > i) {
		l.ch.debut();
		l.index = 0;
	}
	while (l.index != i) {
		l.ch.suivant();
		l.index++;
	}
}

Etat Liste::lire(unsigned int i) {
	assert((i >= 0) && (i < longueur()));
	deplacer(*this, i);
	return (ch.lire());
}

void Liste::inserer(unsigned int i, const Etat& it) {
	assert((i >= 0) && (i <= longueur()));
	deplacer(*this, i);
	ch.inserer(it);
	nb++;
}

void Liste::supprimer(unsigned int i) {
	assert((i >= 0) && (i < longueur()));
	deplacer(*this, i);
	ch.supprimer();
	nb--;
}

bool Liste::existe(const Etat & etat) {
	for (unsigned int i = 0; i < nb; i++) {
		Etat e = lire(i);
		if (e.estEgaleA(etat)) {
			return true;
		}
	}
	return false;
}

Etat Liste::minimum(bool sup)
{
	Etat etatMin = lire(0);

	unsigned int index = 0;

	for (unsigned int i = 0; i < nb; i++) {
		Etat e = lire(i);
		if ((e.g + e.h) < (etatMin.g + etatMin.h)) { //Si le f de l'état e est plus petit que celui déjà stocké, on écrase l'ancien et on sauvegarde e
			etatMin = e;
			index = i;
		}
		else if ((e.g + e.h) == (etatMin.g + etatMin.h)) { //Si le f de l'état e ainsi que le plus petit etat que nous avons pu trouvé sont égaux
			if (e.h < etatMin.h) { //On garde l'état avec le plus petit h
				etatMin = e;
				index = i;
			}
		}
	}

	//On supprime l'etat a la position index dans la liste, si on nous le demande
	if (sup) { 
		supprimer(index);
	}

	//On retourne le plus petit état que avons trouvés
	return etatMin;
}