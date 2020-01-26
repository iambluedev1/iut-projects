package fr.sokchalons.crazycircus.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import fr.sokchalons.crazycircus.entities.EnumAnimal;
import fr.sokchalons.crazycircus.structure.Podium;

/**
 * La classe PodiumTest pour tester la classe Podium
 */
class PodiumTest {

	/**
	 * Test pour vérifier l'ajout d'un animal à un podium
	 */
	@Test
	void testAddAnimal() {
		Podium p = new Podium("BLEU");
		
		p.addAnimal(EnumAnimal.OURS);
		
		assertTrue(p.getAnimals().size() == 1);
		assertTrue(p.getAnimals().contains(EnumAnimal.OURS));
	}

	/**
	 * Test pour vérifier que le podium retourne bien le bon dernier animal
	 */
	@Test
	void testGetLast() {
		Podium p = new Podium("BLEU");
		
		p.addAnimal(EnumAnimal.OURS);
		p.addAnimal(EnumAnimal.ELEPHANT);
		p.addAnimal(EnumAnimal.LION);
		
		assertFalse(p.getLast() == EnumAnimal.ELEPHANT);
		assertTrue(p.getLast() == EnumAnimal.LION);
	}

	/**
	 * Test pour vérifier que le podium retourne bien le bon premier animal
	 */
	@Test
	void testGetFirst() {
		Podium p = new Podium("BLEU");
		
		p.addAnimal(EnumAnimal.OURS);
		p.addAnimal(EnumAnimal.ELEPHANT);
		p.addAnimal(EnumAnimal.LION);
		
		assertFalse(p.getFirst() == EnumAnimal.ELEPHANT);
		assertTrue(p.getFirst() == EnumAnimal.OURS);
	}

	/**
	 * Test pour vérifier le bon fonctionnement de l'inversion du podium
	 */
	@Test
	void testInverse() {
		Podium p = new Podium("BLEU");
		
		p.addAnimal(EnumAnimal.OURS);
		p.addAnimal(EnumAnimal.ELEPHANT);
		p.addAnimal(EnumAnimal.LION);
		
		assertTrue(p.getFirst() == EnumAnimal.OURS);
		assertTrue(p.getLast() == EnumAnimal.LION);
		
		p.inverse();
		
		assertFalse(p.getFirst() == EnumAnimal.OURS);
		assertFalse(p.getLast() == EnumAnimal.LION);
		
		assertTrue(p.getFirst() == EnumAnimal.LION);
		assertTrue(p.getLast() == EnumAnimal.OURS);
	}

	/**
	 * Test pour vérifier le bon fonctionnement de la supprésion d'un animal du podium
	 */
	@Test
	void testRemove() {
		Podium p = new Podium("BLEU");
		
		p.addAnimal(EnumAnimal.OURS);
		p.addAnimal(EnumAnimal.ELEPHANT);
		p.addAnimal(EnumAnimal.LION);
		
		p.remove(EnumAnimal.OURS);
		assertTrue(p.getAnimals().size() == 2);
		assertFalse(p.getAnimals().contains(EnumAnimal.OURS));
	}

	/**
	 * Test pour vérifier la couleur associée à un podium et correctement retournée
	 */
	@Test
	void testGetColor() {
		Podium p1 = new Podium("BLEU");
		Podium p2 = new Podium("ROUGE");
		
		assertFalse(p1.equals(p2));
		assertTrue(p1.getColor().equals("BLEU"));
		assertTrue(p2.getColor().equals("ROUGE"));
	}

}
