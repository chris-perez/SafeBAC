import controllers.Drinks;
import play.Application;
import play.GlobalSettings;

/**
 * Created by Chris on 4/25/2016.
 */
public class Global extends GlobalSettings{
  @Override
  public void onStart(Application application) {
    Drinks.importDrinks();
    super.onStart(application);
  }
}
