import java.security.NoSuchAlgorithmException;

public class User {
  // Doubles as the address, because there's little need to guard against elliptic curve failures
  String publicKey;
  String secretKey;
  BlockChain ledger;
  int balance;

  public User(String secretKey) {
    this.secretKey = secretKey;
    this.publicKey = generatePublicKey(secretKey);
    this.ledger = new BlockChain(0);
    this.balance = 0;
  }

  public String generatePublicKey(String privateKey) {
    byte[] bytes = privateKey.getBytes();
    int publicKey = 0;
    int g = Math.random(10); // Substitution for the secp256k1 curve
    int multiplier = 1;

    for (byte b : bytes) {
       int val = b;
       for (int i = 0; i < 8; i++) {
          int binary = ((val & 128) == 0 ? 0 : 1);
          val <<= 1;
          publicKey += binary*g*multiplier;
          multiplier *= 2;
       }
    }

    return Integer.toString(publicKey);
  }

  private boolean blockIsValid(Block b) {
    // check public and private key
    // check the public keys match
    // mine it
    return true;
  }

  /**
   * Creates a new user with a given name, initial balance, and ledger
   *
   * @param  name the name of the user
   * @param  initBalance the initial balance of the user (usually 0)
   * @param  ledger the most recent ledger
   */
  public User(String secretKey, int initBalance) {
    this.secretKey = secretKey;
    this.balance = initBalance;
    this.ledger = new BlockChain(0);
  }

  /**
   * Updates the user's ledger to the new blockchain passed
   *
   * @param  bc the new ledger
   */
  public void updateLedger(BlockChain bc) {
    if (bc.getSize() > this.size) {
      this.ledger = bc;
    }
  }

  public String toString() {
    return "Public key: " + publicKey + ", balance: " + balance + "\n";
  }

}
