package main;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the package weight: ");
        double weight = scanner.nextDouble();
        Package pkg = new Package(weight);
        int updateChoice = 1;

        while (true) {
            if (updateChoice == 1) {
                System.out.println("\nSelect Shipping Method:");
                System.out.println("1. Standard Shipping");
                System.out.println("2. Express Shipping");
                System.out.print("Enter your choice: ");
                int shippingChoice = scanner.nextInt();

                switch (shippingChoice) {
                    case 1 -> pkg.setShippingStrategy(new StandardShipping());
                    case 2 -> pkg.setShippingStrategy(new ExpressShipping());
                    default -> {
                        System.out.println("Invalid choice. Please select again.");
                        continue;
                    }
                }
                pkg.calculateCost();
                System.out.println("Current shipping cost: " + pkg.getCost());
                pkg.printState();
            } else if (updateChoice == 2) {
                System.out.println("\nSelect Package Status:");
                System.out.println("1. In-transit");
                System.out.println("2. Delivered");
                System.out.print("Enter your choice: ");
                int statusChoice = scanner.nextInt();

                switch (statusChoice) {
                    case 1 -> pkg.setState(new InTransitState());
                    case 2 -> pkg.setState(new DeliveredState());
                    default -> System.out.println("Invalid choice. Please select again.");
                }
                System.out.println("Current shipping cost: " + pkg.getCost());
                pkg.printState();
            }
            else  System.out.println("Invalid choice. Please select again.");

            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Update Shipping Strategy");
            System.out.println("2. Update Package State");
            System.out.print("Enter your choice: ");
            updateChoice = scanner.nextInt();
        }
    }
}
