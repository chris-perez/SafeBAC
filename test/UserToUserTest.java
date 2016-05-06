import models.User;
import models.UserToUser;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

/**
 * Created by Chris on 5/3/2016.
 */
public class UserToUserTest {

  @Test
  public void exists() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, "authID1");
        User user2 = new User("name2", "email2", "password2", "female", DateTime.now(), 120, "authID2");
        new UserToUser(user1, user2);
        assertThat(UserToUser.exists(user1, user2)).isTrue();
      }
    });
  }

  @Test
  public void findByUsers() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, "authID1");
        User user2 = new User("name2", "email2", "password2", "female", DateTime.now(), 120, "authID2");
        new UserToUser(user1, user2);
        assertThat(UserToUser.findByUsers(user1, user2)).isNotNull();
      }
    });
  }

  @Test
  public void findByUser() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, "authID1");
        User user2 = new User("name2", "email2", "password2", "female", DateTime.now(), 120, "authID2");
        User user3 = new User("name3", "email3", "password3", "female", DateTime.now(), 140, "authID3");
        UserToUser u2u1 = new UserToUser(user1, user2);
        UserToUser u2u2 = new UserToUser(user3, user1);
        assertThat(UserToUser.findByUser(user1).contains(u2u2)).isTrue();
        assertThat(UserToUser.findByUser(user1).contains(u2u1)).isTrue();
      }
    });
  }
}
