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
  public Long id;
  public String name;
  public double percentAlcohol;
}
