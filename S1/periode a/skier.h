#include "utils.h"

#ifndef __SKIER_H__
#define __SKIER_H__


typedef struct {
	int identifier;
	double time;
} Entry;

typedef struct {
	String name;
	String country;
	int identifier;
	int passedDoors;
	Boolean disqualified;

	Entry *entries;
} Skier;

typedef struct {
	int countRegisteredSkiers;
	int countDisqualifiedSkiers;
	int doors;
	Boolean isRunning;

	Skier registeredSkiers[50];
} Race;

/*
* Fonction permettant d'enregistrer un skieur
*   - nom du skieur
*   - pays du skieur
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void registerSkier(const String name, const String country, Race *race);

/*
* Fonction permettant d'afficher tous les skieurs enregistrés
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void showRegisteredSkiers(Race *race);

/*
* Fonction permettant d'enregistrer un temps à un skieur
*   - temps chronométré
*   - numéro de la porte
*   - dossard du skieur disqualifié
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void registerEntry(const double time, const int door, const int identifier, Race *race);

/*
* Fonction permettant d'afficher tous les temps enregistrés pour un skieur
*   - dossard du skieur
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void displayEntries(const int identifier, Race *race);

/*
* Fonction permettant de disqualifier un skieur
*   - numéro de la porte non passée
*   - dossard du skieur disqualifié
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void disqualifySkier(const int door, const int identifier, Race *race);

/*
* Fonction permettant de classer les skieurs non disqualifiés entre eux
*	- numéro de la porte pour laquelle on veut afficher le classement
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void displayLeaderbord(const int door, Race *race);

/*
* Fonction permettant de classer les skieurs disqualifiés entre eux
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void displayDisqualifiedSkiers(Race *race);

/* Permet de détecter la fin ou non de la course
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*  Retourne :
*   - une variable de type Boolean
*/
Boolean detectEndGame(Race *race);

/*
* Fonction permettant d'arreter le programme et de libérer la mémoire des tableaux qui ont été crées dynamiquement.
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void exitAction(Race *race);

#endif