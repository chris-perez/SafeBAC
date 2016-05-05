import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.Users;
import controllers.routes;
import models.Drink;
import models.User;
import models.UserToUser;
import models.UserToDrink;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import play.Logger;
import play.api.libs.Crypto;
import play.libs.Json;
import play.mvc.Result;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.status;

/**
 * Created by Chris on 3/22/2016.
 */
public class UsersTest {
  String TEST_EMAIL = "c@v.com";
  String TEST_PASSWORD = "password";
  String TEST_NAME = "Baby Dragon";
  String TEST_SEX = "male";
  int TEST_WEIGHT = 160;
  int TEST_AGE = 18;
  String TEST_AUTH_TOKEN = Users.genID();

  String CHANGE_NAME = "Keith Stone";
  String CHANGE_SEX = "male";
  int CHANGE_WEIGHT = 200;
  int CHANGE_AGE = 21;

  ObjectNode request;
  JsonNode content;
  Result result;
  @Test
  public void testBAC() {

    running(fakeApplication(), new Runnable() {
      //add errors if blank entry or negative weight, etc, if it gets negative input, send back message to what took input, exceptions, border cases (0)
      private void checkBAC() {//unit testing
        User testBob = new User("Bob", "b@ob.com", "1234", "male", new DateTime(734799539), 170, "m");//create new User before String name, String email, String password, String sex, DateTime birthDate, int weight, String authI
        String x = testBob.getAuthID();
        testBob.setAuthID(x);
        assertThat(testBob.calculateBAC(x)).isEqualTo(0);//test user with no drinks in db

        UserToDrink u2d = new UserToDrink(testBob, new Drink("beer1", .05,"beer"), 60.0, new DateTime(DateTime.now().minusMinutes(210)));
        assertThat(testBob.calculateBAC(x)).isEqualTo(.07);//test male with drinks in db

        User testAnne = new User("Anne", "a@nne.com", "5678", "female", new DateTime(734799589), 130, "f");//create new User before String name, String email, String password, String sex, DateTime birthDate, int weight, String authI
        String y = testAnne.getAuthID();
        testAnne.setAuthID(y);
        assertThat(testAnne.calculateBAC(x)).isEqualTo(0);//test second user with no drinks in db

        UserToDrink u2d2 = new UserToDrink(testAnne, new Drink("beer1", .05,"beer"), 60.0, new DateTime(DateTime.now().minusMinutes(210)));
        assertThat(testAnne.calculateBAC(x)).isEqualTo(.13);//test female with drinks in db

        UserToDrink u2d3 = new UserToDrink(testAnne, new Drink("beer1", .05,"beer"), 60.0, new DateTime(DateTime.now().minusMinutes(210)));
        assertThat(testAnne.calculateBAC(x)).isEqualTo(.31);//test female with drinks in db

      }
      @Override
      public void run() {
        checkBAC();
      }
    });
  }

  private void validateUser(JsonNode content) {
    assertThat(content.has("id")).isTrue();
    assertThat(content.has("authID")).isTrue();
    assertThat(content.get("authID")).isNotEqualTo(null);
    assertThat(content.has("name")).isTrue();
    assertThat(content.has("email")).isTrue();
    assertThat(content.has("sex")).isTrue();
    assertThat(content.has("weight")).isTrue();
    assertThat(content.has("birthDate")).isTrue();
  }

  @Test
  public void createUser() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        // Create User
        request = Json.newObject()
            .put("email", TEST_EMAIL)
            .put("password", TEST_PASSWORD)
            .put("name", TEST_NAME)
            .put("birthDate", 0)
            .put("sex", TEST_SEX)
            .put("weight", TEST_WEIGHT);

