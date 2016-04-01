package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import org.joda.time.DateTime;
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

    if (body.has("email") && body.has("password") && body.has("birthDate") && body.has("name") && body.has("sex")
        && body.has("weight")) {
      //TODO: Add email requirements.
      String email = body.get("email").asText();
      if (User.userExists(email)) {
        return badRequest(ACCOUNT_EXISTS);
      }
      //TODO: add password requirements
      String name = body.get("name").asText();
      String password = Crypto.sign(body.get("password").asText());
      int weight = body.get("weight").asInt();
      String sex = body.get("sex").asText();
      if (!(sex.equals("male")) || sex.equals("female")) {
        return badRequest(INCORRECT_FIELDS);
      }

      try {
        DateTime birthDate = new DateTime(body.get("birthDate").asLong());
        u = new User(name, email, password, sex, birthDate, weight, genID());
      } catch (Exception e) {
        return badRequest(INCORRECT_FIELDS);
      }

    }else{
      return badRequest(INCORRECT_FIELDS);
    }

    ObjectNode json = u.toJson();
    json.put("authID", u.getAuthID());
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
      if (u.getPassword().equals(password)) {
        if (u.getAuthID() == null) {
          u.setAuthID(genID());
        }
      } else {
        return badRequest(INCORRECT_PASSWORD);
      }
    }else {
      return badRequest(INCORRECT_FIELDS);
    }

    u.update();
    ObjectNode json = u.toJson();
    json.put("authID", u.getAuthID());
    return ok(json);
  }

  /**
   * Logs out the user.
   * @return 200
   */
  public static Result logoutUser() {
    response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    User u = Users.fromRequest();
    if (u == null) {
      return unauthorized(NO_SESSION);
    }
    u.setAuthID(null);
    u.save();
    return noContent();
  }

  /**
   * Get a user by looking up the auth token associated with the request.
   */
  public static User fromRequest() {
    try {
      if (!request().hasHeader("X-Auth-Token")) {
        throw new Exception(NO_TOKEN.get("error").asText());
      }
      String token = request().getHeader("X-Auth-Token");
      if (!User.idExists(token)) {
        throw new Exception(NO_SESSION.get("error").asText());
      }
      return User.fromAuthID(token);
    } catch (Exception ex) {
      Logger.error(ex.getMessage(), ex.getCause());
      return null;
    }
  }

  /**
   * @return randomly generated authID
   */
  public static String genID() {
    byte[] randomValue = new  byte[25];
    MersenneGeneratorPlugin.generator.nextBytes(randomValue);
    return Codecs.toHexString(randomValue);
  }
}
