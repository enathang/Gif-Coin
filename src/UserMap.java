import java.util.Map;
import java.util.HashMap;
import java.security.NoSuchAlgorithmException;

/**
 * A HashMap<sk, User> of GifCoin users for the bank
 */
public class UserMap {
  Map<String, User> userMap;

/**
 * Creates an empty usermap
 */
  public UserMap() {
    this.userMap = new HashMap<String, User>();
  }

/**
 * Adds a user to the usermap
 *
 * @param  name the name of the user to add
 * @param  initBalance the initial balance of the user (if any)
 */
  public void addUser(String secretKey) {
    User newUser = new User(secretKey);
    userMap.put(secretKey, newUser);
  }
  /**
   * Adds a user to the usermap
   *
   * @param  name the name of the user to add
   * @param  initBalance the initial balance of the user (if any)
   */
    public void addUser(String secretKey, int balance) {
      User newUser = new User(secretKey, balance);
      userMap.put(secretKey, newUser);
    }

  /**
   * Returns the user from the map that has the given name (or null)
   *
   * @param  name the name of the user
   * @return      the user that corresponds to the given name
   */
  public User getUser(String privateKey) {
    return userMap.get(privateKey);
  }

  /**
   * Updates the ledgers of all the users of the bank
   *
   * @param  bc the new blockchain
   */
  public void updateLedgers(BlockChain bc) {
    for (String key : userMap.keySet()) {
      userMap.get(key).updateLedger(bc);
    }
  }

  public String toString() {
    String ret = "";
    for (String key : userMap.keySet()) {
      ret += userMap.get(key).toString();
    }

    return ret;
  }

}
