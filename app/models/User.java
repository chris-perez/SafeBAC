package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by chris_000 on 3/21/2016.
 */
@Entity
public class User extends Model{
  @Id
  public Long id;
  public String name;
  public String email;
  public String password;
  public String sex;
  public Integer age;
  public Integer weight;
  public String authID;

  public static Finder<Long, User> find = new Finder<>(Long.class, User.class);


  /**
   * Searches for user in database by name
   *
   * @param email email of user to search for
   * @return if th user exists in the database
   */
  public static boolean userExists(String email) {
    return find.where().eq("email", email).findRowCount() > 0;
  }

  /**
   * @param email email to search for User
   * @return User containing given email
   */
  public static User fromEmail(String email) {
    return find.where().eq("email", email).findUnique();
  }

  public ObjectNode toJson() {
    ObjectNode node = Json.newObject();
    node.put("id", id);
    node.put("name", name);
    node.put("email", email);
    node.put("sex", sex);
    node.put("age", age);
    node.put("weight", weight);
    return node;
  }
  /**
   * put calcBAC function in here
   * */

  private User(String name, String email, String password, String sex, int age, int weight, String authID) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.sex = sex;
    this.age = age;
    this.weight = weight;
    this.authID = authID;
    this.save();
  }

  public static class UserBuilder {
    private String name;
    private String email;
    private String password;
    private String sex;
    private Integer age;
    private Integer weight;
    private String authID;

    public UserBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public UserBuilder setEmail(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder setPassword(String password) {
      this.password = password;
      return this;
    }

    public UserBuilder setSex(String sex) {
      this.sex = sex;
      return this;
    }

    public UserBuilder setAge(Integer age) {
      this.age = age;
      return this;
    }

    public UserBuilder setWeight(Integer weight) {
      this.weight = weight;
      return this;
    }

    public UserBuilder setAuthID(String authID) {
      this.authID = authID;
      return this;
    }

    public User build() {
      return new User(name, email, password, sex, age, weight, authID);
    }
  }
}
