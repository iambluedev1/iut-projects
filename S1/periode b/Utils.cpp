#include <iostream>
#include "Utils.h"

using namespace std;

Position chercherCaseNulle(const Tab2D &tab) {
	for (int x = 0; x < tab.nbLignes; x++) {
		for (int y = 0; y < tab.nbColonnes; y++) {
			//on cherche la case contenant un # (c'est une case nulle)
			if (tab.damier[x][y] == 0) {
				//on l'a trouvé, on peut arreter les deux boucles et retourner sa position
				return Position{ x, y };
			}
		}
	}

	//c'est pas sensé arriver, mais au cas ou un lance une erreur pour pas que le programme continu

	throw new exception("Case nulle non trouvée");
}

Mouvement *chercherMouvementsPossible(const Tab2D & tab, const Position &pos) {
	//on crée un tableau de 4 cases car il y a 4 mouvements et on initialise chaque case a un mouvement nul
	Mouvement *mouvements = new Mouvement[4];

	for (int i = 0; i < 4; i++) {
		mouvements[i] = NONE;
	}

	unsigned int maxL = tab.nbLignes - 1, maxC = tab.nbColonnes - 1, nb = 0;

	if (pos.x == 0) {
		//se trouve sur la première ligne
		// mouvements possibles SUD, EST, OUEST
		mouvements[nb++] = EST;
		mouvements[nb++] = SUD;
		mouvements[nb++] = OUEST;
	}
	else if (pos.x == maxL) {
		//se trouve sur la dernières ligne
		// mouvements possibles SUD, EST, OUEST
		mouvements[nb++] = EST;
		mouvements[nb++] = OUEST;
		mouvements[nb++] = NORD;
	}
	else {
		//se trouve n'importe ou dans les lignes ormis au extremités
		//mouvements possible NORD, SUD, EST, OUEST
		mouvements[nb++] = EST;
		mouvements[nb++] = SUD;
		mouvements[nb++] = OUEST;
		mouvements[nb++] = NORD;
	}

	return mouvements;
}

Position nouvellePositionDepuisMouvement(const Position &pos, const Mouvement &mouvement, const unsigned int nbc) {
	Position nouvellePosition = pos;

	switch (mouvement) {
		case EST:
			//on deplace de 1 vers la droite
			//on vérifie si l'on se trouve a une extremité ou non
			if (nbc - 1 == pos.y) {
				nouvellePosition.y = 0;
			}
			else {
				nouvellePosition.y++;
			}
			break;
		case NORD:
			//on monte de 1
			nouvellePosition.x--;
			break;
		case OUEST:
			//on de place de 1 vers la gauche
			//on vérifie si l'on se trouve a une extremité ou non
			if (pos.y == 0) {
				nouvellePosition.y = nbc - 1;
			}
			else {
				nouvellePosition.y--;
			}
			break;
		case SUD:
			//on descend de 1
			nouvellePosition.x++;
			break;
		default:
			break;
	}

	return nouvellePosition;
}

void genererEtatsButs(Taquin &t, const unsigned int nbl, const unsigned int nbc) {
	
	//On crée un tableau en partant de l'état initial
	Tab2D damierOrdonné;
	damierOrdonné.initialiser(nbl, nbc);
	damierOrdonné.copier(t.initial);
	damierOrdonné.ordonner(); //que l'on ordonne

	unsigned int compteur = 1;
	Tab2D *solutions = new Tab2D[nbc];
	solutions[0] = damierOrdonné; //et qu'on enregistre, l'indiquant comme étant l'une des solutions du Taquin

	//Tant qu'il reste des solutions à trouver
	//Il y a autant de solution que de colonne dans le damier
	while (compteur < nbc) {
		Tab2D tmp;
		tmp.initialiser(nbl, nbc);
		tmp.copier(solutions[compteur - 1]);

		//Boucles permettant de copier la dernière colonne (à droite) et de la mettre tout à gauche
		for (unsigned int i = 0; i < nbl; i++) {
			for (unsigned int j = 0; j < nbc - 1; j++) {
				tmp.damier[i][j + 1] = solutions[compteur - 1].damier[i][j];
			}

			tmp.damier[i][0] = solutions[compteur - 1].damier[i][nbc - 1];
		}

		//On enregistre la solution
		solutions[compteur] = tmp;
		compteur++;
	}

	//On enregistre ainsi toutes les solutions trouvées
	for (unsigned int i = 0; i < nbc; i++) {
		t.etatsbuts[i] = solutions[i];
	}
}

int minimumTableau(int *tab, const unsigned int taille)
{
	int min = tab[0];

	for (int i = 0; i < taille; i++) {
		if (tab[i] < min) {
			min = tab[i];
		}
	}

	return min;
}
