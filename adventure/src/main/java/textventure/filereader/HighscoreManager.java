package textventure.filereader;

import java.io.*;
import java.util.*;

public class HighscoreManager {
	private static final String DEFAULT_HIGHSCORE_FILE = "Highscores.txt";
	private static final int MAX_HIGHSCORES = 10;
	private File highscoreFile = new File(DEFAULT_HIGHSCORE_FILE);
	private List<Highscore> highscores = new ArrayList<>();

	public HighscoreManager(String pfad) {
		this.highscoreFile = new File(pfad);
		loadHighscores();
	}

	public HighscoreManager() {
		loadHighscores();
	}

	public void addHighscore(String playerName, int steps) {
		Highscore newScore = new Highscore(playerName, steps);
		highscores.add(newScore);
		Collections.sort(highscores);
		if (highscores.size() > MAX_HIGHSCORES) {
			highscores = highscores.subList(0, MAX_HIGHSCORES);
		}
		saveHighscores();
	}

	public List<Highscore> getHighscores() {
		return highscores;
	}

	private void loadHighscores() {
		try (BufferedReader reader = new BufferedReader(new FileReader(highscoreFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 2) {
					String playerName = parts[0];
					int steps = Integer.parseInt(parts[1]);
					highscores.add(new Highscore(playerName, steps));
				}
			}
			Collections.sort(highscores);
		} catch (IOException e) {
			System.out.println("Error reading highscore file: " + e.getMessage());
		}
	}

	private void saveHighscores() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(highscoreFile))) {
			for (Highscore highscore : highscores) {
				writer.write(highscore.getPlayerName() + "," + highscore.getSteps());
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error writing highscore file: " + e.getMessage());
		}
	}

	public static class Highscore implements Comparable<Highscore> {
		private final String playerName;
		private final int steps;

		public Highscore(String playerName, int steps) {
			this.playerName = playerName;
			this.steps = steps;
		}

		public String getPlayerName() {
			return playerName;
		}

		public int getSteps() {
			return steps;
		}

		@Override
		public int compareTo(Highscore other) {
			return Integer.compare(this.steps, other.steps);
		}

		@Override
		public String toString() {
			return playerName + ": " + steps + " steps";
		}
	}
}
