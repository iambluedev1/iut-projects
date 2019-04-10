#include <iostream>
#include <cassert>
#include <iomanip>
#include "Tableau2D.h"

using namespace std;

void Tab2D::initialiser(unsigned int nbl, unsigned int nbc) {
	Item **tab = new Item*[nbl];

	for (int i = 0; i < nbl; ++i) {
		Item *tabTmp = new Item[nbc];
		tab[i] = tabTmp;
	}

	damier = tab;
	nbLignes = nbl;
	nbColonnes = nbc;
}

void Tab2D::detruire() {
	delete[] damier;
	nbColonnes = NULL;
	nbLignes = NULL;
}

void Tab2D::afficher() {
	for (int i = 0; i < nbLignes; i++) {
		for (int x = 0; x < nbColonnes; x++) {
			Item z = damier[i][x];
			if (z == 0) {
				cout << "  #";
			}
			else {
				if (z >= 10) {
					cout << " " << z;
				}
				else {
					cout << "  " << z;
				}
			}
		}
		cout << endl;
	}
}

void Tab2D::copier(const Tab2D & tab)
{
	for (int i = 0; i < nbLignes; i++) {
		for (int j = 0; j < nbColonnes; j++) {
			damier[i][j] = tab.damier[i][j];
		}
	}
}

void Tab2D::ordonner()
{
	int nb = 0, total = nbColonnes * nbLignes;
	Item *chiffres = new Item[total];

	//On sauvegarde les chiffres constituant le damier
	for (int i = 0; i < nbLignes; i++) {
		for (int j = 0; j < nbColonnes; j++) {
			chiffres[nb++] = damier[i][j];
		}
	}

	//On ordonne dans l'odre croissant la liste contenant les chiffres
	for (int i = 0; i < total; i++) {
		for (int j = 0; j < total - 1; j++) {
			if (chiffres[i] < chiffres[j]) {
				Item tmp = chiffres[i];
				chiffres[i] = chiffres[j];
				chiffres[j] = tmp;
			}
		}
	}

	//On décale de 1 vers la gauche le tableau contenant les chiffres ordonnés
	for (int i = 0; i < total; i++) {
		chiffres[i] = chiffres[i + 1];
	}

	//Le dernier chiffre de la liste doit être celui correspondant au #
	chiffres[total - 1] = 0;

	nb = 0;

	//On recrée le damier avec les chiffres ordonnées
	for (int i = 0; i < nbLignes; i++) {
		for (int j = 0; j < nbColonnes; j++) {
			damier[i][j] = chiffres[nb++];
		}
	}
	
	//On oublie pas de supprimer le tableau de chiffres ordonnés
	delete[] chiffres;
}

void Tab2D::lire() {
	int totalChiffres = nbColonnes * nbLignes;
	int compteurChiffres = 1;
	int chiffre = 0;
	int ligne = 0;

	//Tant qu'il reste des chiffres à donner
	while (compteurChiffres <= totalChiffres) {
		char *tmp = new char[totalChiffres];
		cin >> tmp;

		unsigned int t;

		//Si le caractère qui est rentré par la saisie clavier est un #, on indique que ça valeur est 0
		if (strcmp(tmp, "#") == 0) {
			t = 0;
		}
		else { //Sinon on convertit tout simplement le caractère en un entier
			t = atoi(tmp); 
		}

		//et on remplit le damier
		damier[ligne][chiffre++] = t; 
		compteurChiffres++;

		//si on arrive à une fin de ligne, on passe à la suivante
		if (chiffre == nbColonnes) {
			ligne++;
			chiffre = 0;
		}
		delete[] tmp;
	}
}