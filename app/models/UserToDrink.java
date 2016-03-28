package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Chris on 3/27/2016.
 */
@Entity
public class UserToDrink extends Model {
  @Id
  public Long id;
  public User user;
  public Drink drink;
  public Double volume;
  public DateTime time;

  public static Finder<Long, UserToDrink> find = new Finder<>(Long.class, UserToDrink.class);

  public UserToDrink(User user, Drink drink, Double volume, DateTime time) {
    this.user = user;
    this.drink = drink;
    this.volume = volume;
    this.time = time;
    this.save();
  }
}