        result = callAction(controllers.routes.ref.Users.createUser(),
            fakeRequest().withJsonBody(request));
        Logger.info("Create User Result: " + contentAsString(result));
        assertThat(status(result)).isEqualTo(OK);
        content = Json.parse(contentAsString(result));
        validateUser(content);
      }
    });
  }

  @Test
  public void loginUser() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User u = new User("name", TEST_EMAIL, Crypto.sign(TEST_PASSWORD), TEST_SEX, DateTime.now(), TEST_WEIGHT, TEST_AUTH_TOKEN);
        // Login
        request = Json.newObject()
            .put("email", TEST_EMAIL)
            .put("password", TEST_PASSWORD);
        result = callAction(controllers.routes.ref.Users.loginUser(),
            fakeRequest().withJsonBody(request));
        Logger.info("Login Result: " + contentAsString(result));
        assertThat(status(result)).isEqualTo(OK);
        content = Json.parse(contentAsString(result));
        validateUser(content);
      }
    });
  }

  @Test
  public void getProfile() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User u = new User("name", TEST_EMAIL, TEST_PASSWORD, TEST_SEX, DateTime.now(), TEST_WEIGHT, TEST_AUTH_TOKEN);
        // Login
        request = Json.newObject()
            .put("email", TEST_EMAIL)
            .put("name", CHANGE_NAME)
            .put("age", CHANGE_AGE)
            .put("sex", CHANGE_SEX)
            .put("weight", CHANGE_WEIGHT);
        result = callAction(controllers.routes.ref.Users.getProfile(),
            fakeRequest().withHeader("X-Auth-Token", TEST_AUTH_TOKEN));
        Logger.info("Update Profile Result: " + contentAsString(result));
        assertThat(status(result)).isEqualTo(OK);
        JsonNode response = Json.parse(contentAsString(result));
        assertThat(response.has("email"));
        assertThat(response.has("name"));
        assertThat(response.has("birthDate"));
        assertThat(response.has("sex"));
        assertThat(response.has("weight"));
      }
    });
  }

  @Test
  public void updateProfile() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User u = new User("name", TEST_EMAIL, TEST_PASSWORD, TEST_SEX, DateTime.now(), TEST_WEIGHT, TEST_AUTH_TOKEN);
        // Login
        request = Json.newObject()
            .put("email", TEST_EMAIL)
            .put("name", CHANGE_NAME)
            .put("birthDate", DateTime.now().minusYears(21).getMillis())
            .put("sex", CHANGE_SEX)
            .put("weight", CHANGE_WEIGHT);
        result = callAction(controllers.routes.ref.Users.updateProfile(),
            fakeRequest().withHeader("X-Auth-Token", TEST_AUTH_TOKEN).withJsonBody(request));
        Logger.info("Update Profile Result: " + contentAsString(result));
        assertThat(status(result)).isEqualTo(OK);
      }
    });
  }

  @Test
  public void addFriend() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, TEST_AUTH_TOKEN);
        User user2 = new User("name2", "email2", "password2", "female", DateTime.now(), 120, "authID2");

        result = callAction(controllers.routes.ref.Users.addFriend(user2.getEmail()),
            fakeRequest().withHeader("X-Auth-Token", TEST_AUTH_TOKEN));
        assertThat(status(result)).isEqualTo(OK);
      }
    });
  }

  @Test
  public void setBACVisibleToFriend() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, TEST_AUTH_TOKEN);
        User user2 = new User("name2", "email2", "password2", "female", DateTime.now(), 120, "authID2");

        result = callAction(controllers.routes.ref.Users.setBACVisibleToFriend(user2.getID(), true),
            fakeRequest().withHeader("X-Auth-Token", TEST_AUTH_TOKEN));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);

        new UserToUser(user1, user2);
        result = callAction(controllers.routes.ref.Users.setBACVisibleToFriend(user2.getID(), true),
            fakeRequest().withHeader("X-Auth-Token", TEST_AUTH_TOKEN));
        assertThat(status(result)).isEqualTo(NO_CONTENT);
      }
    });
  }

  @Test
  public void getFriends() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, TEST_AUTH_TOKEN);
        User user2 = new User("name2", "email2", "password2", "female", DateTime.now(), 120, "authID2");
        User user3 = new User("name3", "email3", "password3", "female", DateTime.now(), 140, "authID3");
        new UserToUser(user1, user2);
        new UserToUser(user1, user3);
        user2.setBACVisibleToFriend(user1, true);
        result = callAction(controllers.routes.ref.Users.getFriends(),
            fakeRequest().withHeader("X-Auth-Token", TEST_AUTH_TOKEN));
        assertThat(status(result)).isEqualTo(OK);

        content = Json.parse(contentAsString(result));
        assertThat(content.isArray()).isTrue();
        Iterator<JsonNode> friendIterator = content.elements();
        while (friendIterator.hasNext()) {
          JsonNode node = friendIterator.next();
          assertThat(node.has("id")).isTrue();
          assertThat(node.has("name")).isTrue();
          assertThat(node.has("visible")).isTrue();
          if (node.get("id").asLong() == user2.getID()) {
            assertThat(node.has("bac")).isTrue();
          }
        }
      }
    });
  }

  @Test
  public void logoutUser() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User u = new User("name", TEST_EMAIL, TEST_PASSWORD, TEST_SEX, DateTime.now(), TEST_WEIGHT, TEST_AUTH_TOKEN);
        // Logout
        result = callAction(controllers.routes.ref.Users.logoutUser(),
            fakeRequest().withHeader("X-Auth-Token", TEST_AUTH_TOKEN));
        assertThat(status(result)).isEqualTo(NO_CONTENT);
        User user = User.find.where().eq("email", TEST_EMAIL).findUnique();
        assertThat(user.getAuthID()).isNull();
      }
    });
  }
}
