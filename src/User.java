import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class User {
  PublicKey publicKey;
  PrivateKey secretKey;
  BlockChain ledger;
  int balance;

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
   * Creates a new user with a given name, initial balance, and ledger
   *
   * @param  name the name of the user
   * @param  initBalance the initial balance of the user (usually 0)
   * @param  ledger the most recent ledger
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

  public int getBalance() {
    return this.balance;
  }

  /**
   * Updates the user's ledger to the new blockchain passed
   *
   * @param  bc the new ledger
   */
  public void updateLedger(BlockChain bc) {
    if (bc.getSize() > this.ledger.getSize()) {
      this.ledger = bc;
    }
  }


  public PublicKey getPublicKey() {
    return publicKey;
  }

  public String toString() {
    return "Public key: " + publicKey.toString() + ", balance: " + balance + "\n";
  }

}
