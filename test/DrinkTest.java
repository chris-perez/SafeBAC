import models.Drink;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
/**
 * Created by Noah on 4/13/16.
 */

public class DrinkTest {
    @Test
    public void drinkExists() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                boolean exists = Drink.drinkExists("name");
                assertThat(exists).isNotNull();
            }
        });
    }

    @Test
    public void fromName() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                Drink d = Drink.fromName("name");
                assertThat(d).isNull();
            }
        });
    }
}
