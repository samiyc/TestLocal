import java.util.Scanner;

public class AgentExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Read input from the user
            System.out.println("Enter your command:");
            String userInput = scanner.nextLine();

            // Process the input
            if (userInput.equals("hello")) {
                System.out.println("Hello! How can I help you?");
            } else if (userInput.equals("bye")) {
                System.out.println("Goodbye!");
                break; // Exit the loop and terminate the program
            } else {
                System.out.println("Sorry, I don't understand. Please try again.");
            }
        }

        // Close the scanner when done
        scanner.close();
    }
}
