package models;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Chris on 3/27/2016.
 */
@Entity
public class UserToUser {
  @Id
  public Long id;
  public User user1;
  public User user2;
  public Boolean user1IsVisible = false;
  public Boolean user2IsVisible = false;

  public UserToUser(User user1, User user2) {
    this.user1 = user1;
    this.user2 = user2;
  }
}
