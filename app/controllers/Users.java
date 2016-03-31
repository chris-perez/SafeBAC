package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.Logger;
import play.api.libs.Codecs;
import play.libs.Crypto;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import plugins.MersenneGeneratorPlugin;

/**
 * Created by chris_000 on 3/22/2016.
 */
public class Users extends Controller {
  public static final ObjectNode NO_TOKEN = Json.newObject().put("error", "There was no session associated with that request");
  public static final ObjectNode NO_SESSION = Json.newObject().put("error", "There is no current session using that key");
  public static final ObjectNode INCORRECT_FIELDS = Json.newObject().put("error", "Incorrect fields.");
  public static final ObjectNode INCORRECT_EMAIL = Json.newObject().put("error", "Incorrect email");
  public static final ObjectNode INCORRECT_PASSWORD = Json.newObject().put("error", "Incorrect password");
  public static final ObjectNode ACCOUNT_EXISTS = Json.newObject().put("error", "Account already exists.");
  public static final ObjectNode INVALID_TOKEN = Json.newObject().put("error", "Invalid token.");


  /**
   * Creates a new user in the database if it does not already exits.
   * @return the new user
   */
  public static Result createUser() {
    response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    JsonNode body = request().body().asJson();

    if (body == null || body.size() < 6) {
      return badRequest(INCORRECT_FIELDS);
    }
    User u;
    User.UserBuilder userBuilder = new User.UserBuilder();

    if (body.has("email") && body.has("password") && body.has("age") && body.has("name") && body.has("sex")
        && body.has("weight")) {
      //TODO: Add email requirements.
      String email = body.get("email").asText();
      if (User.userExists(email)) {
        return badRequest(ACCOUNT_EXISTS);
      }
      //TODO: add password requirements
      userBuilder = userBuilder.setEmail(email)
          .setPassword(Crypto.sign(body.get("password").asText()))
          .setAge(body.get("age").asInt())
          .setName(body.get("name").asText())
          .setWeight(body.get("weight").asInt())
          .setSex(body.get("sex").asText());

      u = userBuilder.setAuthID(genID()).build();
    }else{
      return badRequest(INCORRECT_FIELDS);
    }

    ObjectNode json = u.toJson();
    json.put("authID", u.authID);
    return ok(json);
  }

  public static Result changeInfoUser() {
    response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    JsonNode body = request().body().asJson();

    if (body == null || body.size() < 6) {
      return badRequest(INCORRECT_FIELDS);
    }
    User u;
    User.UserBuilder userBuilder = new User.UserBuilder();

    if (body.has("email") && body.has("password") && body.has("age") && body.has("name") && body.has("sex")
            && body.has("weight")) {
      //TODO: Add email requirements.
      String email = body.get("email").asText();
      if (User.userExists(email)) {
        return badRequest(ACCOUNT_EXISTS);
      }
      //TODO: add password requirements
      userBuilder = userBuilder.setEmail(email)
              .setPassword(Crypto.sign(body.get("password").asText()))
              .setAge(body.get("age").asInt())
              .setName(body.get("name").asText())
              .setWeight(body.get("weight").asInt())
              .setSex(body.get("sex").asText());

      u = userBuilder.setAuthID(genID()).build();
    }else{
      return badRequest(INCORRECT_FIELDS);
    }

    ObjectNode json = u.toJson();
    json.put("authID", u.authID);
    return ok(json);
  }

  /**
   * Logs in the user if the user exists.
   * @return AuthID of the session.
   */
  public static Result loginUser() {
    response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    JsonNode body = request().body().asJson();
    User u;

    if (body == null || body.size() < 2) {
      return badRequest(INCORRECT_FIELDS);
    }

    if (body.has("email") && body.has("password")) {    // Regular Login
      String email = body.get("email").asText();
      String password = body.get("password").asText();
      password = Crypto.sign(password);
      if (!User.userExists(email)) {
        return badRequest(INCORRECT_EMAIL);
      }

      u = User.fromEmail(email);
      if (u.password.equals(password)) {
        if (u.authID == null) {
          u.authID = genID();
        }
      } else {
        return badRequest(INCORRECT_PASSWORD);
      }
    }else {
      return badRequest(INCORRECT_FIELDS);
    }

    u.update();
    ObjectNode json = u.toJson();
    json.put("authID", u.authID);
    return ok(json);
  }

  public static String genID() {
    byte[] randomValue = new  byte[25];
    MersenneGeneratorPlugin.generator.nextBytes(randomValue);
    return Codecs.toHexString(randomValue);
  }
}
