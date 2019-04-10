package fr.sokchalons.crazycircus;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import fr.sokchalons.crazycircus.entities.Player;
import fr.sokchalons.crazycircus.utils.EnumDifficulty;

/**
 * La classe d'entrée du jeu.
 */
public class Main {

	
	/**
	 * La méthode main
	 *
	 * @param args : les identifiants des joueurs
	 */
	public static void main(String[] args) {

		if(args.length < 2) {
			System.err.println("Vous devez indiquer au moins deux identifiants !");
			System.exit(0);
			return;
		}
		
		Game game = null;
		
		try {
			game = new Game();
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			System.err.println("Impossible de charger le jeu !");
			e.printStackTrace();
			System.exit(0);
			return;
		}
		
		Scanner sc = new Scanner(System.in);

		System.out.println("-------- CRAZY CIRCUS --------");
		System.out.println("> " + args.length + " joueurs inscrits !");

		for (String name : args) {
			game.addPlayer(new Player(name));
		}

		System.out.print("Quelle mode de jeu voulez-vous ? (F=Facile, D=Difficile) :");

		boolean difficultySelected = false;

		while (!difficultySelected) {
			String difficulty = sc.next();

			if (difficulty.equalsIgnoreCase("F")) {
				game.setDifficulty(EnumDifficulty.NORMAL);
				difficultySelected = true;
			} else if (difficulty.equalsIgnoreCase("D")) {
				game.setDifficulty(EnumDifficulty.HARD);
				difficultySelected = true;
			} else {
				System.out.println("Le mode que vous avez entré n'est pas reconnu.");
			}
		}

		while (!game.isFinish()) {
			game.newRound();
			System.out.println("\n\n\nTour n°" + (game.countRounds()) + " : A vous de jouer !");

			System.out.println(game.toString());

			while (game.getActualRound().getWinner() == null) {
				System.out.println("");
				String command = sc.nextLine();

				if (!command.isEmpty()) {
					int code = game.parseCommand(command);
					switch(code) {
						case 1:
							System.err.println("> Commande inconnue");
							break;
						case 2:
							System.err.println("> Identifiant '" + command.split(" ")[0] + "' inconnu");
							break;
						case 3:
							System.err.println("> Vous ne pouvez plus joueur ce tour !");
							break;
						case 4:
							System.err.println("> Séquence de mouvement incorrecte");
							break;
						case 5:
							System.out.println("> Vous (" + command.split(" ")[0] + ") avez gagné ce tour !");
							break;
						case 6:
							System.err.println("> Vous (" + command.split(" ")[0] + ") avez rentré une mauvaise séquence, vous ne pouvez plus joueur durant ce tour");
							break;
						case 7:
							System.err.println("> Vous (" + command.split(" ")[0] + ") avez rentré une mauvaise séquence, vous ne pouvez plus joueur durant ce tour");
							System.out.println("> Comme il ne reste plus qu'un joueur c'est " + game.getActualRound().getWinner().getId() + " qui remport ce tour !");
							break;
						case 8:
							System.err.println("En mode difficile vous ne pouvez pas utiliser le mouvement SO !");
							break;
						default:
							break;
					}
				}
			}
		}

		System.out.println("\n\n\n-------- SCORE --------");
		
		List<Player> scoreboard = game.getScoreboard();
		
		for (int i = 0; i < scoreboard.size(); i++) {
			System.out.println(" N°" + (i + 1) + " | " + scoreboard.get(i).getId() + " avec un score de "
					+ scoreboard.get(i).getScore());
		}

		sc.close();
	}
	
}
