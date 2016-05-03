package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by Chris on 3/27/2016.
 */
@Entity
public class UserToUser extends Model {
  @Id
  Long id;
  @ManyToOne
  User user1;
  User user2;
  Boolean user1IsVisible = false;
  Boolean user2IsVisible = false;

  public static Finder<Long, UserToUser> find = new Finder<>(Long.class, UserToUser.class);

  public UserToUser(User user1, User user2) {
    this.user1 = user1;
    this.user2 = user2;
    this.save();
  }

  public Long getId() {
    return id;
  }

  public Boolean getUser1IsVisible() {
    return user1IsVisible;
  }

  public void setUser1IsVisible(Boolean user1IsVisible) {
    this.user1IsVisible = user1IsVisible;
  }

  public Boolean getUser2IsVisible() {
    return user2IsVisible;
  }

  public void setUser2IsVisible(Boolean user2IsVisible) {
    this.user2IsVisible = user2IsVisible;
  }
}
