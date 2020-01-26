package fr.sokchalons.crazycircus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import fr.sokchalons.crazycircus.entities.EnumAnimal;
import fr.sokchalons.crazycircus.entities.Player;
import fr.sokchalons.crazycircus.party.EnumMovement;
import fr.sokchalons.crazycircus.party.Round;
import fr.sokchalons.crazycircus.structure.Part;
import fr.sokchalons.crazycircus.structure.Podium;
import fr.sokchalons.crazycircus.utils.CustomStringBuilder;
import fr.sokchalons.crazycircus.utils.EnumDifficulty;

/**
 * La classe Jeu.
 */
public class Game {

	/**  Tableau contenant tous les joueurs inscrits. */
	private List<Player> players;
	
	/**  Tableau contenant tous les tours joués. */
	protected List<Round> rounds;

	/**  Tableau contenant toutes les situations du jeu. */
	private List<Part> parts;
	
	/**  Difficulté du jeu. */
	private EnumDifficulty difficulty;

	/**
	 * Constructeur de la classe Jeu, permet d'instancier un nouveau jeu.
	 *
	 * @throws JsonSyntaxException : si la structure du fichier de solution n'est pas bonne
	 * @throws JsonIOException : s'il y a un problème avec le flux de donnée
	 * @throws FileNotFoundException : si le fichier n'est pas trouvé
	 */
	public Game() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		this.players = new ArrayList<Player>();
		this.rounds = new ArrayList<Round>();
		this.parts = new ArrayList<Part>();

		Gson gson = new GsonBuilder().create();
		Part[] parts = gson.fromJson(new BufferedReader(new FileReader("solutions.json")), Part[].class);

