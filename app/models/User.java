package models;

import play.db.ebean.Model;

/**
 * Created by chris_000 on 3/21/2016.
 */
public class User extends Model{
  public String name;
  public String email;
  public String password;
  public String sex;
  public int age;
  public int weight;

  private User(String name, String email, String password, String sex, int age, int weight) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.sex = sex;
    this.age = age;
    this.weight = weight;
  }

  public static class UserBuilder {

    private String name;
    private String email;
    private String password;
    private String sex;
    private int age;
    private int weight;

    public UserBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public UserBuilder setEmail(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder setPassword(String password) {
      this.password = password;
      return this;
    }

    public UserBuilder setSex(String sex) {
      this.sex = sex;
      return this;
    }

    public UserBuilder setAge(int age) {
      this.age = age;
      return this;
    }

    public UserBuilder setWeight(int weight) {
      this.weight = weight;
      return this;
    }

    public User createUser() {
      return new User(name, email, password, sex, age, weight);
    }
  }
}
