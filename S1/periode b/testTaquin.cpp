#include <iostream>
#include "Taquin.h"

using namespace std;

int main() {
	Taquin t;
	t.initialiser();

	t.jouer();

	t.listeEtatsAExplorer.detruire();
	t.listeEtatsExplores.detruire();
	delete[] t.etatsbuts;

	//system("pause");

	return 0;
}