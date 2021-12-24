// Holds public static void main(), the entry point to our program

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        // initialize scanner
        Scanner sc = new Scanner(System.in);

        // initialize bank
        Bank theBank = new Bank("Bank of De Luna");

        // add a user, which also creates a savings account
        User aUser = theBank.addUser("John", "Doe", "1234");

        // add a checking account for our user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount); 

        User curUser;
        while (true) {
            // stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);

            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc); // note that you can't have more than one scanner reading from System.in, so we just pass the single scanner, sc
        }
    }

    /**
     * Print the ATM's login menu
     * @param theBank   the bank object whose accounts to use
     * @param sc        the scanner object to use for user input
     * @return          the authenticated User object
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) { // this method is static since there's no data in our ATM class
        // inits
        String userID;
        String pin;
        User authUser;

        // prompt the user for user ID/pin combo until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s \n\n", theBank.getName());
            System.out.print("Enter User ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination. " + "Please try again");
            }
        } while (authUser == null); // continue looping until successful login

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {
        // print a summary of the user's accounts
        theUser.printAccountsSummary();

        // initialize
        int choice;

        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?", theUser.getFirstName());
            System.out.println("    1) Show account transaction history");
            System.out.println("    2) Withdrawl");
            System.out.println("    3) Desposit");
            System.out.println("    4) Transfer");
            System.out.println("    5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while (choice < 1 || choice > 5);

        // process the choice
        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawlFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            // note that we don't have to handle the default (5: exit) choice
        }

        // redisplay this menu unless the user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc); // note that this is a recursive call
        }
    }
}