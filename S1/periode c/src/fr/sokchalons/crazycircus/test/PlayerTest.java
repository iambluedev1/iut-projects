package fr.sokchalons.crazycircus.test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import fr.sokchalons.crazycircus.entities.Player;

/**
 * La classe PlayerTest pour tester la classe Player
 */
class PlayerTest {

	/**
	 * Test pour vérifier la bonne création des joueurs
	 */
	@Test
	void testPlayer() {
		Player p1 = new Player("Jean");
		Player p2 = new Player("John");
		
		assertTrue(p1.getId().equals("Jean"));
		assertTrue(p2.getId().equals("John"));
	}

	/**
	 * Test pour vérifier le bon fonctionnement du système de score
	 */
	@Test
	void testGetScore() {
		Player p1 = new Player("Jean");
		
		assertTrue(p1.getScore() == 0);
		
		for(int i = 0; i < 5; i++) {
			p1.addScore();
		}
		
		assertTrue(p1.getScore() == 5);
	}

}
