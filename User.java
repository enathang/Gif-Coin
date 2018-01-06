public class User {
  String name;
  int balance;
  int[] nonces;
  BlockChain ledger;

  public User(String name, int initBalance, BlockChain ledger) {
    this.name = name;
    this.balance = initBalance;
    this.nonces = null;
    this.ledger = ledger;
  }

  public void updateLedger(BlockChain bc) {
    this.ledger = bc;
  }
  
}
