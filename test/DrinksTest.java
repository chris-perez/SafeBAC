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
 * Created by Noah on 4/6/16.
 */
public class DrinksTest {
    String TEST_NAME = "drink";
    double TEST_ABV = 1.0;

    @Test
    public void drinkTest() {
        running(fakeApplication(), new Runnable() {
            ObjectNode request;
            JsonNode content;
            Result result;

            private void validateDrink(JsonNode content) {
                assertThat(content.has("name")).isTrue();
                assertThat(content.has("abv")).isTrue();
            }

            private void createDrink() {
                // Create Drink
                request = Json.newObject()
                        .put("name", TEST_NAME)
                        .put("abv", TEST_ABV);

                result = callAction(controllers.routes.ref.Users.createUser(),
                        fakeRequest().withJsonBody(request));
                Logger.info("Create User Result: " + contentAsString(result));
                assertThat(status(result)).isEqualTo(OK);
                content = Json.parse(contentAsString(result));
                validateUser(content);
            }
            @Override
            public void run() {
                Ebean.beginTransaction();
                try {
                    createDrink();
                } finally {
                    Ebean.rollbackTransaction();
                }
            }
        });
    }
}
