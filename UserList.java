import java.util.List;
import java.util.ArrayList;
public class UserList {
  List<User> userlist;

  public UserList() {
    this.userlist = new ArrayList<User>();
  }

  public void addUser(String name, int initBalance) {
    User newUser = new User(name, initBalance);
    userlist.add(newUser);
  }

}
