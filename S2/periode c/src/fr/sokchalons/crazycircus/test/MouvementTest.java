package fr.sokchalons.crazycircus.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import fr.sokchalons.crazycircus.party.EnumMovement;

/**
 * La classe MouvementTest pour tester la classe Mouvement
 */
class MouvementTest {

	/**
	 * Test pour vérifier l'analyse des commandes
	 */
	@Test
	void testParseCommand() {
		List<EnumMovement> mvs = EnumMovement.parseCommand("KIMALO");
		assertTrue(mvs.contains(EnumMovement.KI));
		assertTrue(mvs.contains(EnumMovement.MA));
		assertTrue(mvs.contains(EnumMovement.LO));
		
		assertTrue(EnumMovement.parseCommand("KIMAL") == null);
		assertTrue(EnumMovement.parseCommand("KO") == null);
	}

	/**
	 * Test pour vérifier le mouvement effectué
	 */
	@Test
	void testGetMouv() {
		assertTrue(EnumMovement.KI.getMovement().equals("BLEU --> ROUGE"));
		assertTrue(EnumMovement.MA.getMovement().equals("ROUGE ^"));
	}

}
