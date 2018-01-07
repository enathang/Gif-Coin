import java.util.Map;
import java.util.HashMap;

/**
 * A HashMap<Name, User> of GifCoin users for the bank
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
 * @param  initBalance the initial balance of the user(if any)
 */
  public void addUser(String name, int initBalance, BlockChain ledger) {
    User newUser = new User(name, initBalance, ledger);
    userMap.put(name, newUser);
  }

  /**
   * Returns the user from the map that has the given name (or null)
   *
   * @param  name the name of the user
   * @return      the user that corresponds to the given name
   */
  public User getUser(String name) {
    return userMap.get(name);
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

}
