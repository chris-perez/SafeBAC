import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Files;
import controllers.Drinks;
import models.Drink;
import models.User;
import org.junit.Test;
import play.libs.Json;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

/**
 * Created by Noah on 4/6/16.
 */
public class DrinksTest {
  @Test
  public void importDrinks() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
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
}
