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

  /**
   * @return JsonNode of important information.
   */
  public ObjectNode toJson() {
    ObjectNode node = Json.newObject();
    node.put("id", id);
    node.put("name", name);
    node.put("abv", abv);
    node.put("type", type);
    return node;
  }

  /**
   * Gets a drink by its id
   * @param id id of the drink to be found
   * @return drink with given id
   */
  public static Drink findByID(Long id) {
    return find.byId(id);
  }

  /**
   * Gets a drink by its name
   * @param name name of drink to be found
   * @return drink with given name
   */
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