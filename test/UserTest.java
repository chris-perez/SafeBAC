import models.User;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Created by chris_000 on 3/25/2016.
 */
public class UserTest {

  @Test
  public void userExists() {
    running(fakeApplication(), new Runnable() {
      @Override
      public void run() {
        boolean exists = User.userExists("email");
        assertThat(exists).isNotNull();
      }
    });
  }

  @Test
  public void fromEmail() {
    running(fakeApplication(), new Runnable() {
      @Override
      public void run() {
        User u = User.fromEmail("email");
        assertThat(u).isNull();
      }
    });
  }

  @Test
  public void idExists() {
    running(fakeApplication(), new Runnable() {
      @Override
      public void run() {
        boolean exists = User.idExists("userID");
        assertThat(exists).isNotNull();
      }
    });
  }

  @Test
  public void fromAuthID() {
    running(fakeApplication(), new Runnable() {
      @Override
      public void run() {
        User u = User.fromAuthID("email");
        assertThat(u).isNull();
      }
    });
  }
}
