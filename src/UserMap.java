import java.util.Map;
import java.util.HashMap;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * A HashMap<sk, User> of GifCoin users for the bank
 */
public class UserMap {
  Map<PublicKey, User> userMap;

/**
 * Creates an empty usermap
 */
  public UserMap() {
    this.userMap = new HashMap<PublicKey, User>();
  }

/**
 * Adds a user to the usermap
 *
 * @param  name the name of the user to add
 * @param  initBalance the initial balance of the user (if any)
 */
  public void addUser() throws NoSuchAlgorithmException {
    User newUser = new User();
    userMap.put(newUser.getPublicKey(), newUser);
  }
  /**
   * Adds a user to the usermap
   *
   * @param  name the name of the user to add
   * @param  initBalance the initial balance of the user (if any)
   */
    public void addUser(int balance) throws NoSuchAlgorithmException {
      User newUser = new User(balance);
      userMap.put(newUser.getPublicKey(), newUser);
    }

  /**
   * Returns the user from the map that has the given name (or null)
   *
   * @param  name the name of the user
   * @return      the user that corresponds to the given name
   */
  public User getUser(PublicKey publicKey) {
    return userMap.get(publicKey);
  }

  public boolean userExists(PublicKey publicKey) {
    return (userMap.get(publicKey) == null);
  }

  /**
   * Updates the ledgers of all the users of the bank
   *
   * @param  bc the new blockchain
   */
  public void updateLedgers(BlockChain bc) {
    for (PublicKey key : userMap.keySet()) {
      userMap.get(key).updateLedger(bc);
    }
  }

  public String toString() {
    String ret = "";
    for (PublicKey key : userMap.keySet()) {
      ret += userMap.get(key).toString();
    }

    return ret;
  }

}
