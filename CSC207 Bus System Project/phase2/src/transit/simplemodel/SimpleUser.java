package transit.simplemodel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import transit.UserAccount;

public class SimpleUser {

  private final SimpleStringProperty name;
  private final SimpleStringProperty email;
  private final SimpleStringProperty password;
  private final SimpleBooleanProperty isAdmin;

  public SimpleUser(UserAccount user) {
    name = new SimpleStringProperty(user.getName());
    email = new SimpleStringProperty(user.getEmail());
    password = new SimpleStringProperty(user.getPassword());
    isAdmin = new SimpleBooleanProperty(user.isAdmin());
  }

  public String getName() {
    return name.get();
  }

  public String getEmail() {
    return email.get();
  }

  public String getPassword() {
    return password.get();
  }

  public boolean getIsAdmin() {
    return isAdmin.get();
  }

}