		for (Part p : parts) {
			this.parts.add(p);
		}
	}

	/**
	 * Ajouter un joueur au jeu.
	 *
	 * @param p : le joueur
	 */
	public void addPlayer(Player p) {
		if (!this.players.contains(p)) {
			this.players.add(p);
		}
	}

	/**
	 * Démarrer un nouveau tour.
	 */
	public void newRound() {
		Round round = new Round();
		
		if(this.parts.size() == 2) {
			round.setStart(this.parts.get(0));
			round.setEnd(this.parts.get(1));
			
			this.parts.clear();
			this.rounds.add(round);
			
			return;
		}
		
		Random r = new Random();

		int p1 = 0;
		int p2 = 0;

		do {
			p1 = r.nextInt(this.parts.size() - 1);
			p2 = r.nextInt(this.parts.size() - 1);
		} while (p1 == p2);

		round.setStart(this.parts.get(p1));
		round.setEnd(this.parts.get(p2));

		this.parts.remove(p2);
		this.parts.remove(p1);

		this.rounds.add(round);
	}

	/**
	 * Récupérer le tour actuel.
	 *
	 * @return tour actuel
	 */
	public Round getActualRound() {
		return this.rounds.get(this.rounds.size() - 1);
	}

	/**
	 * Retourne sous forme d'une chaine de caractère le tour actuel.
	 *
	 * @return the string
	 */
	public String toString() {
		List<Podium> podiums = new ArrayList<Podium>();
		podiums.addAll(this.getActualRound().getStart().getPodiums());
		podiums.addAll(this.getActualRound().getEnd().getPodiums());

		StringBuilder mainSb = new StringBuilder();

		List<List<String>> elementsByLines = new ArrayList<List<String>>();

		for (int i = 0; i < 3; i++) {
			elementsByLines.add(new ArrayList<String>());
		}

		for (Podium p : podiums) {
			int maxLenght = 0;

			for (EnumAnimal a : p.getAnimals()) {
				if(a != null)
					if (maxLenght < a.length()) {
						maxLenght = a.length();
					}
			}

			int i = 0;

			for (int u = 0; u < 3; u++) {
				StringBuilder sb = new StringBuilder();
				if (p.getAnimals().size() < (u + 1)) {
					for (int x = 0; x < EnumAnimal.getMaxLength(); x++) {
						sb.append(" ");
					}
				} else {
					EnumAnimal a = p.getAnimals().get(u);
					int nb = a.length();
					nb = (maxLenght - nb);
					nb = nb / 2;

					if (maxLenght == 4) {
						nb = 2;
					}

					for (int x = 0; x < nb; x++) {
						sb.append(" ");
					}

					sb.append(a);

					for (int x = 0; x < nb; x++) {
						sb.append(" ");
					}
				}
				elementsByLines.get(i).add(sb.toString());
				i++;
			}
		}

		for (int i = elementsByLines.size() - 1; i >= 0; i--) {
			mainSb.append(
					new CustomStringBuilder().appendFormat("%s  %s       %s  %s\n", elementsByLines.get(i).toArray()));
		}

		mainSb.append("  ----      ----    ==>    ----      ----\n");
		mainSb.append(new CustomStringBuilder().appendFormat("  %s      %s          %s      %s\n\n",
				podiums.get(0).getColor(), podiums.get(1).getColor(), podiums.get(2).getColor(),
				podiums.get(3).getColor()));

		mainSb.append(EnumMovement.showHelp());

		return mainSb.toString();
	}

	/**
	 * Analyser la commande.
	 *
	 * @param command : la commande
	 * @return le code de l'analyse
	 */
	public int parseCommand(String command) {
		String[] tmp = command.split(" ");

		if (tmp.length != 2) {
			// Commande inconnue
			return 1;
		}

		String id = tmp[0];
		String mvs = tmp[1];

		boolean find = false;
		Player player = null;

		for (Player p : this.players) {
			if (p.getId().equalsIgnoreCase(id)) {
				find = true;
				player = p;
				break;
			}
		}

		if (!find) {
			// Identifiant inconnu
			return 2;
		}

		if (!this.getActualRound().canPlay(player)) {
			// Vous ne pouvez plus joueur ce tour !
			return 3;
		}

		List<EnumMovement> mouvements = EnumMovement.parseCommand(mvs);

		if (mouvements == null) {
			// Séquence de mouvement incorrecte
			return 4;
		}
		
		if(this.difficulty == EnumDifficulty.HARD && mouvements.contains(EnumMovement.SO)) {
			return 8;
		}

		boolean status = this.getActualRound().isAGoodSequence(mouvements, this);

		if (status) {
			// Vous avez gagné ce tour !
			this.getActualRound().setWinner(player);
			return 5;
		} else {
			this.getActualRound().addError(player);

			// Vous avez rentré une mauvaise séquence, vous ne pouvez plus joueur durant ce tour
			
			if (this.getActualRound().countErrors() == (this.players.size() - 1) || this.players.size() <= 2) {
				Player last = this.getActualRound().getLast(this.players);

				// C'est [] qui remporte ce tour !
				this.getActualRound().setWinner(last);
				return 7;
			} else {
				return 6;
			}
		}
	}

	/**
	 * Récupérer le classement.
	 *
	 * @return le classement
	 */
	public List<Player> getScoreboard() {
		List<Player> ordonnedPlayers = this.players;

		Collections.sort(ordonnedPlayers, new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				return p2.getScore() - p1.getScore();
			}
		});

		return ordonnedPlayers;
	}

	/**
	 * Déterminer si le jeu est fini.
	 *
	 * @return true, si il l'est
	 */
	public boolean isFinish() {
		return (this.parts.isEmpty());
	}

	/**
	 * Récupérer le niveau de difficulté du jeu.
	 *
	 * @return la difficulté
	 */
	public EnumDifficulty getDifficulty() {
		return this.difficulty;
	}

	/**
	 * Récupérer le nombre de tours joués.
	 *
	 * @return le nombre de tours joués
	 */
	public int countRounds() {
		return this.rounds.size();
	}
	
	
	/**
	 * Définir le niveau de difficulté du jeu
	 *
	 * @param difficulty : la difficulté du jeu
	 */
	public void setDifficulty(EnumDifficulty difficulty) {
		this.difficulty = difficulty;
	}
}
