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

  public static Finder<Long, Drink> find = new Finder<>(Long.class, Drink.class);

  public Drink(String name, double abv) {
    this.name = name;
    this.abv = abv;
    this.save();
  }

  public ObjectNode toJson() {
    ObjectNode node = Json.newObject();
    node.put("id", id);
    node.put("name", name);
    node.put("abv", abv);
    return node;
  }

  public static Drink findByID(Long id) {
    return find.byId(id);
  }

  public static Drink findByName(String name) {
    return find.where().eq("name", name).findUnique();
  }
}