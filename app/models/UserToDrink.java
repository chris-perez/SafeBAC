package models;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Chris on 3/27/2016.
 */
@Entity
public class UserToDrink {
  @Id
  public Long id;
  public User user;
  public Drink drink;
  public DateTime time;
  public double volume;
}
