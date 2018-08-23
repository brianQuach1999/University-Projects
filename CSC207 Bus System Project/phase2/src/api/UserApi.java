package api;

import java.util.ArrayList;
import transit.StatisticsManager;
import transit.TransitFareManager;
import transit.Trip;
import transit.UserAccount;
import transit.simplemodel.SimpleTrip;
import transit.simplemodel.SimpleUser;
import util.EasyLogger;

/**
 * Class to handle user-related API queries.
 */
public class UserApi extends ChildApi {

  public UserApi(TransitFareManager transitFareManager, StatisticsManager statisticsManager,
      EasyLogger logger) {
    super(transitFareManager, statisticsManager, logger);
  }

  /**
   * Create new user with given name, email, and admin status isAdmin. Throws error when name or
   * email are empty, or when email is already in use.
   *
   * @param name name of new user
   * @param email email of new user
   * @param isAdmin whether new user should be admin
   * @return instance of user
   * @throws CreateUserException could not create user
   */
  public UserAccount create(String name, String email, boolean isAdmin) throws CreateUserException {
    if (name.isEmpty() || email.isEmpty() || (transitFareManager.getUserByEmail(email) != null)) {
      logger.log.warning("Failed to create new user");
      throw new CreateUserException();
    }
    return transitFareManager.createUserAccount(name, email, isAdmin);
  }

  /**
   * Update the name of the user who has the specified email.
   *
   * @param email email of user to be updated
   * @param name new name of user
   * @throws UpdateUserException could not find user with that email
   */
  public void updateName(String email, String name) throws UpdateUserException {
    UserAccount user = transitFareManager.getUserByEmail(email);
    if (user != null) {
      user.setName(name);
    } else {
      throw new UpdateUserException();
    }
  }

  /**
   * Update the password of the user who has the specified email.
   *
   * @param email email of user to be updated
   * @param password new password of user
   * @throws UpdateUserException could not find user
   */
  public void updatePassword(String email, String password) throws UpdateUserException {
    UserAccount user = transitFareManager.getUserByEmail(email);
    if (user != null) {
      user.setPassword(password);
    } else {
      throw new UpdateUserException();
    }
  }

  /**
   * Update user with email oldEmail. Fails if any of oldEmail, name, newEmail, password are empty.
   * Also fails if the email is being updated to an email that is had by an existing user.
   *
   * @param oldEmail email of user to be updated
   * @param name new name of user
   * @param newEmail new email of user
   * @param password new password of user
   * @param isAdmin whether user should be admin or not
   * @throws UpdateUserException could not update user
   */
  public void update(String oldEmail, String name, String newEmail, String password,
      boolean isAdmin)
      throws UpdateUserException {
    if (oldEmail.isEmpty() || name.isEmpty() || newEmail.isEmpty() || password.isEmpty() || (
        (transitFareManager.getUserByEmail(newEmail) != null) && (!newEmail.equals(oldEmail)))) {
      logger.log.warning("Failed to update user with email " + oldEmail);
      throw new UpdateUserException();
    }
    transitFareManager.updateUser(oldEmail, name, newEmail, password, isAdmin);
  }

  /**
   * Delete user which has specified email.
   *
   * @param email email of user to be deleted
   */
  public void delete(String email) {
    transitFareManager.deleteUser(email);
  }

  /**
   * Get list of SimpleUser instances corresponding to list of all existing users.
   *
   * @return list of SimpleUser
   */
  public ArrayList<SimpleUser> getSimple() {
    ArrayList<UserAccount> users = transitFareManager.getUsers();
    ArrayList<SimpleUser> result = new ArrayList<>();
    for (UserAccount user : users) {
      result.add(new SimpleUser(user));
    }
    return result;
  }

  /**
   * Log in a user with email and password.
   *
   * @param email email of user to log in
   * @param password password of user to log in
   * @return instance of user which has been logged in
   * @throws LoginFailedException bad username and password combo
   */
  public UserAccount login(String email, String password) throws LoginFailedException {
    try {
      UserAccount user = transitFareManager.getUserByEmail(email);
      if (user != null && user.validatePassword(password)) {
        logger.log.fine("Successfully logged in user with email " + email);
        return user;
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      logger.log.warning("Failed failed to log in user with email " + email);
      throw new LoginFailedException();
    }
  }

  /**
   * Get list of SimpleTrips corresponding to 3 recent trips.
   */
  public ArrayList<SimpleTrip> getTrips(UserAccount user) {
    ArrayList<SimpleTrip> trips = new ArrayList<>();
    for (Trip trip : user.calculateRecentTrips()) {
      trips.add(new SimpleTrip(trip));
    }
    return trips;
  }

}
