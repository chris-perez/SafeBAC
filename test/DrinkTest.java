import models.Drink;
import models.User;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Created by Chris on 4/25/2016.
 */
public class DrinkTest {
  @Test
  public void findByName() {
    running(fakeApplication(), new Runnable() {
      @Override
      public void run() {
        Drink d = new Drink("name1", .5);
        Drink found = Drink.findByName("name1");
        assertThat(found).isNotNull();
      }
    });
  }
}
