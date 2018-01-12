import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;
import java.util.Arrays;
import java.io.IOException;

public class BlockChainDriver {

	/**
	 * Checks the validity of the transfer information and then calls transferKernel
	 *
	 * @param  bc the blockchain to record the transaction
	 * @param  users the users
	 * @param  s the scanner
	 * @return whether or not the transfer was successful
	 * @see transferKernel
	 */
  private static boolean transferHusk(BlockChain bc, UserMap users, Scanner s) {
    System.out.print("Transfer from(public key): ");
    String fromPublicKeyString = s.nextLine();
    System.out.print("Transfer from(secret key): ");
    String fromPrivateKeyString = s.nextLine();
    System.out.print("Transfer to(public address): ");
    String toPublicKeyString = s.nextLine();

		PublicKey fromPublicKey = null;
		PrivateKey fromPrivateKey = null;
		PublicKey toPublicKey = null;

		try {
			KeyStringConverter conv = new KeyStringConverter();
			fromPublicKey = conv.stringToPublicKey(fromPublicKeyString);
			fromPrivateKey = conv.stringToPrivateKey(fromPrivateKeyString);
			toPublicKey = conv.stringToPublicKey(toPublicKeyString);
		} catch (GeneralSecurityException e) {
			System.out.println("One or more of the keys were invalid. Aborting.");
			System.err.println("Caught GeneralSecurityException: "+e.getMessage());
			return false;
		}

		if (!users.userExists(fromPublicKey)) {
			System.out.println("From user not found");
			return false;
		}
		User u1 = users.getUser(fromPublicKey);

		if (!users.userExists(toPublicKey)) {
			System.out.println("To user not found");
			return false;
		}
		User u2 = users.getUser(toPublicKey);

    System.out.print("Amount transferred: ");
    int amt = s.nextInt();

		if (users.getUser(fromPublicKey).getBalance() < amt) {
			System.out.println("Insufficient balance for transfer");
			return false;
		}

		return transferKernel(bc, users, amt, u1, u2);
  }

	/**
   * Attempts the transaction and returns if it was successful
   *
   * @param  bc the blockchain to record the transaction
	 * @param users the users
	 * @param amt the amount of the transaction
	 * @param u1 the user the GifCoins come from
	 * @param u2 the user the GifCoins go to
   * @return whether or not the transfer was successful
   */
	public static boolean transferKernel(BlockChain bc, UserMap users, int amt, User u1, User u2) {
		// Calculate digital signature
		Block b = new Block(bc.getSize()+1, amt, bc.getHash());
		b.signBlock(u1.secretKey);
		if (b.isValid(u1.publicKey)) {
			approveTransaction(bc, b, u1, u2, amt);
			// Current substitution for mining

			return true;
		} else {
			System.out.println("Transaction block found invalid");
			return false;
		}
	}

	/**
	 * Approves the transaction
	 *
	 * @param  bc the blockchain to record the transaction
	 * @param app the transaction block
	 * @param u1 the user the GifCoin comes from
	 * @param u2 the user the GifCoin goes to
	 * @param int the amount of the transaction
	 */
  private static void approveTransaction(BlockChain bc, Block app, User u1, User u2, int amt) {
    bc.append(app);
		u1.withdraw(amt);
		u2.deposit(amt);
  }

	/**
   * Creates a new user and adds them to the group of users
   *
   * @param  users the group of users
	 * @param s the scanner
   */
  private static void createNewUser(UserMap users, Scanner s) throws NoSuchAlgorithmException {
    System.out.print("Initial balance: ");
    int initBalance = s.nextInt();
    users.addUser(initBalance);
  }

	/**
	 * Prints info about Gif-Coin
	 */
  public static void printInfo() {
    System.out.println("Gif-coin is a digital crypto-currency that leverages");
    System.out.println("blockchain to create a centralized, safe, easy currency");
  }

	/**
	 * Parses commands and calls them
	 */
  private static void parseInput(BlockChain bc, UserMap users, Scanner s, String cmd) throws NoSuchAlgorithmException {

    switch (cmd) {
    case "transfer":
      transferHusk(bc, users, s);
      break;
    case "create user":
      createNewUser(users, s);
      break;
		case "list users":
			System.out.println(users.toString());
			break;
    case "list transactions":
      System.out.println(bc.toString());
      break;
		case "help":
			printHelp();
			break;
		case "info":
			printInfo();
			break;
    case "quit":
      break;
    default:
      System.out.println("Command not valid. Type 'help' to see list of commands.");
      break;
    }
  }

	/**
   * Keeps receiving commands from the user and parsing them until quit
   *
   * @param bc the blockchain
	 * @param users the group of users
   */
  private static void runBlockChainLoop(BlockChain bc, UserMap users) throws NoSuchAlgorithmException {
    Scanner s = new Scanner(System.in);

    String cmd = "";
		while (!cmd.equals("quit")) {
			System.out.print("\nCommand: ");
			cmd = s.nextLine();
      parseInput(bc, users, s, cmd);
		}

		s.close();
  }

	/**
   * Prints the list of commands
   */
	private static void printHelp() {
		System.out.println("Valid commands:");
		System.out.println("\transfer: transfers GifCoin from one user to another");
		System.out.println("\tcreate user: creates a new user to trade Gif-coins");
		System.out.println("\tlist users: prints out all current users's pk and balances");
		System.out.println("\tlist transactions: prints out all past transactions");
		System.out.println("\thelp: prints this list of commands");
		System.out.println("\tquit: quits the program");
	}

	/**
   * Prints a custom welcome message
   */
  private static void printWelcomeMessage() {
    System.out.println("");
    System.out.println("\n\n========================================================");
    System.out.println("Hello and welcome to Gif-Bank, the new standard in");
    System.out.println("crypto-currency. If you are new, you can type 'info' for");
    System.out.println("info or 'help' for a list of commands.");
    System.out.println("\nOtherwise, have fun!");
    System.out.println("========================================================");
  }

	/**
   * The beginning of the program, including welcoming the user and activating
	 * the blockchain loop
   *
   * @param args the command line arguments
   */
	public static void main(String[] args) throws NoSuchAlgorithmException {
    printWelcomeMessage();

		BlockChain bc = new BlockChain(50);
    UserMap users = new UserMap();
		runBlockChainLoop(bc, users);
	}

}
