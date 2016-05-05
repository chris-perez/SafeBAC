package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.h2.mvstore.cache.CacheLongKeyLIRS;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
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
    node.put("birthDate", birthDate.getMillis());
    node.put("weight", weight);
    node.put("bac", getBAC());
    return node;
  }

  /**
   * @return Json ObjectNode that contains necessary info about the user that can be seen by friends
   */
  public ObjectNode toFriendJson() {
    ObjectNode node = Json.newObject();
    node.put("id", id);
    node.put("name", name);
    return node;
  }

  /**
   * @return current BAC of the user
   */
  public double getBAC() {
    //TODO: Swap with real BAC
    return .08;
  }

  public List<UserToDrink> getDrinksAfter(DateTime time) {
    return UserToDrink.find.where().eq("user", this).ge("time", time).orderBy("time desc").findList();
  }

//must write update hours and update alcohol ounces consumed function -> how fast does alcohol filter out of system
  //^whenever in app or whenever new drink? -> whenever checks it -> runs this function
  //is graph of BAC over time or oz's of alcohol? Second easier
  //ounces changeable by person (pint, small glass, next to amounts)
  //alcConsumed = percentage * total ounces drank -> alcohol ounces drank
  //static method belongs to class not to any user
  //4/23 must account for exceptions like not sending something to function or having impossible bac
  /*BAC calculation
  returns user's current BAC
   */
  //LocalDateTime.now()
  //DateTime() timestamp
  //  List<UserToDrink> userToDrinks;

  public int calculateBAC(String id) {
    if(userToDrinks.isEmpty()) {//if no drinks in list
      return 0;
    }

    DateTime timeNow = DateTime.now();

    List<UserToDrink> userHasDrunk = getDrinksAfter(timeNow.minusHours(4));//returns list of drinks that user has drunk in last 4 hours (average filters out in 2 or so, this is safe estimate)
    Minutes timePassed = Minutes.minutesBetween(userHasDrunk.get(0).time,timeNow);//calculates how long user has been drinking within that time
    int minutesPassed =  timePassed.getMinutes();//gets minutes passed in this period
    double hoursPassed = minutesPassed / 60;//converts to hours

    if(userHasDrunk.isEmpty()){//if no drinks in past 4 hours
      return 0;
    }

    double ouncesAlcConsumed = 0;
    int lenList = userHasDrunk.size();
    int x = 0;
    while(x < lenList){
      ouncesAlcConsumed += userHasDrunk.get(x).volume * userHasDrunk.get(0).drink.abv;
      x++;
    }

    double sexRatio;
    if(sex.equals("female")) {
      sexRatio = .66;
    }else{
      sexRatio = .73;
    }

    double bac = ((ouncesAlcConsumed * 5.14)/(weight * sexRatio)) - (.015 * hoursPassed);//alcohol burns off at about .015 an hour
    int bacPercentage = (int)(bac*100);
    return bacPercentage;
  }
  /**
   * Adds a user as a friend to userToUsers
   * @param friend user to be added as friend
   */
  public void addFriend(User friend) {
    if (UserToUser.exists(this, friend))
      return;

    UserToUser u2u = new UserToUser(this, friend);
  }

  /**
   * Gets a list of Users that are added as friends
   * @return
   */
  public List<User> getFriends() {
    List<User> friends = new ArrayList<>();
    for (UserToUser u2u: UserToUser.findByUser(this)) {
      if (u2u.getUser1().equals(this)) {
        friends.add(u2u.getUser2());
      } else {
        friends.add(u2u.getUser1());
      }
    }
    return friends;
  }

  /**
   * Sets if user is visible to friend;
   * @param friend friend to set visibility for
   * @param visible whether or not the user's BAC should be visible to friend
   */
  public void setBACVisibleToFriend(User friend, boolean visible) {
    UserToUser u2u = UserToUser.findByUsers(this, friend);
    if (this.equals(u2u.getUser1())) {
      u2u.setUser1IsVisible(visible);
    } else {
      u2u.setUser2IsVisible(visible);
    }
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

  public Long getID() {
    return id;
  }
}
