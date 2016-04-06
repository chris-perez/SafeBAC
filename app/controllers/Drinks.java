package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Drink;
import org.testng.annotations.Test;
import play.mvc.Controller;
import play.mvc.Result;


/**
 * Created by Noah on 4/6/16.
 */
public class Drinks extends Controller {
    
    public static Result addDrink()
    {
        String name="Svedka";
        double abv=40.0;
        response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        JsonNode body = request().body().asJson();

        Drink d;
        Drink.DrinkBuilder drinkBuilder = new Drink.DrinkBuilder();

        drinkBuilder = drinkBuilder.setName(name)
                .setAbv(abv);

        d = drinkBuilder.setName(name).build();

        ObjectNode json = d.toJson();
        json.put("name", d.name);
        return ok(json);
    }
}
