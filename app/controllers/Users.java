package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.Logger;
import play.libs.Crypto;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

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

    if (body == null || body.size() < 4) {
      return badRequest(INCORRECT_FIELDS);
    }
    User u;
    User.UserBuilder userBuilder = new User.UserBuilder();

    // TODO: If user logs in with FB/Google and email is already in system, just link accounts.
    if (body.has("email") && body.has("password") && body.has("birthdate")) {
      //TODO: Add email requirements.
      String email = body.get("email").asText();
      if (User.userExists(email)) {
        return badRequest(ACCOUNT_EXISTS);
      }
      userBuilder = userBuilder.setEmail(email);
      //TODO: add password requirements
      userBuilder = userBuilder.setPassword(Crypto.sign(body.get("password").asText()));
      userBuilder = userBuilder.setAge(body.get("age").asInt());

      u = userBuilder.authID(Util.genID()).build();
    }else{
      return badRequest(INCORRECT_FIELDS);
    }

    if (body.has("name")) {
      u.name = body.get("name").asText();
    }

    u.update();

    return ok(u.toJson());
  }
}
