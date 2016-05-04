package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by Chris on 3/27/2016.
 */
@Entity
public class UserToDrink extends Model {
  @Id
  Long id;
  @ManyToOne
  User user;
  @ManyToOne
  Drink drink;
  Double volume;
  DateTime time;

  public static Finder<Long, UserToDrink> find = new Finder<>(Long.class, UserToDrink.class);

  public UserToDrink(User user, Drink drink, Double volume, DateTime time) {
    this.user = user;
    this.drink = drink;
    this.volume = volume;
    this.time = time;
    this.save();
  }

  public Long getId() {
    return id;
  }

  public Drink getDrink() {
    return drink;
  }

  public Double getVolume() {
    return volume;
  }

  public DateTime getTime() {
    return time;
  }
}
