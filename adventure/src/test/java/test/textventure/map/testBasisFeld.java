package test.textventure.map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import textventure.events.BasisEvent;
import textventure.map.BasisFeld;

public class testBasisFeld {
	private BasisFeld basisFeld;
	private BasisEvent testEvent;
	private String testDescription;

	@Before
	public void setUp() {
		testEvent = new BasisEvent("TestEvent");
		testDescription = "This is a test description.";
		basisFeld = new BasisFeld(testEvent, testDescription);
	}

	@Test
	public void testConstructor() {
		assertEquals(testEvent, basisFeld.getEvent());
		assertEquals(testDescription, basisFeld.getDescription());
		assertFalse(basisFeld.isSearched()); // Default-Wert sollte false sein
	}

	@Test
	public void testGetAndSetEvent() {
		BasisEvent newEvent = new BasisEvent("NewTestEvent");
		basisFeld.setEvent(newEvent);
		assertEquals(newEvent, basisFeld.getEvent());
	}

	@Test
	public void testGetAndSetDescription() {
		String newDescription = "New test description.";
		basisFeld.setDescription(newDescription);
		assertEquals(newDescription, basisFeld.getDescription());
	}

	@Test
	public void testGetAndSetSearched() {
		basisFeld.setSearched(true);
		assertTrue(basisFeld.isSearched());

		basisFeld.setSearched(false);
		assertFalse(basisFeld.isSearched());
	}

}
