import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        assertThat(content.has("age")).isTrue();
      }

      private void createUser() {
        // Create User
        request = Json.newObject()
            .put("email", TEST_EMAIL)
            .put("password", TEST_PASSWORD)
            .put("name", TEST_NAME)
            .put("age", TEST_AGE)
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

      private void userChangeInfo() {
        // Login
        request = Json.newObject()
                .put("email", TEST_EMAIL)
                .put("password", TEST_PASSWORD)
                .put("name", CHANGE_NAME)
                .put("age", CHANGE_AGE)
                .put("sex", CHANGE_SEX)
                .put("weight", CHANGE_WEIGHT);
        result = callAction(controllers.routes.ref.Users.changeInfoUser(),
                fakeRequest().withJsonBody(request));
        Logger.info("Login Result: " + contentAsString(result));
        assertThat(status(result)).isEqualTo(OK);
        content = Json.parse(contentAsString(result));
        validateUser(content);
        TEST_AUTH_TOKEN = content.get("authID").asText();
      }

      @Override
      public void run() {
        Ebean.beginTransaction();
        try {
          createUser();
          loginUser();
        } finally {
          Ebean.rollbackTransaction();
        }
      }
    });
  }
}
