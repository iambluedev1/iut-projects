package fr.sokchalons.crazycircus.test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import fr.sokchalons.crazycircus.entities.EnumAnimal;
import fr.sokchalons.crazycircus.utils.CustomStringBuilder;

/**
 * La classe CustomStringBuilderTest pour tester la classe CustomStringBuilder
 */
class CustomStringBuilderTest {

	/**
	 * Test pour vérifier que le formattage fonctionne correctement
	 */
	@Test
	void testFormatter() {
		String f = "LION ELEPHANT OURS";
		String t = new CustomStringBuilder().appendFormat("%s %s %s", EnumAnimal.LION.toString(), EnumAnimal.ELEPHANT.toString(), EnumAnimal.OURS.toString());
		
		assertTrue(f.equals(t));
	}
}
