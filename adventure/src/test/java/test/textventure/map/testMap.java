package test.textventure.map;

import org.junit.Test;

import textventure.map.BasisFeld;
import textventure.map.Map;
import textventure.map.StartFeld;

import static org.junit.Assert.*;

public class testMap {
	@Test
	public void testMapSize() {
		Map map = new Map();
		BasisFeld[][] generatedMap = map.getMap();

		assertEquals(50, generatedMap.length);
		assertEquals(50, generatedMap[0].length);
	}

	@Test
	public void testStartAndEndFields() {
		Map map = new Map();
		BasisFeld[][] generatedMap = map.getMap();
		int[] startCoords = map.getStartCoords();
		int[] goalCoords = map.getGoalCoords();

		assertNotNull(generatedMap[startCoords[0]][startCoords[1]]);
		assertNotNull(generatedMap[goalCoords[0]][goalCoords[1]]);
	}

	@Test
	public void testRandomlyGeneratedEvents() {
		Map map = new Map();
		BasisFeld[][] generatedMap = map.getMap();

		for (int i = 0; i < generatedMap.length; i++) {
			for (int j = 0; j < generatedMap[0].length; j++) {
				if (!(i == map.getStartCoords()[0] && j == map.getStartCoords()[1])
						&& !(i == map.getGoalCoords()[0] && j == map.getGoalCoords()[1])) {
					assertNotNull(generatedMap[i][j].getEvent());
				}
			}
		}
	}

	@Test
	public void testGetFieldFromPosition() {
		Map map = new Map();
		int[] startCoords = map.getStartCoords();

		BasisFeld startField = map.getFieldFromPosition(startCoords[0], startCoords[1]);
		assertNotNull(startField);
		assertEquals(StartFeld.class, startField.getClass());
	}
}
