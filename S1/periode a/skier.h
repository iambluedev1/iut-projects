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
*	- pointeur vers la structure associ�e � la course (d�finie dans le fichier principal du sprint)
*/
void registerSkier(const String name, const String country, Race *race);

/*
* Fonction permettant d'afficher tous les skieurs enregistr�s
*	- pointeur vers la structure associ�e � la course (d�finie dans le fichier principal du sprint)
*/
void showRegisteredSkiers(Race *race);

/*
* Fonction permettant d'enregistrer un temps � un skieur
*   - temps chronom�tr�
*   - num�ro de la porte
*   - dossard du skieur disqualifi�
*	- pointeur vers la structure associ�e � la course (d�finie dans le fichier principal du sprint)
*/
void registerEntry(const double time, const int door, const int identifier, Race *race);

/*
* Fonction permettant d'afficher tous les temps enregistr�s pour un skieur
*   - dossard du skieur
*	- pointeur vers la structure associ�e � la course (d�finie dans le fichier principal du sprint)
*/
void displayEntries(const int identifier, Race *race);

/*
* Fonction permettant de disqualifier un skieur
*   - num�ro de la porte non pass�e
*   - dossard du skieur disqualifi�
*	- pointeur vers la structure associ�e � la course (d�finie dans le fichier principal du sprint)
*/
void disqualifySkier(const int door, const int identifier, Race *race);

/*
* Fonction permettant de classer les skieurs non disqualifi�s entre eux
*	- num�ro de la porte pour laquelle on veut afficher le classement
*	- pointeur vers la structure associ�e � la course (d�finie dans le fichier principal du sprint)
*/
void displayLeaderbord(const int door, Race *race);

/*
* Fonction permettant de classer les skieurs disqualifi�s entre eux
*	- pointeur vers la structure associ�e � la course (d�finie dans le fichier principal du sprint)
*/
void displayDisqualifiedSkiers(Race *race);

/* Permet de d�tecter la fin ou non de la course
*	- pointeur vers la structure associ�e � la course (d�finie dans le fichier principal du sprint)
*  Retourne :
*   - une variable de type Boolean
*/
Boolean detectEndGame(Race *race);

/*
* Fonction permettant d'arreter le programme et de lib�rer la m�moire des tableaux qui ont �t� cr�es dynamiquement.
*	- pointeur vers la structure associ�e � la course (d�finie dans le fichier principal du sprint)
*/
void exitAction(Race *race);

#endif