package sigma.pinance.src.poctesting;

import sigma.pinance.src.cli.CLIController;

import java.util.Scanner;
import java.util.regex.Pattern;

public class POCTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(": ");
        while (true) {
            String input = scanner.nextLine();
            CLIController.executeCommand(input);
            System.out.print(": ");
        }
    }
}
