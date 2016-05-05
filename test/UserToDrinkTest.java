import models.Drink;
import models.User;
import models.UserToDrink;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

/**
 * Created by chris_000 on 5/4/2016.
 */
public class UserToDrinkTest {
  @Test
  public void userToDrinkConstructor() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User u = new User("name", "email", "password", "male", new DateTime(), 160, "authID");
        Drink d = new Drink("name", .07, "beer");
        UserToDrink u2d = new UserToDrink(u, d, .5, new DateTime());
        UserToDrink u2d2 = UserToDrink.find.byId(u2d.getId());
        assertThat(u2d2).isNotNull();
      }
    });
  }
}
