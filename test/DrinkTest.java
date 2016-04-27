import com.fasterxml.jackson.databind.JsonNode;
import models.Drink;
import models.User;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

/**
 * Created by Chris on 4/25/2016.
 */
public class DrinkTest {
  @Test
  public void findByName() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        Drink d = new Drink("name1", .5, "beer");
        Drink found = Drink.findByName("name1");
        assertThat(found).isNotNull();
      }
    });
  }

  @Test
  public void toJson() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        Drink d = new Drink("name1", .5, "beer");
        JsonNode node = d.toJson();
        assertThat(node).isNotNull();
        assertThat(node.has("name")).isTrue();
        assertThat(node.get("name").asText()).isEqualTo("name1");
        assertThat(node.has("abv")).isTrue();
        assertThat(node.get("abv").asDouble()).isEqualTo(.5);
        assertThat(node.has("type")).isTrue();
        assertThat(node.get("type").asText()).isEqualTo("beer");
      }
    });
  }

}
