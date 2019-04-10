package fr.sokchalons.crazycircus.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.sokchalons.crazycircus.entities.EnumAnimal;

/**
 * La classe AnimalTest pour tester la classe Animal
 */
class AnimalTest {

	/**
	 * Test pour vérifier la bonne longueur des noms des animaux
	 */
	@Test
	void testLength() {
		assertTrue(EnumAnimal.ELEPHANT.length() == 8);
		assertTrue(EnumAnimal.LION.length() == 4);
		assertTrue(EnumAnimal.OURS.length() == 4);
	}

	/**
	 * Test pour vérifier la bonne longueur du plus long nom entre tous les animaux
	 */
	@Test
	void testGetMaxLength() {
		assertTrue(EnumAnimal.getMaxLength() == EnumAnimal.ELEPHANT.length());
	}

}
