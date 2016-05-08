package models;

import com.avaje.ebean.Expr;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Chris on 3/27/2016.
 */
@Entity
public class UserToUser extends Model {
  @Id
  Long id;
  @ManyToOne
  User user1;
  @ManyToOne
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

  public User getUser1() {
    return user1;
  }

  public User getUser2() {
    return user2;
  }

  /**
   * @return if user1's bac is visible to user2
   */
  public Boolean getUser1IsVisible() {
    return user1IsVisible;
  }

  /**
   * @param user1IsVisible if user1's bac is visible to user2
   */
  public void setUser1IsVisible(Boolean user1IsVisible) {
    this.user1IsVisible = user1IsVisible;
    this.save();
  }

  /**
   * @return if user2's bac is visible to user1
   */
  public Boolean getUser2IsVisible() {
    return user2IsVisible;
  }

  /**
   * @param user2IsVisible if user2's bac is visible to user1
   */
  public void setUser2IsVisible(Boolean user2IsVisible) {
    this.user2IsVisible = user2IsVisible;
    this.save();
  }

  /**
   * Finds if a UserToUser exists given the two users.
   * @param user1 first user
   * @param user2 second user
   * @return if a UserToUser exists
   */
  public static boolean exists(User user1, User user2) {
    return find.where().or(
        Expr.and(
            Expr.eq("user1", user1),
            Expr.eq("user2", user2)
        ),
        Expr.and(
            Expr.eq("user2", user1),
            Expr.eq("user1", user2)
        )
    ).findRowCount() > 0;
  }

  /**
   * Returns a UserToUser given the two users
   * @param user1 first user
   * @param user2 second user
   * @return UserToUser matching te two users
   */
  public static UserToUser findByUsers(User user1, User user2) {
    return find.where().or(
        Expr.and(
            Expr.eq("user1", user1),
            Expr.eq("user2", user2)
        ),
        Expr.and(
            Expr.eq("user2", user1),
            Expr.eq("user1", user2)
        )
    ).findUnique();
    /*UserToUser u2u = find.where().eq("user1", user1).eq("user2", user2).findUnique();
    if (u2u == null) {
      u2u = find.where().eq("user1", user2).eq("user2", user1).findUnique();
    }
    return u2u;*/
  }

  /**
   * Gets a list of UsersToUser that contain the given user
   * @param user user to search for
   * @return list of UserToUsers
   */
  public static List<UserToUser> findByUser(User user) {
    return find.where().or(
        Expr.eq("user1", user),
        Expr.eq("user2", user)
    ).findList();
  }
}
