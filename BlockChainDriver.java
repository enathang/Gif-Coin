import java.util.Scanner;

import java.security.NoSuchAlgorithmException;

public class BlockChainDriver {

	static void printHelp() {
		System.out.println("Valid commands:");
		System.out.println("    mine: discovers the nonce for a given transaction");
		System.out.println("    append: appends a new block onto the end of the chain");
		System.out.println("    remove: removes the last block from the end of the chain");
		System.out.println("    check: checks that the block chain is valid");
		System.out.println("    report: reports the balances of Alice and Bob");
		System.out.println("    help: prints this list of commands");
		System.out.println("    quit: quits the program");
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		int amt = Integer.parseInt(args[0]);

		Scanner s = new Scanner(System.in);
		BlockChain bc = new BlockChain(amt);

		String cmd = "";
		while (!cmd.equals("quit")) {
			System.out.println(bc.toString());
			System.out.println("Command? ");
			cmd = s.nextLine();
			switch (cmd) {
			case "mine":
				System.out.println("Amount transferred? ");
				int amtMine = s.nextInt();
				Block b = bc.mine(amtMine);
				System.out.println("amount = " +amtMine + ", nonce = " + b.getNonce());

				break;
			case "append":
				System.out.println("Amount transferred? ");
				int amtAppend = s.nextInt();
				System.out.println("Nonce? ");
				long nonce = s.nextInt();
				Block app = new Block(bc.getSize()+1, amtAppend, bc.getHash(), nonce);
				bc.append(app);
        
				break;
			case "remove":
				bc.removeLast();

				break;
			case "check":
				boolean valid = bc.isValidBlockChain();
				if (valid) {
					System.out.println("Chain is valid!");
				} else {
					System.out.println("Chain is not valid!");
				}

				break;
			case "report":
				bc.printBalances();

				break;
			case "help":
				printHelp();

				break;
			case "quit":

				break;
			}
		}
		s.close();
	}

}
