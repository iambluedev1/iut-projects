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

	//On initialise les différentes listes
	listeEtatsAExplorer.initialiser();
	listeEtatsExplores.initialiser();

	//ainsi que le tableau contenant les solutions du Taquin
	etatsbuts = new Tab2D[nbc];

	//que l'on génère maintenant
	genererEtatsButs((*this), nbl, nbc);

	//On insère également l'état initial dans la liste des états à explorer
	Etat etat1;
	etat1.initialiser(initial, NONE, 0, NULL, etatsbuts);
	listeEtatsAExplorer.inserer(0, etat1);
}

void Taquin::jouer() {
	unsigned int it = 0;
	bool fini = false;

	//Tant qu'aucune solution n'a été trouvée, ou que nous ne dépassons pas les 100 itérations
	while (!fini && it < 1000000000) {
		//cout << "Iteration " << it << endl; n'est plus affiché dans le sprint 5

		//Si la liste des états à explorer n'est pas vide
		if (listeEtatsAExplorer.longueur() != 0) {
			Etat e = listeEtatsAExplorer.minimum(true); //On récupère via l'algorithme l'état que l'on doit traiter

			if (e.estBut((*this).etatsbuts)) { //Si c'est un état but
				solutionTrouvée = &e; //On l'enregistre

				fini = true; //On indique au programme qu'une solution a été trouvée

				break; //et on arrète la boucle while
			}

			//sinon

			listeEtatsExplores.inserer(listeEtatsExplores.longueur(), e); //on l'insert dans la liste des etats exploré

			genererFils(e);
		}

		it++;
	}

	//Si la boucle est terminée

	if (fini) { //et qu'une solution a été trouvée
		afficherSolution(); //on affiche l'arbre des solutions
	}
	else {//sinon
		cout << "Aucune solution au Taquin proposé n'a été trouvée " << endl;
	}
}

bool Taquin::appartient(Etat &e, Liste &l)
{
	return l.existe(e);
}

void Taquin::afficherSolution() {
	int nbcoup = solutionTrouvée->g; //g est égal au nombre de coup depuis l'état initial

	cout << "Damier : " << solutionTrouvée->damierCourant.nbLignes << " lignes, " << solutionTrouvée->damierCourant.nbColonnes << " colonnes" << endl;
	cout << "Solution en " << nbcoup << " mouvements" << endl;
	
	Etat *e = solutionTrouvée;
	Etat *arbre = new Etat[nbcoup]; 

	//On remonte dans l'aborescence
	for (int i = 0; i < nbcoup; i++) {
		arbre[i] = *e;
		*e = listeEtatsExplores.lire(e->indexParent);
	}

	initial.afficher();

	//On affiche en partant de la fin du tableau (= début du jeu)
	for (int i = nbcoup-1; i >= 0; i--) {
		arbre[i].afficher();
	}
}

void Taquin::genererFils(Etat &parent)
{

	Position caseN = chercherCaseNulle(parent.damierCourant); //on récupère la position de la case contenant le #
	Mouvement *mouvementPossibles = chercherMouvementsPossible(parent.damierCourant, caseN); //on génère tout les mouvements possibles

	for (int i = 3; i >= 0; i--) {
		Mouvement m = mouvementPossibles[i]; //On récupère le mouvement à la position i

		if (m != NONE) {
			Position nouvellePosition = nouvellePositionDepuisMouvement(caseN, m, parent.damierCourant.nbColonnes); //on génère la nouvelle position de la case nulle

			//On initialise un nouveau damier
			Tab2D nouveauDamier;
			nouveauDamier.initialiser(parent.damierCourant.nbLignes, parent.damierCourant.nbColonnes);
			nouveauDamier.copier(parent.damierCourant);

			//On inverse la case nulle entre l'ancienne et la nouvelle position
			Item tmp = nouveauDamier.damier[caseN.x][caseN.y];
			nouveauDamier.damier[caseN.x][caseN.y] = nouveauDamier.damier[nouvellePosition.x][nouvellePosition.y];
			nouveauDamier.damier[nouvellePosition.x][nouvellePosition.y] = tmp;

			//Puis on crée un nouvelle état fils
			Etat nouvelleEtat;
			nouvelleEtat.initialiser(nouveauDamier, m, listeEtatsExplores.longueur() - 1, &parent, etatsbuts);

			//Et on le sauvegarde dans le tableau contenant les états fils
			
			if (!(*this).appartient(nouvelleEtat, listeEtatsExplores) && !(*this).appartient(nouvelleEtat, listeEtatsAExplorer)) {
				listeEtatsAExplorer.inserer(0, nouvelleEtat); //on l'ajoute dans la liste des etats a explorer
			}
		}
	}

}
