package textventure.controllers;

import java.util.List;
import java.util.regex.Pattern;

import textventure.enums.EnemiesEnum;
import textventure.enums.ItemsEnum;
import textventure.events.FightEvent;
import textventure.filereader.HighscoreManager;
import textventure.filereader.HighscoreManager.Highscore;
import textventure.map.BasisFeld;
import textventure.map.EndFeld;
import textventure.map.Map;
import textventure.map.StartFeld;
import textventure.player.Player;
import textventure.randomizer.Randomizer;

public class GameController {
	private ConsoleController consoleController = new ConsoleController();
	private Player player = new Player();
	private Map map = new Map();
	private int delay = 5;
	private int stepCount = 0;
	private int[] playerCoords = map.getStartCoords();
	private boolean gameWon = false;
	private boolean alive = true;
	private Randomizer randomizer = new Randomizer();
	private HighscoreManager highscoreManager = new HighscoreManager();

	public static void main(String[] args) {
		GameController gameController = new GameController();
		gameController.startGame();
	}

	private void startGame() {
		consoleController.displayWelcomeMessage();
		consoleController.displayBorder();
		consoleController.slowPrint("Please enter your name.", delay);
		player.setName(consoleController.readConsole());
		consoleController.displayBorder();
		consoleController.displayControlls();
		consoleController.displayBorder();
		displayHighscore();
		consoleController.displayBorder();
		while (!gameWon && alive) {
			descripeField();
			playerAction();
		}
		consoleController.displayBorder();
		consoleController.slowPrint("Thanks for playing!", delay);
		if (gameWon) {
			highscoreManager.addHighscore(player.getName(), stepCount);
		}
	}

	private void displayHighscore() {
		List<Highscore> highscores = highscoreManager.getHighscores();
		if (highscores.isEmpty()) {
			consoleController.slowPrint("Keine Highscores vorhanden.", delay);
		} else {
			for (Highscore highscore : highscores) {
				consoleController.slowPrint(highscore.toString(), delay);
			}
		}
	}

	private void playerAction() {
		consoleController.slowPrint("What do you want to do?", delay);
		String action = consoleController.readConsole();
		consoleController.displayBorder();
		if (checkIfCommand(action)) {
			action = action.strip();
			if (action.contains(" ")) {
				actionEat(action);
			} else {
				actionOthers(action);
			}
		} else {
			consoleController.slowPrint("You need to choose another action.", delay);
			playerAction();
		}

	}

	private void actionOthers(String action) {
		switch (action) {
		case "n":
			actionNorth();
			break;
		case "w":
			actionWest();
			break;
		case "s":
			actionSouth();
			break;
		case "e":
			actionEast();
			break;
		case "controls":
			consoleController.displayControlls();
			break;
		case "status":
			displayStatus();
			break;
		case "search":
			actionSearch();
			break;
		case "inventory":
			actionInventory();
			break;
		case "money":
			actionMoney();
			break;
		default:
			break;
		}
	}

	private void actionMoney() {
		consoleController.slowPrint("Your money: " + player.getMoney(), delay);
	}

	private void actionInventory() {
		consoleController.slowPrint(player.printInventory(), delay);
	}

	private void actionSearch() {
		consoleController.slowPrint("You start searching...", delay);
		BasisFeld currentField = map.getFieldFromPosition(playerCoords[0], playerCoords[1]);
		if (currentField instanceof EndFeld) {
			consoleController.slowPrint(currentField.getEvent().getMessage(), delay);
			if (player.isJoystick()) {
				gameWon();
			}
		} else if (currentField instanceof StartFeld) {
			consoleController.slowPrint(currentField.getEvent().getMessage(), delay);
		} else if (currentField.isSearched()) {
			consoleController.slowPrint("You found nothing new.", delay);
		} else if (isFightEvent(currentField)) {
			handleFightEvent(currentField);
		} else {
			consoleController.slowPrint(currentField.getEvent().getMessage(), delay);
			currentField.setSearched(true);
			if (randomizer.getRandomBoolean(10)) {
				findSalesperson();
			} else {
				findRandomItem();
			}
		}
	}

	private void gameWon() {
		consoleController.displayBorder();
		consoleController.displayBorder();
		consoleController.slowPrint("You used the joystick and escaped with the helicopter!", delay);
		consoleController.slowPrint("You WON!", delay);
		gameWon = true;
	}

