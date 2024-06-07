package textventure.map;

import lombok.Getter;
import textventure.enums.EnemiesEnum;
import textventure.events.BasisEvent;
import textventure.events.EndEvent;
import textventure.events.FightEvent;
import textventure.events.NothingEvent;
import textventure.events.StartEvent;
import textventure.filereader.FileReaderFieldDescription;
import textventure.filereader.FileReaderFight;
import textventure.randomizer.Randomizer;

@Getter
public class Map {
	private int mapSize = 50;
	private Randomizer randomizer = new Randomizer();
	private FileReaderFight fileReaderFight = new FileReaderFight();
	private FileReaderFieldDescription fileReaderField = new FileReaderFieldDescription();
	private EnemiesEnum[] enemiesKinds = EnemiesEnum.values();
	private int[] startCoords = new int[2];
	private int[] goalCoords = new int[2];
	private BasisFeld[][] map = generateMap(mapSize);

	private BasisFeld[][] generateMap(int size) {
		BasisFeld[][] mapInitial = new BasisFeld[size][size];
		for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mapInitial[i][j] = randomEvent();
            }
        }
		BasisFeld[][] mapFinal = generateSpecialFields(mapInitial);
		return mapFinal;
	}

	private BasisFeld[][] generateSpecialFields(BasisFeld[][] mapInitial) {
		boolean isSame = true;
		startCoords[0] = randomizer.GetRandomIntFromRange(0, mapInitial.length-1);
		startCoords[1] = randomizer.GetRandomIntFromRange(0, mapInitial.length-1);
		while (isSame) {
			goalCoords[0] = randomizer.GetRandomIntFromRange(0, mapInitial.length-1);
			goalCoords[1] = randomizer.GetRandomIntFromRange(0, mapInitial.length-1);
			if (!(startCoords[0] == goalCoords[0] && startCoords[1] == goalCoords[1])) {
				isSame = false;
			}
		}
		mapInitial[startCoords[0]][startCoords[1]] = new StartFeld(new StartEvent());
		mapInitial[goalCoords[0]][goalCoords[1]] = new EndFeld(new EndEvent());
		return mapInitial;
	}

	private BasisFeld randomEvent() {
		int RandomInt = randomizer.GetRandomIntFromRange(0, 1);
		BasisFeld generatedFeld = new BasisFeld(new NothingEvent("Nothing to see here"), generateFieldDescription());
		switch (RandomInt) {
		case 0:
			generatedFeld.setEvent(generateFightEvent());
			break;
		case 1:
			generatedFeld.setEvent(generateBasisEvent());
			break;
		}
		return generatedFeld;
	}

	private BasisEvent generateBasisEvent() {
		return new BasisEvent("You found something.");
	}

	private String generateFieldDescription() {
		return fileReaderField.getLine();
	}

	private FightEvent generateFightEvent() {
		return new FightEvent(fileReaderFight.getLine(),
				enemiesKinds[randomizer.GetRandomIntFromRange(0, enemiesKinds.length-1)], randomizer.GetRandomBoolean(50),
				randomizer.GetRandomIntFromRange(10, 30));
	}
	
	public BasisFeld getFieldFromPosition(int x, int y) {
		return map[x][y];
	}
}
