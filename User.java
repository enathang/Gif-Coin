public class User {
  String name;
  int balance;
  int[] nonces;

  public User(String name, int initBalance) {
    this.name = name;
    this.balance = initBalance;
    this.nonces = null;
  }
}
