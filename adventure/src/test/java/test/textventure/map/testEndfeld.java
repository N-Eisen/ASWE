package test.textventure.map;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import textventure.events.EndEvent;
import textventure.map.EndFeld;

public class testEndfeld {
	private EndFeld endFeld;
	private EndEvent mockEvent;

	@Before
	public void setUp() {
		mockEvent = mock(EndEvent.class);
		endFeld = new EndFeld(mockEvent);
	}

	@Test
	public void testConstructorSetsEvent() {
		assertEquals(endFeld.getEvent(), mockEvent);
	}

	@Test
	public void testConstructorSetsDescription() {
		String expectedDescription = "The it is. An option to get out of here. You see an old helicopter.";
		assertEquals(expectedDescription, endFeld.getDescription());
	}

	@Test
	public void testSearchedDefaultIsFalse() {
		assertFalse(endFeld.isSearched());
	}

	@Test
	public void testSetSearched() {
		endFeld.setSearched(true);
		assertTrue(endFeld.isSearched());
	}

}
