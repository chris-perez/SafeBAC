import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import org.junit.Assert;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;

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
  String TEST_AUTH_TOKEN = "";

  String CHANGE_NAME = "Keith Stone";
  String CHANGE_SEX = "male";
  int CHANGE_WEIGHT = 200;
  int CHANGE_AGE = 21;

  @Test
  public void testBAC() {
    running(fakeApplication(), new Runnable() {

      private void checkBAC(){
        Assert.assertEquals(9,User.calculateBAC(115,"id","female",2,3));
        Assert.assertEquals(9,User.calculateBAC(205,"id","male",4.68,4.25));
        Assert.assertEquals(10,User.calculateBAC(170,"id","male",4.2,4.5));
        Assert.assertEquals(20,User.calculateBAC(130,"id","female",4,2));
        Assert.assertEquals(19,User.calculateBAC(130,"id","female",4,3));
      }

      @Override
      public void run() {
        checkBAC();
      }
    });
  }

  @Test
  public void userTest() {
    running(fakeApplication(), new Runnable() {
      ObjectNode request;
      JsonNode content;
      Result result;

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

      private void createUser() {
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

      private void loginUser() {
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
        TEST_AUTH_TOKEN = content.get("authID").asText();
      }

      private void updateProfile() {
        // Login
        request = Json.newObject()
                .put("email", TEST_EMAIL)
                .put("name", CHANGE_NAME)
                .put("age", CHANGE_AGE)
                .put("sex", CHANGE_SEX)
                .put("weight", CHANGE_WEIGHT);
        result = callAction(controllers.routes.ref.Users.updateProfile(),
                fakeRequest().withHeader("X-Auth-Token", TEST_AUTH_TOKEN).withJsonBody(request));
        Logger.info("Login Result: " + contentAsString(result));
        assertThat(status(result)).isEqualTo(OK);
      }

      private void logoutUser() {
        // Logout
        result = callAction(controllers.routes.ref.Users.logoutUser(),
            fakeRequest().withHeader("X-Auth-Token", TEST_AUTH_TOKEN));
        assertThat(status(result)).isEqualTo(NO_CONTENT);
      }

      @Override
      public void run() {
        Ebean.beginTransaction();
        try {
          createUser();
          loginUser();
          updateProfile();
          logoutUser();
        } finally {
          Ebean.rollbackTransaction();
        }
      }
    });
  }
}
