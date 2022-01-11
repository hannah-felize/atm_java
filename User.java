import java.util.ArrayList;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    /**
     * The first name of the user
     */
    private String firstName;

    /**
     * The last name of the user
     */
    private String lastName;

    /**
     * The ID number of the user
     */
    private String uuid;

    /**
     * The MD5 hash of the user's pin number
     */
    private byte pinHash[]; // MD5 hash since we don't want to store the actual pin

    /**
     * The list of accounts for this user
     */
    private ArrayList<Account> accounts; // an arraylist a Java dynamic array

    /**
     * Create a new user
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param pin       the user's account pin number
     * @param theBank   the Bank object that the user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {
        // set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's MD5 hash, rather than the original value, for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes()); // taking the pin and grabbing its bytes, then digesting them into the MD5 algorithm
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException"); 
            e.printStackTrace();
            System.exit(1);
        }

        // get a new unique universal identifier for the user
        this.uuid = theBank.getNewUserUUID();
    
        // create empty list of accounts
        this.accounts = new ArrayList<Account>();
    
        // print log message
        System.out.printf("New User %s, %s with ID %s created.\n", lastName, firstName, this.uuid); // [?] why do we use 'this' only for the uuid?
    }

    /**
     * Add an account for the user
     * @param anAccount the account to add
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    /**
     * Get the user ID
     * @return the uuid
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Check whether a given pin matches the true User pin
     * @param aPIN  the pin to check
     * @return      whether the pin is valid or not
     */
    public boolean validatePIN(String aPIN) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPIN.getBytes()), this.pinHash); // returns a boolean value that says whether they're equal
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException"); 
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    /**
     * Return the user's first name.
     * @return the first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary:\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }

        System.out.println();
    }

    /**
     * Get the number of accounts of the user
     * @return the number of accounts
     */
    public int numAccounts() {
        return this.accounts.size();
    }

    /**
     * Print transaction history for a particular account.
     * @param accountIndex the index of the account to use
     */
    public void printAcctTransHistory(int accountIndex) {
        this.accounts.get(accountIndex).printTransHistory();
    }

    /**
     * Get the balance of a particular account
     * @param accountIndex  the index of the account to use
     * @return              the balance of the account
     */
    public double getAccountBalance(int accountIndex) {
        return this.accounts.get(accountIndex).getBalance();
    }

    /**
     * Get the UUID of a particular account
     * @param accountIndex  the index of the account to use
     * @return              the UUID of the account
     */
    public String getAccountUUID(int accountIndex) {
        return this.accounts.get(accountIndex).getUUID();
    }

    /**
     * Add a transaction to a particular account
     * @param accountIndex  the index of the account
     * @param amount        the amount of the transaction
     * @param memo          the memo of the transaction
     */
    public void addAcctTransaction(int accountIndex, double amount, String memo) {
        // for simplicity, we'll assume that there's always going to be a memo

        System.out.printf("\n=====DEBUGGING START WITHIN USER.JAVA=====\naccountIndex: %s\namount: %f\nmemo: %s\n====DEBUGGING END=====\n", accountIndex, amount, memo); // for debugging
        this.accounts.get(accountIndex).addTransaction(amount, memo);
    }
}