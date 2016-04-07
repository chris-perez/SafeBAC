package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Drink;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;


/**
 * Created by Noah on 4/6/16.
 */
public class Drinks extends Controller {
    public static final ObjectNode NO_SESSION = Json.newObject().put("error", "There is no current session using that key");
    public static final ObjectNode INCORRECT_FIELDS = Json.newObject().put("error", "Incorrect fields.");
    
    public static Result addDrink()
    {
        response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        User u = Users.fromRequest();
        if (u == null) {
            return badRequest(NO_SESSION);
        }
        JsonNode body = request().body().asJson();
        Long drinkID = body.get("id").asLong();
        Double volume = body.get("volume").asDouble();
        Drink d = Drink.findByID(drinkID);

        if (d == null) {
            return badRequest(INCORRECT_FIELDS);
        }

        u.addDrink(d, volume);

        //TODO: return current BAC
        return ok(u.toJson());
    }
}