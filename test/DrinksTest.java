import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.io.Files;
import controllers.Drinks;
import controllers.routes;
import models.Drink;
import models.User;
import org.joda.time.DateTime;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.test.Helpers.contentAsString;

/**
 * Created by Noah on 4/6/16.
 */
public class DrinksTest {
  ObjectNode request;
  JsonNode content;
  Result result;

  @Test
  public void importDrinks() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        for (Drink d : Drink.find.all()) {
          d.delete();
        }

        try {
          Drinks.importDrinks();

          File file = new File("./app/assets/alcoholCatalog.json");
          String fileAsString = Files.toString(file, Charset.defaultCharset());
          JsonNode drinksNode = Json.parse(fileAsString);

          Iterator<JsonNode> drinksIterator = drinksNode.get("drinkTypes").elements();
          while (drinksIterator.hasNext()) {
            JsonNode d = drinksIterator.next();
            Drink drink = Drink.find.where().eq("name", d.get("name").asText())
                .eq("abv", d.get("abv").asDouble())
                .eq("type", d.get("type").asText())
                .findUnique();
            assertThat(drink).isNotNull();
          }

        }catch (IOException e) {
          e.printStackTrace();
          assertThat(false).isTrue();
        }
      }
    });
  }

  @Test
  public void getCatalog() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        result = callAction(routes.ref.Drinks.getCatalog(),
            fakeRequest());
        Logger.info("Get Catalog Result: " + contentAsString(result));
        assertThat(status(result)).isEqualTo(OK);
        content = Json.parse(contentAsString(result));
        assertThat(content.isArray()).isTrue();

        JsonNode drink = content.get(0);
        assertThat(drink).isNotNull();
        assertThat(drink.has("id")).isTrue();
        assertThat(drink.has("name")).isTrue();
        assertThat(drink.has("abv")).isTrue();
        assertThat(drink.has("type")).isTrue();
      }
    });
  }

  @Test
  public void addDrink() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user = new User("name", "email", "password", "male", DateTime.now(), 160, "authID");
        request = Json.newObject();
        request.put("id", 1);
        request.put("volume", 3);
        result = callAction(routes.ref.Drinks.addDrink(),
            fakeRequest().withHeader("X-Auth-Token", "authID").withJsonBody(request));
        Logger.info("Add Drink Result: " + contentAsString(result));
        assertThat(status(result)).isEqualTo(OK);
        content = Json.parse(contentAsString(result));



      }
    });
  }
}
