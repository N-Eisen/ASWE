package textventure.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleController {
	private Scanner scanner = new Scanner(System.in);

	public String readConsole() {
		System.out.print("Enter something: ");
		return scanner.nextLine();
	}

	public void slowPrint(String message, int delay) {
		try {
			for (char c : message.toCharArray()) {
				System.out.print(c);
				Thread.sleep(delay);
			}
			System.out.println();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

	public void displayControlls() {
		try (BufferedReader reader = new BufferedReader(
				new FileReader("src/main/java/textventure/filereader/Controls"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				slowPrint(line, 50);
			}
		} catch (IOException e) {
			System.out.println("Error reading welcome message file: " + e.getMessage());
		}
	}

	public void displayBorder() {
		slowPrint("-----------------------------------------", 50);
	}

	public void displayWelcomeMessage() {
		try (BufferedReader reader = new BufferedReader(
				new FileReader("src/main/java/textventure/filereader/WelcomeMessage"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				slowPrint(line, 50);
			}
		} catch (IOException e) {
			System.out.println("Error reading welcome message file: " + e.getMessage());
		}
	}
}
