#include "stdlib.h"
#include "stdio.h"
#include "constants.h"
#include "string.h"
#include "utils.h"
#include "skier.h"
#include "assert.h"
#pragma warning(disable:4996)

void loadSprint() {
	Race race;

	race.countDisqualifiedSkiers = 0;
	race.countRegisteredSkiers = 0;
	race.doors = 0;
	race.isRunning = true;

	while (race.isRunning) {
		String params[COMMAND_MAX_PARAMS_COUNT] = { "", "", "", "" };
		char input[COMMAND_MAX_LENGTH];
		if (fgets(input, COMMAND_MAX_LENGTH, stdin)) {
			sscanf(input, "%s %s %s %s", &params[0], &params[1], &params[2], &params[3]);
			if (strcmp(params[0], "exit") == 0) {
				exitAction(&race);
			}
			else if (strcmp(params[0], "definir_course") == 0) {
				assert(atoi(params[1]) >= DOOR_MIN && atoi(params[1]) <= DOOR_MAX);
				race.doors = atoi(params[1]);
			}
			else if (strcmp(params[0], "inscrire_skieur") == 0) {
				assert(race.countRegisteredSkiers <= SKIER_MAX_COUNT);
				registerSkier(params[1], params[2], &race);
			}
			else if (strcmp(params[0], "afficher_skieurs") == 0) {
				showRegisteredSkiers(&race);
			}
			else if (strcmp(params[0], "enregistrer_temps") == 0) {
				assert((atoi(params[3]) >= SKIER_START_IDENTIFIER) && (atoi(params[3]) <= SKIER_MAX_IDENTIFIER));
				assert(atoi(params[2]) <= race.doors);
				assert(atof(params[1]) >= 0.);
				registerEntry(atof(params[1]), atoi(params[2]), atoi(params[3]), &race);
			}
			else if (strcmp(params[0], "disqualification") == 0) {
				disqualifySkier(atoi(params[1]), atoi(params[2]), &race);
			}
			else if (strcmp(params[0], "afficher_temps") == 0) {
				assert((atoi(params[1]) >= SKIER_START_IDENTIFIER) && (atoi(params[1]) <= SKIER_MAX_IDENTIFIER));
				displayEntries(atoi(params[1]), &race);
			}
			else if (strcmp(params[0], "afficher_classement") == 0) {
				assert(atoi(params[1]) > 0 && atoi(params[1]) <= race.doors + 1);
				displayLeaderbord(atoi(params[1]), &race);
			}
		}
	}
}

/*	
	FILE * sprint;
	errno_t err;
	boolean isReading = true;
	int i = 1;
	err = fopen_s(&sprint, "inSp1.txt", "r");
	if (!err) {
		while (isReading) {
			char input[COMMAND_MAX_LENGTH];
			if (fgets(input, COMMAND_MAX_LENGTH, (FILE*)sprint)) {
				printf("%d : %s", i++, input);

			}
			else { isReading = false; }
		}
	}
	else {
		printf("Le fichier n'existe pas.\n");
	}
	fclose(sprint);
*/

