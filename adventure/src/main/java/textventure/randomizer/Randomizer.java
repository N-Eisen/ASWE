package textventure.randomizer;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

	public int GetRandomIntFromRange(int min, int max) {
		if (min>max) {
			throw new IllegalArgumentException();
		}
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public boolean GetRandomBoolean(int percentTrue) {
		int randInt = GetRandomIntFromRange(0, 100);
		if (randInt<=percentTrue) {
			return true;
		}
		return false;
	}
}
