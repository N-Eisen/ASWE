package test.textventure.controller;

import org.mockito.Mock;

import textventure.controllers.ConsoleController;
import textventure.controllers.GameController;
import textventure.filereader.HighscoreManager;
import textventure.map.Map;
import textventure.player.Player;
import textventure.randomizer.Randomizer;

public class testGameController {
	@Mock
	private ConsoleController consoleController;

	@Mock
	private Player player;

	@Mock
	private Map map;

	@Mock
	private HighscoreManager highscoreManager;

	@Mock
	private Randomizer randomizer;

	private GameController gameController;

	

	
}