	private void findSalesperson() {
		consoleController.slowPrint("You found an old lady. She appears to be friendly", delay);
		consoleController.slowPrint("Do you want to buy somethin?", delay);
		String answerBuy = consoleController.readConsole().strip().toLowerCase();
		if (answerIsYes(answerBuy)) {
			buyProcess();
		}
		consoleController.slowPrint("Do you want to sell somethin?", delay);
		String answerSell = consoleController.readConsole().strip().toLowerCase();
		if (answerIsYes(answerSell)) {
			sellProcess();
		}
		consoleController.slowPrint("You say bye to the old lady and leave.", delay);
	}

	private void sellProcess() {
		actionMoney();
		actionInventory();
		consoleController.slowPrint("What do you want to sell?", delay);
		String answerSell = consoleController.readConsole().strip().toUpperCase();
		try {
			ItemsEnum itemToSell = ItemsEnum.valueOf(answerSell);
			if (player.hasItem(itemToSell)) {
				consoleController.slowPrint("How many do you want to sell?", delay);
				String countStr = consoleController.readConsole().strip();
				int countToSell = Integer.parseInt(countStr);
				consoleController.slowPrint(player.sellItem(itemToSell, countToSell), delay);
				actionMoney();
			} else {
				consoleController.slowPrint("You don't have any " + itemToSell.getDisplayName() + " to sell.", delay);
			}
		} catch (IllegalArgumentException e) {
			consoleController.slowPrint("Invalid item.", delay);
		}
		consoleController.slowPrint("Do you want to sell somethin?", delay);
		String answerSellAgain = consoleController.readConsole().strip().toLowerCase();
		if (answerIsYes(answerSellAgain)) {
			sellProcess();
		}
	}

	private void buyProcess() {
		ItemsEnum itemForSale = getRandomItem();
		consoleController.slowPrint("She has one thing to sell.", delay);
		consoleController.slowPrint(itemForSale.getDisplayName() + " for " + itemForSale.getPrice(), delay);
		consoleController.slowPrint("Buy it?", delay);
		String answerBuyIt = consoleController.readConsole().strip().toLowerCase();
		if (answerIsYes(answerBuyIt)) {
			consoleController.slowPrint(player.buyItem(itemForSale), delay);
		}
	}

	private ItemsEnum getRandomItem() {
		int range = ItemsEnum.values().length - 1;
		if (player.hasItem(ItemsEnum.PISTOL)) {
			range -= 1;
		}
		return ItemsEnum.values()[randomizer.getRandomIntFromRange(0, range)];
	}

	private void findRandomItem() {
		if (!player.isJoystick() && randomizer.getRandomBoolean(5)) {
			consoleController.slowPrint("You found an old joystick!", delay);
			player.foundJoystick();
		} else {
			int count;
			ItemsEnum randomItem = getRandomItem();
			if (randomItem.equals(ItemsEnum.PISTOL)) {
				count = 1;
			} else {
				count = randomizer.getRandomIntFromRange(1, 10);
			}
			consoleController.slowPrint(randomItem.getDisplayName(), delay);
			consoleController.slowPrint("Count: " + count, delay);
			player.addItem(randomItem, count);
		}

	}

	private void displayStatus() {
		consoleController.displayBorder();
		consoleController.slowPrint(player.getStatus(), delay);
	}

	private void actionEast() {
		if (playerCoords[0] == 0) {
			consoleController.slowPrint("You cant head East. There is an mountain range blocking your way.", delay);
		} else {
			consoleController.slowPrint("You head East.", delay);
			player.step();
			if (player.getHp() <= 0) {
				playerDied();
			} else {
				stepCount += 1;
				playerCoords[0] -= 1;
			}
		}
	}

	private void actionSouth() {
		if (playerCoords[1] == 0) {
			consoleController.slowPrint("You cant head South. There is an mountain range blocking your way.", delay);
		} else {
			consoleController.slowPrint("You head South.", delay);
			player.step();
			if (player.getHp() <= 0) {
				playerDied();
			} else {
				stepCount += 1;
				playerCoords[1] -= 1;
			}
		}
	}

	private void actionWest() {
		if (playerCoords[0] == map.getMapSize() - 1) {
			consoleController.slowPrint("You cant head West. There is an mountain range blocking your way.", delay);
		} else {
			consoleController.slowPrint("You head West.", delay);
			player.step();
			if (player.getHp() <= 0) {
				playerDied();
			} else {
				stepCount += 1;
				playerCoords[0] += 1;
			}
		}
	}

	private void actionNorth() {
		if (playerCoords[1] == map.getMapSize() - 1) {
			consoleController.slowPrint("You cant head North. There is an mountain range blocking your way.", delay);
		} else {
			consoleController.slowPrint("You head North.", delay);
			player.step();
			if (player.getHp() <= 0) {
				playerDied();
			} else {
				stepCount += 1;
				playerCoords[1] += 1;
			}
		}
	}

