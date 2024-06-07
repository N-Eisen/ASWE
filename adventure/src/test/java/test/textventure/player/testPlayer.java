package test.textventure.player;

import org.junit.Before;

import static org.junit.Assert.*;

import textventure.enums.ItemsEnum;
import textventure.player.Player;
import org.junit.Test;

public class testPlayer {
	private Player player;

	@Before
	public void setUp() {
		player = new Player();
		player.setName("TestPlayer");
	}

	@Test
	public void testInitialValues() {
		assertEquals(100, player.getHunger());
		assertEquals(1, player.getHp());
		assertEquals("TestPlayer", player.getName());
		assertEquals(1.0, player.getMoney(), 0.001);
		assertFalse(player.isJoystick());
	}

	@Test
	public void testAddItem() {
		String response = player.addItem(ItemsEnum.BERRY, 3);
		assertEquals("Added Item: Berry", response);
		assertTrue(player.hasItem(ItemsEnum.BERRY));
		assertEquals(3, player.getCount(ItemsEnum.BERRY));
	}

	@Test
	public void testEatItem() {
		player.addItem(ItemsEnum.BERRY, 2);

		String response = player.eatItem(ItemsEnum.BERRY, 2);
		assertEquals("You ate 2 Berry", response);
		assertEquals(100, player.getHunger());
		assertFalse(player.hasItem(ItemsEnum.BERRY));
	}

	@Test
	public void testEatNonEatableItem() {
		player.addItem(ItemsEnum.BULLET, 2);

		String response = player.eatItem(ItemsEnum.BULLET, 1);
		assertEquals("Item Bullet is not eatable.", response);
		assertTrue(player.hasItem(ItemsEnum.BULLET));
	}

	@Test
	public void testUseItem() {
		player.addItem(ItemsEnum.BULLET, 3);

		assertTrue(player.useItem(ItemsEnum.BULLET));
		assertEquals(2, player.getCount(ItemsEnum.BULLET));
		assertTrue(player.useItem(ItemsEnum.BULLET));
		assertEquals(1, player.getCount(ItemsEnum.BULLET));
	}

	@Test
	public void testStep() {
		player.step();
		assertEquals(95, player.getHunger());
		assertEquals(6, player.getHp());

		player.step();
		assertEquals(90, player.getHunger());
		assertEquals(11, player.getHp());
	}

	@Test
	public void testDecreaseHp() {
		player.decreaseHp(1);
		assertEquals(0, player.getHp());
	}

	@Test
	public void testIncreaseHp() {
		player.step();
		assertEquals(6, player.getHp());

		player.step();
		assertEquals(11, player.getHp());
	}

	@Test
	public void testPrintInventory() {
		player.addItem(ItemsEnum.BERRY, 3);
		player.addItem(ItemsEnum.BULLET, 2);
		String expectedOutput = "Inventory:\n" + "Berry: 3 | Price: $0.5 | Eatable: Yes | Hunger Value: 5\n"
				+ "Bullet: 2 | Price: $0.5 | Eatable: No";
		
		String inventoryOutput = player.printInventory().trim();
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

	@Test
	public void testSellItem() {
		player.addItem(ItemsEnum.BERRY, 5);
		String response = player.sellItem(ItemsEnum.BERRY, 2);
		assertEquals("You sold 2 Berry(s) for 1.0 money.", response);
		assertEquals(3, player.getCount(ItemsEnum.BERRY));
		assertEquals(2.0, player.getMoney(), 0.001);
	}

	@Test
	public void testSellMoreItemsThanAvailable() {
		player.addItem(ItemsEnum.BERRY, 1);
		String response = player.sellItem(ItemsEnum.BERRY, 2);
		assertEquals("You do not have enough Berry to sell.", response);
	}

	@Test
	public void testSellNonExistentItem() {
		String response = player.sellItem(ItemsEnum.BERRY, 1);
		assertEquals("not", response);
	}

	@Test
	public void testBuyItem() {
		ItemsEnum itemForSale = ItemsEnum.BERRY;

		String response = player.buyItem(itemForSale);
		assertEquals("Item bought", response);
		assertTrue(player.hasItem(itemForSale));
		assertEquals(1.5, player.getMoney(), 0.001);
	}

	@Test
	public void testBuyItemWithoutEnoughMoney() {
		player.buyItem(ItemsEnum.PISTOL);

		String response = player.buyItem(ItemsEnum.PISTOL);
		assertEquals("Not enough money", response);
	}

	@Test
	public void testFoundJoystick() {
		player.foundJoystick();
		assertTrue(player.isJoystick());
	}

	@Test
	public void testGetStatus() {
		String status = player.getStatus();
		assertEquals("HP: 1  Hunger: 100", status);

		player.step();
		status = player.getStatus();
		assertEquals("HP: 6  Hunger: 95", status);
	}
}
