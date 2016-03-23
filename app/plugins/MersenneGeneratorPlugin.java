package plugins;

import play.Application;
import play.Plugin;

import java.util.Random;

public class MersenneGeneratorPlugin extends Plugin {

  public static MersenneTwister generator = new MersenneTwister(new Random().nextInt() + 10);
  private Application application;

  public MersenneGeneratorPlugin(Application application) {
    this.application = application;
  }

  @Override
  public void onStart() {
    generator = new MersenneTwister(new Random().nextInt() + 10);
  }
}
