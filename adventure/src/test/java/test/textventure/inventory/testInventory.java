package test.textventure.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import textventure.enums.ItemsEnum;
import textventure.inventory.Inventory;

public class testInventory {
	private Inventory inventory;

	@Before
	public void setUp() {
		inventory = new Inventory();
	}

	@Test
	public void testAddItem() {
		String response = inventory.addItem(ItemsEnum.BERRY, 3);
		assertEquals("Added Item: Berry", response);
		assertTrue(inventory.hasItem(ItemsEnum.BERRY));
		assertEquals(3, inventory.getCount(ItemsEnum.BERRY));

		inventory.addItem(ItemsEnum.BERRY, 2);
		assertEquals(5, inventory.getCount(ItemsEnum.BERRY));
	}

	@Test
	public void testHasItem() {
		assertFalse(inventory.hasItem(ItemsEnum.BERRY));
		inventory.addItem(ItemsEnum.BERRY, 1);
		assertTrue(inventory.hasItem(ItemsEnum.BERRY));
	}

	@Test
	public void testDecreaseItem() {
		inventory.addItem(ItemsEnum.BERRY, 5);
		assertTrue(inventory.decrease(ItemsEnum.BERRY, 2));
		assertEquals(3, inventory.getCount(ItemsEnum.BERRY));

		assertTrue(inventory.decrease(ItemsEnum.BERRY, 3));
		assertFalse(inventory.hasItem(ItemsEnum.BERRY));

		inventory.addItem(ItemsEnum.BULLET, 2);
		assertFalse(inventory.decrease(ItemsEnum.BULLET, 3));
		assertEquals(2, inventory.getCount(ItemsEnum.BULLET));
	}

	@Test
	public void testPrintInventory_FlexibleComparison() {
		inventory.addItem(ItemsEnum.BERRY, 3);
		inventory.addItem(ItemsEnum.BULLET, 2);

		String expectedOutput = "Inventory:\n" + "Berry: 3 | Price: $0.5 | Eatable: Yes | Hunger Value: 5\n"
				+ "Bullet: 2 | Price: $0.5 | Eatable: No";

		String inventoryOutput = inventory.printInventory().trim();
		String[] expectedLines = expectedOutput.trim().split("\n");
		String[] actualLines = inventoryOutput.split("\n");

		assertEquals(expectedLines.length, actualLines.length);

		for (String expectedLine : expectedLines) {
			boolean found = false;
			for (String actualLine : actualLines) {
				if (expectedLine.trim().equals(actualLine.trim())) {
					found = true;
					break;
				}
			}
			assertTrue("Die erwartete Zeile '" + expectedLine + "' wurde nicht in der Ausgabe gefunden.", found);
		}
	}
}
