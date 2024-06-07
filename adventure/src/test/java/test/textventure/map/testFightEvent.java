package test.textventure.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import textventure.enums.EnemiesEnum;
import textventure.events.FightEvent;

public class testFightEvent {

	@Test
	public void testDefeated() {
		FightEvent fightEvent = new FightEvent("A fierce battle!", EnemiesEnum.Goblin, true, 50);

		assertFalse(fightEvent.isDefeated());

		fightEvent.decreasehp(60);

		assertTrue(fightEvent.isDefeated());
	}

	@Test
	public void testDecreaseHp() {
		FightEvent fightEvent = new FightEvent("A fierce battle!", EnemiesEnum.Goblin, true, 50);

		fightEvent.decreasehp(20);

		assertEquals(30, fightEvent.getHpEnemy());
	}
}
