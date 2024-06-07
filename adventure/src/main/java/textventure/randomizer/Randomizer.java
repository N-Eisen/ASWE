package textventure.randomizer;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

	public int getRandomIntFromRange(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException();
		}
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public boolean getRandomBoolean(int percentTrue) {
		int randInt = getRandomIntFromRange(0, 100);
		return randInt <= percentTrue;
	}
}
