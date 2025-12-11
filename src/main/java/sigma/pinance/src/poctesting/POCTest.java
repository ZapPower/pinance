package sigma.pinance.src.poctesting;

import sigma.pinance.src.cli.CLIController;

import java.util.Scanner;

public class POCTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CLIController.init(scanner);
        while (true) {
            CLIController.nextCommand();
        }
    }
}
