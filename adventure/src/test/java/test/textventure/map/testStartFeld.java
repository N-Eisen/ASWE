package test.textventure.map;

import textventure.events.StartEvent;
import textventure.map.StartFeld;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class testStartFeld {
	private StartFeld startFeld;
	private StartEvent mockEvent;

	@Before
	public void setUp() {
		mockEvent = mock(StartEvent.class);
		startFeld = new StartFeld(mockEvent);
	}

	@Test
	public void testConstructorSetsEvent() {
		assertEquals(mockEvent, startFeld.getEvent());
	}

	@Test
	public void testConstructorSetsDescription() {
		String expectedDescription = "You open your eyes. You look around and see nothing but trees.";
		assertEquals(expectedDescription, startFeld.getDescription());
	}

	@Test
	public void testSearchedDefaultIsFalse() {
		assertFalse(startFeld.isSearched());
	}

	@Test
	public void testSetSearched() {
		startFeld.setSearched(true);
		assertTrue(startFeld.isSearched());
	}
}
