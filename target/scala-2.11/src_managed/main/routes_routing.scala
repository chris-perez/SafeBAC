// @SOURCE:D:/Documents/GitHub/SafeBAC/conf/routes
// @HASH:7200edc95b519d999ae3d4b908acd07f443d7dcf
// @DATE:Thu May 05 15:17:45 EDT 2016


import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset
import _root_.play.libs.F

import Router.queryString

object Routes extends Router.Routes {

import ReverseRouteContext.empty

private var _prefix = "/"

def setPrefix(prefix: String): Unit = {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
private[this] lazy val controllers_Application_index0_invoker = createInvoker(
controllers.Application.index(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
        

// @LINE:9
private[this] lazy val controllers_Assets_at1_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_at1_invoker = createInvoker(
controllers.Assets.at(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
        

// @LINE:11
private[this] lazy val controllers_Users_createUser2_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("user/auth"))))
private[this] lazy val controllers_Users_createUser2_invoker = createInvoker(
controllers.Users.createUser(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "createUser", Nil,"POST", """""", Routes.prefix + """user/auth"""))
        

// @LINE:12
private[this] lazy val controllers_Users_loginUser3_route = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("user/auth"))))
private[this] lazy val controllers_Users_loginUser3_invoker = createInvoker(
controllers.Users.loginUser(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "loginUser", Nil,"PUT", """""", Routes.prefix + """user/auth"""))
        

// @LINE:13
private[this] lazy val controllers_Users_logoutUser4_route = Route("DELETE", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("user/auth"))))
private[this] lazy val controllers_Users_logoutUser4_invoker = createInvoker(
controllers.Users.logoutUser(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "logoutUser", Nil,"DELETE", """""", Routes.prefix + """user/auth"""))
        

// @LINE:15
private[this] lazy val controllers_Users_getProfile5_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("user/profile"))))
private[this] lazy val controllers_Users_getProfile5_invoker = createInvoker(
controllers.Users.getProfile(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "getProfile", Nil,"GET", """""", Routes.prefix + """user/profile"""))
        

// @LINE:16
private[this] lazy val controllers_Users_updateProfile6_route = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("user/profile"))))
private[this] lazy val controllers_Users_updateProfile6_invoker = createInvoker(
controllers.Users.updateProfile(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "updateProfile", Nil,"PUT", """""", Routes.prefix + """user/profile"""))
        

// @LINE:18
private[this] lazy val controllers_Users_getFriends7_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("user/friends"))))
private[this] lazy val controllers_Users_getFriends7_invoker = createInvoker(
controllers.Users.getFriends(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "getFriends", Nil,"GET", """""", Routes.prefix + """user/friends"""))
        

// @LINE:19
private[this] lazy val controllers_Users_addFriend8_route = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("user/friends"))))
private[this] lazy val controllers_Users_addFriend8_invoker = createInvoker(
controllers.Users.addFriend(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "addFriend", Seq(classOf[String]),"PUT", """""", Routes.prefix + """user/friends"""))
        

// @LINE:20
private[this] lazy val controllers_Users_setBACVisibleToFriend9_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("user/friends"))))
private[this] lazy val controllers_Users_setBACVisibleToFriend9_invoker = createInvoker(
controllers.Users.setBACVisibleToFriend(fakeValue[Long], fakeValue[Boolean]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "setBACVisibleToFriend", Seq(classOf[Long], classOf[Boolean]),"POST", """""", Routes.prefix + """user/friends"""))
        

