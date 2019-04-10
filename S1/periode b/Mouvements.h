#pragma once
#ifndef _MOUVEMENTS_
#define _MOUVEMENTS_

// Enumération correspond au 4 mouvements possibles au niveau d'un damier
// La valeur NONE n'est utilisée qu'à des fins de traitement et pour l'état initial
enum Mouvement {
	NONE,
	NORD,
	EST,
	SUD,
	OUEST
};

#endif