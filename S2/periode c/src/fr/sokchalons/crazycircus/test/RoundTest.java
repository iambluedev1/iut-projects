package fr.sokchalons.crazycircus.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import fr.sokchalons.crazycircus.Game;
import fr.sokchalons.crazycircus.entities.EnumAnimal;
import fr.sokchalons.crazycircus.entities.Player;
import fr.sokchalons.crazycircus.party.EnumMovement;
import fr.sokchalons.crazycircus.party.Round;
import fr.sokchalons.crazycircus.structure.Part;
import fr.sokchalons.crazycircus.structure.Podium;

/**
 * La classe RoundTest pour tester la classe Round
 */
class RoundTest {

	/**
	 * Classe custom permettant de définir manuellement le tour (n'est pas possible par défaut)
	 */
	private class CustomGame extends Game {
		
		/**
		 * Constructeur de la classe
		 *
		 * @throws JsonSyntaxException : si la structure du fichier de solution n'est pas bonne
		 * @throws JsonIOException : s'il y a un problème avec le flux de donnée
		 * @throws FileNotFoundException : si le fichier n'est pas trouvé
		 */
		public CustomGame() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
			super();
		}
		
		/**
		 * Lancer un nouveau tour en spécifiant le tour en question
		 *
		 * @param round : le tour
		 */
		public void newRound(Round round) {
			this.rounds.add(round);
		}
	}
	
	/**
	 * Test pour vérifier le bon fonctionnement d'un tour
	 *
	 * @throws JsonSyntaxException : si la structure du fichier de solution n'est pas bonne
	 * @throws JsonIOException : s'il y a un problème avec le flux de donnée
	 * @throws FileNotFoundException : si le fichier n'est pas trouvé
	 */
	@Test
	void test() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Game game = new Game();
		game.newRound();
		
		assertTrue(game.getActualRound().countErrors() == 0);
		assertNull(game.getActualRound().getWinner());
		
		Player p1 = new Player("Jean");
		Player p2 = new Player("Louis");
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		
		game.getActualRound().addError(p2);
		assertTrue(game.getActualRound().countErrors() == 1);
		
		assertFalse(game.getActualRound().canPlay(p2));
		assertTrue(game.getActualRound().canPlay(p1));
		
		assertTrue(game.getActualRound().getLast(Arrays.asList(p1, p2)) == p1);
		
		game.getActualRound().setWinner(p1);
		assertTrue(game.getActualRound().getWinner() == p1);
	}

	/**
	 * Test pour vérifier la bonne détection de la solution du tour
	 *
	 * @throws JsonSyntaxException : si la structure du fichier de solution n'est pas bonne
	 * @throws JsonIOException : s'il y a un problème avec le flux de donnée
	 * @throws FileNotFoundException : si le fichier n'est pas trouvé
	 */
	@Test
	void testSolving() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		CustomGame game = new CustomGame();
		Round round = new Round();
		
		Podium p1 = new Podium("Bleu");
		p1.addAnimal(EnumAnimal.LION);
		
		Podium p2 = new Podium("Rouge");
		p2.addAnimal(EnumAnimal.ELEPHANT);
		p2.addAnimal(EnumAnimal.OURS);
		
		Podium p3 = new Podium("Bleu");
		
		Podium p4 = new Podium("Rouge");
		p4.addAnimal(EnumAnimal.OURS);
		p4.addAnimal(EnumAnimal.ELEPHANT);
		p4.addAnimal(EnumAnimal.LION);
		
		Part p1p2 = new Part(p1, p2);
		Part p3p4 = new Part(p3, p4);
		
		round.setStart(p1p2);
		round.setEnd(p3p4);
		
		game.newRound(round);
		
		assertTrue(round.isAGoodSequence(EnumMovement.parseCommand("MAKI"), game));
	}
}