// @LINE:22
private[this] lazy val controllers_Drinks_getCatalog10_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("drinks"))))
private[this] lazy val controllers_Drinks_getCatalog10_invoker = createInvoker(
controllers.Drinks.getCatalog(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Drinks", "getCatalog", Nil,"GET", """""", Routes.prefix + """drinks"""))
        

// @LINE:23
private[this] lazy val controllers_Drinks_addDrink11_route = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("drinks"))))
private[this] lazy val controllers_Drinks_addDrink11_invoker = createInvoker(
controllers.Drinks.addDrink(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Drinks", "addDrink", Nil,"PUT", """""", Routes.prefix + """drinks"""))
        

// @LINE:25
private[this] lazy val controllers_Drinks_getDrinkHistory12_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("drinks/history"))))
private[this] lazy val controllers_Drinks_getDrinkHistory12_invoker = createInvoker(
controllers.Drinks.getDrinkHistory(),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Drinks", "getDrinkHistory", Nil,"GET", """""", Routes.prefix + """drinks/history"""))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """user/auth""","""controllers.Users.createUser()"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """user/auth""","""controllers.Users.loginUser()"""),("""DELETE""", prefix + (if(prefix.endsWith("/")) "" else "/") + """user/auth""","""controllers.Users.logoutUser()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """user/profile""","""controllers.Users.getProfile()"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """user/profile""","""controllers.Users.updateProfile()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """user/friends""","""controllers.Users.getFriends()"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """user/friends""","""controllers.Users.addFriend(email:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """user/friends""","""controllers.Users.setBACVisibleToFriend(id:Long, visible:Boolean)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """drinks""","""controllers.Drinks.getCatalog()"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """drinks""","""controllers.Drinks.addDrink()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """drinks/history""","""controllers.Drinks.getDrinkHistory()""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]]
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0_route(params) => {
   call { 
        controllers_Application_index0_invoker.call(controllers.Application.index())
   }
}
        

// @LINE:9
case controllers_Assets_at1_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at1_invoker.call(controllers.Assets.at(path, file))
   }
}
        

// @LINE:11
case controllers_Users_createUser2_route(params) => {
   call { 
        controllers_Users_createUser2_invoker.call(controllers.Users.createUser())
   }
}
        

// @LINE:12
case controllers_Users_loginUser3_route(params) => {
   call { 
        controllers_Users_loginUser3_invoker.call(controllers.Users.loginUser())
   }
}
        

// @LINE:13
case controllers_Users_logoutUser4_route(params) => {
   call { 
        controllers_Users_logoutUser4_invoker.call(controllers.Users.logoutUser())
   }
}
        

// @LINE:15
case controllers_Users_getProfile5_route(params) => {
   call { 
        controllers_Users_getProfile5_invoker.call(controllers.Users.getProfile())
   }
}
        

// @LINE:16
case controllers_Users_updateProfile6_route(params) => {
   call { 
        controllers_Users_updateProfile6_invoker.call(controllers.Users.updateProfile())
   }
}
        

// @LINE:18
case controllers_Users_getFriends7_route(params) => {
   call { 
        controllers_Users_getFriends7_invoker.call(controllers.Users.getFriends())
   }
}
        

// @LINE:19
case controllers_Users_addFriend8_route(params) => {
   call(params.fromQuery[String]("email", None)) { (email) =>
        controllers_Users_addFriend8_invoker.call(controllers.Users.addFriend(email))
   }
}
        

// @LINE:20
case controllers_Users_setBACVisibleToFriend9_route(params) => {
   call(params.fromQuery[Long]("id", None), params.fromQuery[Boolean]("visible", None)) { (id, visible) =>
        controllers_Users_setBACVisibleToFriend9_invoker.call(controllers.Users.setBACVisibleToFriend(id, visible))
   }
}
        

// @LINE:22
case controllers_Drinks_getCatalog10_route(params) => {
   call { 
        controllers_Drinks_getCatalog10_invoker.call(controllers.Drinks.getCatalog())
   }
}
        

// @LINE:23
case controllers_Drinks_addDrink11_route(params) => {
   call { 
        controllers_Drinks_addDrink11_invoker.call(controllers.Drinks.addDrink())
   }
}
        

// @LINE:25
case controllers_Drinks_getDrinkHistory12_route(params) => {
   call { 
        controllers_Drinks_getDrinkHistory12_invoker.call(controllers.Drinks.getDrinkHistory())
   }
}
        
}

}
     