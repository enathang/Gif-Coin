public class User {
  String name;
  int balance;
  int[] nonces;
  BlockChain ledger;

  /**
   * Creates a new user with a given name, initial balance, and ledger
   *
   * @param  name the name of the user
   * @param  initBalance the initial balance of the user (usually 0)
   * @param  ledger the most recent ledger
   */
  public User(String name, int initBalance, BlockChain ledger) {
    this.name = name;
    this.balance = initBalance;
    this.nonces = null;
    this.ledger = ledger;
  }

  /**
   * Updates the user's ledger to the new blockchain passed
   *
   * @param  bc the new ledger
   */
  public void updateLedger(BlockChain bc) {
    this.ledger = bc;
  }

}
