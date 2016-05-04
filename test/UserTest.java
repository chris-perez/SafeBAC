import com.fasterxml.jackson.databind.JsonNode;
import models.Drink;
import models.User;
import models.UserToDrink;
import models.UserToUser;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

/**
 * Created by chris_000 on 3/25/2016.
 */
public class UserTest {

  @Test
  public void toJson() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, "authID1");
        JsonNode json = user1.toJson();
        assertThat(json.get("id").asLong()).isEqualTo(user1.getID());
        assertThat(json.get("name").asText()).isEqualTo(user1.getName());
        assertThat(json.get("email").asText()).isEqualTo(user1.getEmail());
        assertThat(json.get("sex").asText()).isEqualTo(user1.getSex());
        assertThat(json.get("birthDate").asLong()).isEqualTo(user1.getBirthDate().getMillis());
        assertThat(json.get("weight").asInt()).isEqualTo(user1.getWeight());
        assertThat(json.get("bac").asDouble()).isEqualTo(user1.getBAC());
      }
    });
  }

  @Test
  public void friendToJson() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, "authID1");
        JsonNode json = user1.toFriendJson();
        assertThat(json.get("id").asLong()).isEqualTo(user1.getID());
        assertThat(json.get("name").asText()).isEqualTo(user1.getName());
      }
    });
  }

  @Test
  public void addFriend() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, "authID1");
        User user2 = new User("name2", "email2", "password2", "female", DateTime.now(), 120, "authID2");
        User user3 = new User("name3", "email3", "password3", "female", DateTime.now(), 140, "authID3");
        user3.addFriend(user1);
        user1.addFriend(user2);
        assertThat(user1.getFriends().contains(user2)).isTrue();
        assertThat(user1.getFriends().contains(user3)).isTrue();
      }
    });
  }

  @Test
  public void setBACVisibleToFriend() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User user1 = new User("name", "email", "password", "male", DateTime.now(), 160, "authID1");
        User user2 = new User("name2", "email2", "password2", "female", DateTime.now(), 120, "authID2");
        User user3 = new User("name3", "email3", "password3", "female", DateTime.now(), 140, "authID3");
        user3.addFriend(user1);
        user1.addFriend(user2);
        user1.setBACVisibleToFriend(user2, true);
        assertThat(UserToUser.findByUsers(user1, user2).getUser1IsVisible()).isTrue();
        assertThat(UserToUser.findByUsers(user1, user3).getUser1IsVisible()).isFalse();
      }
    });
  }


  @Test
  public void userExists() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        boolean exists = User.userExists("email");
        assertThat(exists).isNotNull();
      }
    });
  }

  @Test
  public void fromEmail() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User u = User.fromEmail("email");
        assertThat(u).isNull();
      }
    });
  }

  @Test
  public void idExists() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        boolean exists = User.idExists("userID");
        assertThat(exists).isNotNull();
      }
    });
  }

  @Test
  public void fromAuthID() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User u = User.fromAuthID("userID");
        assertThat(u).isNull();
      }
    });
  }

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
        u2d.delete();
        u.delete();
        d.delete();
      }
    });
  }

  @Test
  public void userToUserConstructor() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      @Override
      public void run() {
        User u = new User("name", "email", "password", "male", new DateTime(), 160, "authID");
        User u2 = new User("name2", "email2", "password2", "male", new DateTime(), 180, "authID2");
        UserToUser u2u = new UserToUser(u, u2);
        UserToUser u2u2 = UserToUser.find.byId(u2u.getId());
        assertThat(u2u2).isNotNull();
        u2u.delete();
        u.delete();
        u2.delete();
      }
    });
  }
}
