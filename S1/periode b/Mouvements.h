#pragma once
#ifndef _MOUVEMENTS_
#define _MOUVEMENTS_

// Enum�ration correspond au 4 mouvements possibles au niveau d'un damier
// La valeur NONE n'est utilis�e qu'� des fins de traitement et pour l'�tat initial
enum Mouvement {
	NONE,
	NORD,
	EST,
	SUD,
	OUEST
};

#endif