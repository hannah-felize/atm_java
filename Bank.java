import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts; // each user object already has its own list of accounts, but this will be a big list of all the accounts

    /**
     * Create a new bank object with empty lists of users and accounts
     * @param name the name of the bank
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Generate a new universal unique identifier for a user
     * @return the UUID
     */
    public String getNewUserUUID() {
        // initialize
        String uuid;
        Random rng = Random();
        int len = 6;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {
            // generate the number
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer)rng.nextInt(10)).toString(); // the Integer class is wrapping around the primitive integer, allowing us to call methods like toString()
            }

            // check to make sure it's unique
            nonUnique = false;
            for (User u : this.users) { // for each user, do the following:
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique = true);

        return uuid;
    }

    /**
     * Generate a new universal unique identifier for an account
     * @return the UUID
     */
    public String getNewAccountUUID() {
        // initialize
        String uuid;
        Random rng = Random();
        int len = 10; // since there will likely be more accounts than users, this UUID length is 10
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {
            // generate the number
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer)rng.nextInt(10)).toString(); // the Integer class is wrapping around the primitive integer, allowing us to call methods like toString()
            }

            // check to make sure it's unique
            nonUnique = false;
            for (Account a : this.accounts) { // for each account, do the following:
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique = true);

        return uuid;

    }

    /**
     * Add an account for the bank
     * @param anAccount the account to add
     */
    public void addAccount(Account anAccount)
    {
        accounts.add(anAccount);
    }

    /**
     * Add a user for the bank
     * @param firstName
     * @param lastName
     * @param pin
     */
    public User addUser(String firstName, String lastName, String pin) {
        // create a new User object and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // create a savings account for the user and add to User and Bank accounts lists
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin) {
        // search through list of users
        for (User u : this.users) {

            // check user ID is correct
            if (u.getUUID().compareTo(userID) == 0 && u.validatePIN(pin)) {
                return u;
            }
        }

        return null;
    }
}