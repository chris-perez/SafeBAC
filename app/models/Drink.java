package models;

import play.db.ebean.Model;

/**
 * Created by chris_000 on 3/22/2016.
 */
public class Drink extends Model{
  public String name;
  public double volume;
  public double percentAlcohol;
}
