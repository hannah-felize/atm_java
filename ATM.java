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
    }
}
