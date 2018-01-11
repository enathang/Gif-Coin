import java.security.*;

public class User {
  PublicKey publicKey;
  PrivateKey secretKey;
  BlockChain ledger;
  int balance;

  /**
   * Creates a new user by randomly generating a public and secret key and
   * an empty ledger
   *
   * @throws NoSuchAlgorithmException If java.security cannot find the crypto algorithm
   */
  public User() throws NoSuchAlgorithmException {
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    keyGen.initialize(256, random);

    KeyPair pair = keyGen.generateKeyPair();
    secretKey = pair.getPrivate();
    publicKey = pair.getPublic();

    this.ledger = new BlockChain(0);
    this.balance = 0;
  }

  /**
   * Creates a new user by randomly generating a public and secret key, an
   * empty ledger, and an initial balance
   *
   * @param  initBalance the initial balance of the user
   * @throws NoSuchAlgorithmException If java.security cannot find the crypto algorithm
   */
  public User(int initBalance) throws NoSuchAlgorithmException {
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    keyGen.initialize(256, random);

    KeyPair pair = keyGen.generateKeyPair();
    secretKey = pair.getPrivate();
    publicKey = pair.getPublic();

    this.ledger = new BlockChain(0);
    this.balance = initBalance;
  }

  /**
   * Returns the balance of the user
   *
   * @return the user's balance
   */
  public int getBalance() {
    return this.balance;
  }

  /**
   * Updates the user's ledger to the new blockchain passed if there is
   * more proof of work
   *
   * @param  bc the new ledger
   */
  public void updateLedger(BlockChain bc) {
    if (bc.getSize() > this.ledger.getSize()) {
      this.ledger = bc;
    }
  }

  /**
   * Returns the public key of the user
   *
   * @return the user's public key
   */
  public PublicKey getPublicKey() {
    return publicKey;
  }

  /**
   * Converts the user instance into a readable string
   *
   * @return a string that contains all the user data
   */
  public String toString() {
    KeyStringConverter conv = new KeyStringConverter();
    return "Public key: \n\n" + conv.publicKeyToString(publicKey)
      + "\n\nPrivate key(for debugging):\n\n" + conv.privateKeyToString(secretKey)
      + "\n\nBalance: " + balance + "\n\n";
  }

}
