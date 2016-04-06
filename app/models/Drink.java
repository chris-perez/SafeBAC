package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by chris_000 on 3/22/2016.
 */
@Entity
public class Drink extends Model {
  @Id
  Long id;
  String name;
  Double abv; //alcohol by volume

  private Drink(String name, double abv) {
    this.name = name;
    this.abv = abv;
    this.save();
  }
  
  public ObjectNode toJson() {
    ObjectNode node = Json.newObject();
    node.put("name", name);
    node.put("abv", abv);
    return node;
  }

  public static class DrinkBuilder {
    private String name;
    private double abv;

    public DrinkBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public DrinkBuilder setAbv(double abv) {
      this.abv = abv;
      return this;
    }

    public Drink build() {
          return new Drink(name, abv);
      }
  }
}