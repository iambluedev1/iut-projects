#include <iostream>
#include "Etat.h"
#include "Position.h"
#include "Utils.h"

using namespace std;

void Etat::initialiser(Tab2D d, Mouvement m, int index, Etat *e, Tab2D *tabs) {
	damierCourant = d;
	mouvement = m;
	indexParent = index;
	g = (e == NULL) ? 0 : (*e).g + 1; //Condition utilisé pour l'Etat inial pour lequel g doit valloir 0
	h = heuristique(tabs);
}

void Etat::afficher() {
	// Affiche le mouvement effectué en clair
	if (mouvement != NONE) {
		cout << ((mouvement == 1) ? "NORD" : ((mouvement == 2) ? "EST" : ((mouvement == 3) ? "SUD" : "OUEST"))) << endl;
	}

	Tab2D t = damierCourant;

	t.afficher();
	//printf("f=g+h=%d+%d=%d\n", g, h, (g+h)); Plus affiché dans le sprint 5
}

bool Etat::estEgaleA(const Etat &etat) {
	//On parcour les damiers à la recherche d'une case qui différerait entre les deux 
	for (int i = 0; i < damierCourant.nbLignes; i++) {
		for (int j = 0; j < damierCourant.nbColonnes; j++) {
			//Le contenu d'une case dans les deux tableaus est-il le même ?
			if (damierCourant.damier[i][j] != etat.damierCourant.damier[i][j]) {
				//il y a au moins une case différente entre les deux tableaux, on arrete ici la recherche et on retourne faux.
				return false;
			}
		}
	}

	return true;
}

unsigned int Etat::heuristique(const Tab2D *tabs) {
	int *heuristiques = new int[damierCourant.nbColonnes]; //tableau qui permet de stocker les différents heuristiques que l'on va calculer
	
	//Boucle permettant d'avoir autant d'heuristiques que de solution, car il y a autant de solutions que de colonnes dans le damier
	for (int i = 0; i < damierCourant.nbColonnes; i++) {
		int h = 0;

		//On parcours le damier
		for (int x = 0; x < damierCourant.nbLignes; x++) {
			for (int y = 0; y < damierCourant.nbColonnes; y++) {
				//Si une case diffère entre les deux damiers et que cela n'est pas la case nulle
				if ((damierCourant.damier[x][y] != tabs[i].damier[x][y]) && damierCourant.damier[x][y] != 0) {
					//On incrémente h
					h++;
				}
			}
		}

		//On ajoute l'heuristique calculé dans le tableau contenant les heuristiques
		heuristiques[i] = h;
	}

	//on retourne le plus petit heuristique que l'on vient de calculer
	return minimumTableau(heuristiques, damierCourant.nbColonnes);
}

bool Etat::estBut(const Tab2D *tabs) {
	Position caseN = chercherCaseNulle(damierCourant);

	//Si la case ne se trouve pas sur la dernière ligne, ce n'est pas un état but
	if (caseN.x != damierCourant.nbLignes - 1) {
		return false;
	}

	//Boucle permettant de vérifier si l'état est un état but avec toutes les solutions que nous avons générés 
	//Il y a autant de solutions que de nombre de colonne
	for (int i = 0; i < damierCourant.nbColonnes; i++) {
		Etat tmp;
		tmp.damierCourant = tabs[i];

		//Si l'état est égale à une solution, on retourne vrai et on arrete la recherche
		if ((*this).estEgaleA(tmp)) {
			return true;
		}
	}

	return false;
}