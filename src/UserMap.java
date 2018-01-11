import java.util.Map;
import java.util.HashMap;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class UserMap {
  Map<PublicKey, User> userMap;

  /**
   * Creates an empty usermap<PublicKey, User>
   */
  public UserMap() {
    this.userMap = new HashMap<PublicKey, User>();
  }

  /**
   * Creates a new user and adds them to the usermap
   */
  public void addUser() throws NoSuchAlgorithmException {
    User newUser = new User();
    userMap.put(newUser.getPublicKey(), newUser);
  }

  /**
   * Adds a user to the usermap
   *
   * @param  initBalance the initial balance of the user
   */
    public void addUser(int balance) throws NoSuchAlgorithmException {
      User newUser = new User(balance);
      userMap.put(newUser.getPublicKey(), newUser);
    }

  /**
   * Returns the user from the map that has the given public key
   *
   * @param  publicKey the public key of the user
   * @return      the user that corresponds to the given name (or null if not found)
   */
  public User getUser(PublicKey publicKey) {
    return userMap.get(publicKey);
  }

  /**
   * Returns whether of not the user is in the usermap
   *
   * @param  publicKey the public key of the user
   * @return whether or not the user is in the map, a boolean
   */
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

  /**
   * Returns a string version of the user map
   *
   * @return the user map in string form
   */
  public String toString() {
    String ret = "";
    for (PublicKey key : userMap.keySet()) {
      ret += userMap.get(key).toString();
    }

    return ret;
  }

}
