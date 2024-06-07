package textventure.controllers;

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
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
	        e.printStackTrace();
		}
	}
}
