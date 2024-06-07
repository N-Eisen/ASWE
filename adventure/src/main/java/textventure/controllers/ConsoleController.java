package textventure.controllers;

import java.util.Scanner;

public class ConsoleController {
	private Scanner scanner = new Scanner(System.in);

	public String readConsole() {
		System.out.print("Enter something: ");
		String userInput = scanner.nextLine();
		return userInput;
	}

	public void slowPrint(String message, int delay) {
		try {
			for (char c : message.toCharArray()) {
				System.out.print(c);
				Thread.sleep(delay);
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
