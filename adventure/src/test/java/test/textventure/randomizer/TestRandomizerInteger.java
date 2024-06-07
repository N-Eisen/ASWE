package test.textventure.randomizer;

import static org.junit.Assert.*;
import org.junit.Test;
import textventure.randomizer.Randomizer;

public class TestRandomizerInteger {
	@Test
	public void testGetRandomIntFromRange() {
		Randomizer randomizer = new Randomizer();
		int min = 1;
		int max = 10;

		for (int i = 0; i < 100; i++) {
			int result = randomizer.getRandomIntFromRange(min, max);
			assertTrue(result >= min && result <= max);
		}
	}

	@Test
	public void testGetRandomIntFromRangeWithSameMinAndMax() {
		Randomizer randomizer = new Randomizer();
		int min = 5;
		int max = 5;

		int result = randomizer.getRandomIntFromRange(min, max);
		assertEquals(min, result);
	}

	@Test
	public void testGetRandomIntFromRangeWithNegativeRange() {
		Randomizer randomizer = new Randomizer();
		int min = -10;
		int max = -1;

		for (int i = 0; i < 100; i++) {
			int result = randomizer.getRandomIntFromRange(min, max);
			assertTrue(result >= min && result <= max);
		}
	}

	@Test
	public void testGetRandomIntFromRangeWithMinGreaterThanMax() {
		Randomizer randomizer = new Randomizer();
		int min = 10;
		int max = 5;

		assertThrows(IllegalArgumentException.class, () -> {
			randomizer.getRandomIntFromRange(min, max);
		});
	}

	@Test
	public void testGetRandomBoolean50() {
		Randomizer randomizer = new Randomizer();
		int percentTrue = 50;
		int trues = 0;
		int falses = 0;
		// Run the test multiple times to cover the randomness
		for (int i = 0; i < 1000; i++) {
			boolean result = randomizer.getRandomBoolean(percentTrue);
			if (result) {
				trues++;
			}else {
				falses++;
			}
		}
		assertTrue(Math.abs(trues-falses)<150);
	}
	
	@Test
	public void testGetRandomBoolean99() {
		Randomizer randomizer = new Randomizer();
		int percentTrue = 99;
		int trues = 0;
		int falses = 0;
		// Run the test multiple times to cover the randomness
		for (int i = 0; i < 1000; i++) {
			boolean result = randomizer.getRandomBoolean(percentTrue);
			if (result) {
				trues++;
			}else {
				falses++;
			}
		}
		assertTrue(Math.abs(trues-falses)>900);
	}
}
