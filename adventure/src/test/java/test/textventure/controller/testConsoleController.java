package test.textventure.controller;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import textventure.controllers.ConsoleController;

public class testConsoleController {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final InputStream originalIn = System.in;

	private ConsoleController consoleController;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		consoleController = new ConsoleController();
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setIn(originalIn);
	}

	@Test
	public void testReadConsole() {
		//Manueller test
		String input = "Test input";
		provideInput(input);
		String userInput = consoleController.readConsole();
		assertEquals("Input from console should match provided input", input, userInput);
	}

	@Test
	public void testSlowPrint() {
		String message = "This is a test message.";
		int delay = 10;
		consoleController.slowPrint(message, delay);
		String expectedOutput = message + System.lineSeparator();
		assertEquals("Slow print should output the message with a delay", expectedOutput, outContent.toString());
	}

	private void provideInput(String data) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getBytes());
		System.setIn(inputStream);
	}
}
