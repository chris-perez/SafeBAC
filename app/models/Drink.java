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
  String type;

  public static Finder<Long, Drink> find = new Finder<>(Long.class, Drink.class);

  public Drink(String name, double abv, String type) {
    this.name = name;
    this.abv = abv;
    this.type = type;
    this.save();
  }

  public ObjectNode toJson() {
    ObjectNode node = Json.newObject();
    node.put("id", id);
    node.put("name", name);
    node.put("abv", abv);
    node.put("type", type);
    return node;
  }

  public static Drink findByID(Long id) {
    return find.byId(id);
  }

  public static Drink findByName(String name) {
    return find.where().eq("name", name).findUnique();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Double getAbv() {
    return abv;
  }

  public String getType() {
    return type;
  }
}