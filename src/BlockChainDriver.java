import java.util.Scanner;
import java.security.*;
import java.io.IOException;
import java.util.Arrays;

public class BlockChainDriver {

	private static void printHelp() {
		System.out.println("Valid commands:");
		System.out.println("\tmine: discovers the nonce for a given transaction");
		System.out.println("\tappend: appends a new block onto the end of the chain");
		System.out.println("\treport: reports the balances of Alice and Bob");
		System.out.println("\tcreate user: creates a new user to trade Gif-coins");
		System.out.println("\tlist users: prints out all current users's pk and balances");
		System.out.println("\thelp: prints this list of commands");
		System.out.println("\tquit: quits the program");
	}

  // Mining new Gif-coins
  private static void mineBlock(BlockChain bc, Scanner s) throws NoSuchAlgorithmException {
    System.out.print("Name: ");
    String name = s.nextLine();
    System.out.print("Amount to mine: ");
    int amtMine = s.nextInt();
    Block b = bc.mine(amtMine);
    System.out.println("amount = " +amtMine + ", nonce = " + b.getNonce());
  }

  // Transferring money
  private static boolean transfer(BlockChain bc, UserMap users, Scanner s) {
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

		if (!users.userExists(toPublicKey)) {
			System.out.println("To user not found");
			return false;
		}

    System.out.print("Amount transferred: ");
    int amt = s.nextInt();

		if (users.getUser(fromPublicKey).getBalance() < amt) {
			System.out.println("Insufficient balance for transfer");
			return false;
		}

		// Calculate digital signature
		Block b = new Block(bc.getSize()+1, amt, bc.getHash());
		b.signBlock(fromPrivateKey);
		if (b.isValid(fromPublicKey)) {
			bc.append(b);
			return true;
		} else {
			System.out.println("Transaction block found invalid");
			return false;
		}
  }

	private static void printUsers(UserMap users) {
		System.out.println(users.toString());
	}

  private static void approveTransaction(BlockChain bc, Block app) {
		// TODO
    bc.append(app);
  }

  private static void checkBlockChain(BlockChain bc) {
    System.out.println(bc.toString());
    boolean valid = bc.isValidBlockChain();
    if (valid) {
      System.out.println("Chain is valid!");
    } else {
      System.out.println("Chain is not valid!");
    }
  }

  private static void createNewUser(UserMap users, Scanner s, BlockChain ledger) throws NoSuchAlgorithmException {
    System.out.print("Initial balance: ");
    int initBalance = s.nextInt();
    users.addUser(initBalance);
  }

  public static void printInfo() {
    System.out.println("Gif-coin is a digital crypto-currency that leverages");
    System.out.println("blockchain to create a centralized, safe, easy currency");
  }

  private static void parseInput(BlockChain bc, UserMap users, Scanner s, String cmd) throws NoSuchAlgorithmException {

    switch (cmd) {
    case "mine":
      mineBlock(bc, s);
      break;
    case "transfer":
      transfer(bc, users, s);
      break;
    case "report":
      // bc.printBalances();
      break;
    case "help":
      printHelp();
      break;
    case "info":
      printInfo();
      break;
    case "create user":
      createNewUser(users, s, bc);
      break;
		case "list users":
			printUsers(users);
			break;
    case "quit":
      break;
    default:
      System.out.println("Command not valid. Type 'help' to see list of commands.");
      break;
    }

  }

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

  private static void printWelcomeMessage() {
    System.out.println("");
    System.out.println("\n\n========================================================");
    System.out.println("Hello and welcome to Gif-Bank, the new standard in");
    System.out.println("crypto-currency. If you are new, you can type 'info' for");
    System.out.println("info or 'help' for a list of commands.");
    System.out.println("\nOtherwise, have fun!");
    System.out.println("========================================================");
  }

	public static void main(String[] args) throws NoSuchAlgorithmException {
    printWelcomeMessage();

		BlockChain bc = new BlockChain(50);
    UserMap users = new UserMap();
		runBlockChainLoop(bc, users);
	}

}
