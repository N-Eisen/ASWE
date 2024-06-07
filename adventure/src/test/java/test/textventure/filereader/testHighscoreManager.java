package test.textventure.filereader;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import textventure.filereader.HighscoreManager;

public class testHighscoreManager {

	@Mock
	private File fileMock;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddHighscore() {
		String testFilePath = "testfile.txt";
		try {
			FileWriter writer = new FileWriter(testFilePath);
			writer.write("Player1,100\nPlayer2,200\nPlayer3,150\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HighscoreManager highscoreManager = new HighscoreManager(testFilePath);

		highscoreManager.addHighscore("Player4", 180);

		assertEquals("Player4: 180 steps", highscoreManager.getHighscores().get(2).toString());

		File testFile = new File(testFilePath);
		testFile.delete();
	}

	@Test
	public void testGetHighscores() {
		String testFilePath = "testfile.txt";
		try {
			FileWriter writer = new FileWriter(testFilePath);
			writer.write("Player1,100\nPlayer2,200\nPlayer3,150\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HighscoreManager highscoreManager = new HighscoreManager(testFilePath);

		assertEquals(3, highscoreManager.getHighscores().size());

		File testFile = new File(testFilePath);
		testFile.delete();
	}
}
