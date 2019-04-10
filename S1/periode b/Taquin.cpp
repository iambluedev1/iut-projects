#include <iostream>
#include "Taquin.h"
#include "Utils.h"

using namespace std;

void Taquin::initialiser() {
	unsigned int nbl, nbc;
	Tab2D tab;

	cin >> nbl >> nbc;

	tab.initialiser(nbl, nbc);
	tab.lire();

	initial = tab;

	//On initialise les diff�rentes listes
	listeEtatsAExplorer.initialiser();
	listeEtatsExplores.initialiser();

	//ainsi que le tableau contenant les solutions du Taquin
	etatsbuts = new Tab2D[nbc];

	//que l'on g�n�re maintenant
	genererEtatsButs((*this), nbl, nbc);

	//On ins�re �galement l'�tat initial dans la liste des �tats � explorer
	Etat etat1;
	etat1.initialiser(initial, NONE, 0, NULL, etatsbuts);
	listeEtatsAExplorer.inserer(0, etat1);
}

void Taquin::jouer() {
	unsigned int it = 0;
	bool fini = false;

	//Tant qu'aucune solution n'a �t� trouv�e, ou que nous ne d�passons pas les 100 it�rations
	while (!fini && it < 1000000000) {
		//cout << "Iteration " << it << endl; n'est plus affich� dans le sprint 5

		//Si la liste des �tats � explorer n'est pas vide
		if (listeEtatsAExplorer.longueur() != 0) {
			Etat e = listeEtatsAExplorer.minimum(true); //On r�cup�re via l'algorithme l'�tat que l'on doit traiter

			if (e.estBut((*this).etatsbuts)) { //Si c'est un �tat but
				solutionTrouv�e = &e; //On l'enregistre

				fini = true; //On indique au programme qu'une solution a �t� trouv�e

				break; //et on arr�te la boucle while
			}

			//sinon

			listeEtatsExplores.inserer(listeEtatsExplores.longueur(), e); //on l'insert dans la liste des etats explor�

			genererFils(e);
		}

		it++;
	}

	//Si la boucle est termin�e

	if (fini) { //et qu'une solution a �t� trouv�e
		afficherSolution(); //on affiche l'arbre des solutions
	}
	else {//sinon
		cout << "Aucune solution au Taquin propos� n'a �t� trouv�e " << endl;
	}
}

bool Taquin::appartient(Etat &e, Liste &l)
{
	return l.existe(e);
}

void Taquin::afficherSolution() {
	int nbcoup = solutionTrouv�e->g; //g est �gal au nombre de coup depuis l'�tat initial

	cout << "Damier : " << solutionTrouv�e->damierCourant.nbLignes << " lignes, " << solutionTrouv�e->damierCourant.nbColonnes << " colonnes" << endl;
	cout << "Solution en " << nbcoup << " mouvements" << endl;
	
	Etat *e = solutionTrouv�e;
	Etat *arbre = new Etat[nbcoup]; 

	//On remonte dans l'aborescence
	for (int i = 0; i < nbcoup; i++) {
		arbre[i] = *e;
		*e = listeEtatsExplores.lire(e->indexParent);
	}

	initial.afficher();

	//On affiche en partant de la fin du tableau (= d�but du jeu)
	for (int i = nbcoup-1; i >= 0; i--) {
		arbre[i].afficher();
	}
}

void Taquin::genererFils(Etat &parent)
{

	Position caseN = chercherCaseNulle(parent.damierCourant); //on r�cup�re la position de la case contenant le #
	Mouvement *mouvementPossibles = chercherMouvementsPossible(parent.damierCourant, caseN); //on g�n�re tout les mouvements possibles

	for (int i = 3; i >= 0; i--) {
		Mouvement m = mouvementPossibles[i]; //On r�cup�re le mouvement � la position i

		if (m != NONE) {
			Position nouvellePosition = nouvellePositionDepuisMouvement(caseN, m, parent.damierCourant.nbColonnes); //on g�n�re la nouvelle position de la case nulle

			//On initialise un nouveau damier
			Tab2D nouveauDamier;
			nouveauDamier.initialiser(parent.damierCourant.nbLignes, parent.damierCourant.nbColonnes);
			nouveauDamier.copier(parent.damierCourant);

			//On inverse la case nulle entre l'ancienne et la nouvelle position
			Item tmp = nouveauDamier.damier[caseN.x][caseN.y];
			nouveauDamier.damier[caseN.x][caseN.y] = nouveauDamier.damier[nouvellePosition.x][nouvellePosition.y];
			nouveauDamier.damier[nouvellePosition.x][nouvellePosition.y] = tmp;

			//Puis on cr�e un nouvelle �tat fils
			Etat nouvelleEtat;
			nouvelleEtat.initialiser(nouveauDamier, m, listeEtatsExplores.longueur() - 1, &parent, etatsbuts);

			//Et on le sauvegarde dans le tableau contenant les �tats fils
			
			if (!(*this).appartient(nouvelleEtat, listeEtatsExplores) && !(*this).appartient(nouvelleEtat, listeEtatsAExplorer)) {
				listeEtatsAExplorer.inserer(0, nouvelleEtat); //on l'ajoute dans la liste des etats a explorer
			}
		}
	}

}
