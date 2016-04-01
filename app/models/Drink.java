package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by chris_000 on 3/22/2016.
 */
@Entity
public class Drink extends Model{
  @Id
  Long id;
  String name;
  Double percentAlcohol;
  public static Finder<Long, Drink> find = new Finder<>(Long.class, Drink.class);

  public Drink(String name, Double percentAlcohol) {
    this.name = name;
    this.percentAlcohol = percentAlcohol;
    this.save();
  }
}
