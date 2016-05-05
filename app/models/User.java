package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris_000 on 3/21/2016.
 */
@Entity
public class User extends Model{
  @Id
  Long id;
  String name;
  String email;
  String password;
  String sex;
  DateTime birthDate;
  Integer weight;
  String authID;

  @OneToMany(mappedBy = "user")
  List<UserToDrink> userToDrinks = new ArrayList<>();

  @OneToMany(mappedBy = "user1")
  List<UserToUser> userToUsers = new ArrayList<>();

  public static Finder<Long, User> find = new Finder<>(Long.class, User.class);

  public User(String name, String email, String password, String sex, DateTime birthDate, int weight, String authID) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.sex = sex;
    this.birthDate = birthDate;
    this.weight = weight;
    this.authID = authID;

    this.save();
  }

  public void addDrink(Drink drink, Double volume) {
    UserToDrink u2d = new UserToDrink(this, drink, volume, DateTime.now());
    this.userToDrinks.add(u2d);
    u2d.save();
    this.save();
  }

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

  /**
   * Finds if there is a user that has the given authID
   * @param token authID to search for
   * @return true if there is a user with the given authID
   */
  public static boolean idExists(String token) {
    return find.where().eq("authID", token).findRowCount() > 0;
  }

  /**
   * Returns a user with the authID given
   * @param token authID to search for
   * @return User that matches the given authID
   */
  public static User fromAuthID(String token) {
    return find.where().eq("authID", token).findUnique();
  }

  /**
   * @return Json ObjectNode that contains necessary info about the user
   */
  public ObjectNode toJson() {
    ObjectNode node = Json.newObject();
    node.put("id", id);
    node.put("name", name);
    node.put("email", email);
    node.put("sex", sex);
    node.put("birthDate", birthDate.toString());
    node.put("weight", weight);
    return node;
  }
  //must write update hours and update alcohol ounces consumed function -> how fast does alcohol filter out of system
  //^whenever in app or whenever new drink?
  //is graph of BAC over time or oz's of alcohol? Second easier
  //catalog of drinks must have percentage of alcohol
  //ounces changeable by person (pint, small glass, next to amounts)
  //alcConsumed = percentage * total ounces drank -> alcohol ounces drank
  //user.getweight
  public static int calculateBAC(double weight, String id, String sex, double alcConsumed, double hoursPassed) { // Changed void to int
    double sexRatio;
    if(sex.equals("female")) {
      sexRatio = .66;
    }else{
      sexRatio = .73;
    }
    double bac = ((alcConsumed * 5.14)/(weight * sexRatio)) - (.015 * hoursPassed);//alcohol burns off at about .015 an hour
    int bacPercentage = (int)(bac*100);
    System.out.println("You have "+ bacPercentage+"% BAC");
    return bacPercentage; // Added return
  }

  public List<UserToDrink> getDrinkHistory() {
    return UserToDrink.find.where().eq("user", this).orderBy("time desc").findList();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public DateTime getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(DateTime birthDate) {
    this.birthDate = birthDate;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public String getAuthID() {
    return authID;
  }

  public void setAuthID(String authID) {
    this.authID = authID;
  }
}
