#include "skier.h"
#include "constants.h"
#include "string.h"

/*
* Fonction permettant d'enregistrer un skieur
*   - nom du skieur
*   - pays du skieur
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void registerSkier(const String name, const String country, Race *race) {
	Skier skier;

	strcpy_s(skier.name, sizeof(String), name);
	strcpy_s(skier.country, sizeof(String), country);
	skier.disqualified = false;
	skier.identifier = race->countRegisteredSkiers + SKIER_START_IDENTIFIER;
	skier.passedDoors = 0;
	skier.entries = malloc(sizeof(Entry) * (race->doors + 1));

	if (skier.entries == NULL){
		fprintf(stderr, "Allocation impossible \n");
		exit(1);
	}

	race->registeredSkiers[race->countRegisteredSkiers] = skier;

	printf("inscription dossard %d\n", skier.identifier);
	(race->countRegisteredSkiers)++;
}

/*
* Fonction permettant d'afficher tous les skieurs enregistrés
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void showRegisteredSkiers(Race *race) {
	for (int i = 0; i < race->countRegisteredSkiers; i++) {
		Skier *skier = &race->registeredSkiers[i];
		printf("%s %s %d\n", skier->name, skier->country, skier->identifier);
	}
}

/*
* Fonction permettant d'enregistrer un temps à un skieur
*   - temps chronométré
*   - numéro de la porte
*   - dossard du skieur disqualifié
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void registerEntry(const double time, const int door, const int identifier, Race *race) {
	Skier *skier = &race->registeredSkiers[identifier - SKIER_START_IDENTIFIER];
	Entry entry = { identifier , time};
	skier->entries[skier->passedDoors] = entry;
	(skier->passedDoors)++;
	if (detectEndGame(race)) {
		printf("detection_fin_course\n");
		displayLeaderbord(race->doors, race);
		displayDisqualifiedSkiers(race);
		exitAction(race);
	}
}

/*
* Fonction permettant d'afficher tous les temps enregistrés pour un skieur
*   - dossard du skieur
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void displayEntries(const int identifier, Race *race) {
	Skier *skier = &race->registeredSkiers[identifier - SKIER_START_IDENTIFIER];
	for (int i = 0; i < skier->passedDoors; i++) {
		Entry entry = skier->entries[i];
		if (entry.time == -1.) {
			printf("%d %d disqualification\n", i, entry.identifier);
		}
		else {
			printf("%d %d %.2f\n", i, entry.identifier, entry.time);
		}
	}
}

/*
* Fonction permettant de disqualifier un skieur
*   - numéro de la porte non passée
*   - dossard du skieur disqualifié
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void disqualifySkier(const int door, const int identifier, Race *race) {
	Skier *skier = &race->registeredSkiers[identifier - SKIER_START_IDENTIFIER];
	skier->disqualified = true;

	Entry entry = {identifier, -1.};
	skier->entries[skier->passedDoors] = entry;
	(skier->passedDoors)++;
	(race->countDisqualifiedSkiers)++;
}

/*
* Fonction permettant de classer les skieurs non disqualifiés entre eux
*	- numéro de la porte pour laquelle on veut afficher le classement
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void displayLeaderbord(const int door, Race *race) {
	int nbEntries = 0;
	Entry *entries;
	entries = malloc(sizeof(Entry) * (race->doors * (race->countRegisteredSkiers - race->countDisqualifiedSkiers)));

	for (int i = 0; i < race->countRegisteredSkiers; i++) {
		Skier *skier = &race->registeredSkiers[i];
		if ((!skier->disqualified && skier->passedDoors >= door) || (skier->disqualified && (skier->passedDoors - 1) >= door)) {
			Entry entry = skier->entries[door];
			entry.time -= skier->entries[0].time;
			entries[nbEntries++] = entry;
		}
	}

	for (int j = 0; j < nbEntries; j++) {
		for (int k = 0; k < (nbEntries - 1); k++) {
			if (entries[j].time < entries[k].time) {
				Entry tmp = entries[j];
				entries[j] = entries[k];
				entries[k] = tmp;
			}
		}
	}

	for (int l = 0; l < nbEntries; l++) {
		Entry entry = entries[l];
		if (entry.time > 0) {
			Skier *skier = &race->registeredSkiers[entry.identifier - SKIER_START_IDENTIFIER];
			printf("%d %d %s %s %.2f\n", door, entry.identifier, skier->name, skier->country, entry.time);
		}
	}

	free(entries);
}

/*
* Fonction permettant de classer les skieurs disqualifiés entre eux
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*/
void displayDisqualifiedSkiers(Race *race) {
	int nbEntries = 0;
	Entry *entries;
	entries = malloc(sizeof(Entry) * race->countDisqualifiedSkiers);

	for (int i = 0; i < race->countRegisteredSkiers; i++) {
		Skier *skier = &race->registeredSkiers[i];
		if (skier->disqualified) {
			Entry entry = skier->entries[skier->passedDoors - 2];
			entry.time -= skier->entries[0].time;
			entries[nbEntries++] = entry;
		}
	}

	for (int j = 0; j < nbEntries; j++) {
		for (int k = 0; k < (nbEntries - 1); k++) {
			if (entries[j].time < entries[k].time) {
				Entry tmp = entries[j];
				entries[j] = entries[k];
				entries[k] = tmp;
			}
		}
	}

	for (int j = 0; j < nbEntries; j++) {
		for (int k = 0; k < (nbEntries - 1); k++) {
			Skier *skier1 = &race->registeredSkiers[entries[j].identifier - SKIER_START_IDENTIFIER];
			Skier *skier2 = &race->registeredSkiers[entries[k].identifier - SKIER_START_IDENTIFIER];

			if (skier1->passedDoors < skier2->passedDoors) {
				Entry tmp = entries[j];
				entries[j] = entries[k];
				entries[k] = tmp;
			}
		}
	}

	for (int l = 0; l < nbEntries; l++) {
		Entry entry = entries[l];
		if (entry.time > 0) {
			Skier *skier = &race->registeredSkiers[entry.identifier - SKIER_START_IDENTIFIER];
			printf("disqualifie %d %s %s\n", entry.identifier, skier->name, skier->country);
		}
	}

	free(entries);
}

/* Permet de détecter la fin ou non de la course
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint)
*  Retourne : 
*   - une variable de type Boolean
*/
Boolean detectEndGame(Race *race) {
	int nbEntries = 0;

	for (int i = 0; i < race->countRegisteredSkiers; i++) {
		Skier *skier = &race->registeredSkiers[i];
		if (!skier->disqualified) {
			nbEntries += skier->passedDoors;
		}
	}

	return (nbEntries >= ((race->countRegisteredSkiers - race->countDisqualifiedSkiers) * (race->doors + 1)));
}

/*
* Fonction permettant d'arreter le programme et de libérer la mémoire des tableaux qui ont été crées dynamiquement.
*	- pointeur vers la structure associée à la course (définie dans le fichier principal du sprint) 
*/
void exitAction(Race *race) {
	race->isRunning = false;
	for (int i = 0; i < race->countRegisteredSkiers; i++) {
		Skier *skier = &race->registeredSkiers[i];
		free(skier->entries);
	}
}