	private void descripeField() {
		BasisFeld currentField = map.getFieldFromPosition(playerCoords[0], playerCoords[1]);
		consoleController.slowPrint(currentField.getDescription(), delay);
		if (isFightEvent(currentField)) {
			FightEvent currentEvent = (FightEvent) currentField.getEvent();
			if (!currentEvent.isNeedsTrigger()) {
				handleFightEvent(currentField);
			}
		}
	}

	private void handleFightEvent(BasisFeld currentField) {
		FightEvent currentEvent = (FightEvent) currentField.getEvent();
		EnemiesEnum kindEnemies = currentEvent.getEnemiesKind();
		consoleController.slowPrint("You encounter " + kindEnemies.toString(), delay);
		consoleController.slowPrint("Do you want to flee?", delay);
		String answer = consoleController.readConsole().strip().toLowerCase();
		if (answerIsYes(answer)) {
			boolean flee = randomizer.getRandomBoolean(20);
			if (flee) {
				consoleController.slowPrint("You fleed", delay);
			} else {
				consoleController.slowPrint("Failed!", delay);
			}
		}
		consoleController.slowPrint("You need to fight!", delay);
		fight(currentField);
	}

	private boolean answerIsYes(String answer) {
		return answer.equals("yes");
	}

	private void fight(BasisFeld currentField) {
		FightEvent currentEvent = (FightEvent) currentField.getEvent();
		while (!currentEvent.isDefeated()) {
			int randomDmg = 0;
			consoleController.slowPrint("Its your turn!", delay);
			if (player.hasItem(ItemsEnum.PISTOL) && player.getCount(ItemsEnum.BULLET) > 0) {
				consoleController.slowPrint("Bullet count: " + player.getCount(ItemsEnum.BULLET), delay);
				consoleController.slowPrint("Do you want to shoot at them?", delay);
				String answer = consoleController.readConsole().strip().toLowerCase();
				if (answerIsYes(answer)) {
					player.useItem(ItemsEnum.BULLET);
					randomDmg = randomizer.getRandomIntFromRange(5, 15);
					consoleController.slowPrint("You shoot!", delay);
				} else {
					consoleController.slowPrint("You attack with your fists.", delay);
					randomDmg = randomizer.getRandomIntFromRange(5, 10);
				}
			} else {
				consoleController.slowPrint("You attack with your fists.", delay);
				randomDmg = randomizer.getRandomIntFromRange(5, 10);
			}
			currentEvent.decreasehp(randomDmg);
			if (!currentEvent.isDefeated()) {
				consoleController.displayBorder();
				consoleController.slowPrint("Enemy is still alive. They attack.", delay);
				randomDmg = randomizer.getRandomIntFromRange(5, 10);
				player.decreaseHp(randomDmg);
				consoleController.slowPrint("You took damage " + randomDmg, delay);
				if (player.getHp() <= 0) {
					playerDied();
					return;
				} else {
					displayStatus();
				}
			}

		}
		consoleController.slowPrint("You won!", delay);
		currentField.setSearched(true);
		findRandomItem();
	}

	private boolean isFightEvent(BasisFeld currentField) {
		if (currentField.getEvent() instanceof FightEvent) {
			return !((FightEvent) currentField.getEvent()).isDefeated();
		}
		return false;
	}

	private void playerDied() {
		alive = false;
		consoleController.slowPrint("You died. Its GAME OVER.", delay);
		consoleController.slowPrint("You did : " + stepCount + " steps, before dying.", delay);
	}

	private void actionEat(String action) {
		String[] words = action.split(" ");
		if (words.length < 2) {
			consoleController.slowPrint("Please specify an item to use or eat.", delay);
			return;
		}
		String itemName = words[1].toUpperCase();
		ItemsEnum item;
		try {
			item = ItemsEnum.valueOf(itemName);
		} catch (IllegalArgumentException e) {
			consoleController.slowPrint("Invalid item specified.", delay);
			return;
		}
		if (words[0].equals("eat")) {
			consoleController.slowPrint(player.eatItem(item, 1), delay);
		} else {
			consoleController.slowPrint("Invalid action specified.", delay);
		}
	}

	private boolean checkIfCommand(String action) {
		String[] keywords = { "n", "w", "s", "e", "controls", "status", "search", "loser", "inventory", "money" };
		Pattern eatPattern = Pattern.compile("eat\\s+(\\w+)");
		for (String keyword : keywords) {
			if (action.contains(keyword) || eatPattern.matcher(action).find()) {
				return true;
			}
		}
		return false;
	}

}
