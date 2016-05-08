// @SOURCE:D:/Documents/GitHub/SafeBAC/conf/routes
// @HASH:7200edc95b519d999ae3d4b908acd07f443d7dcf
// @DATE:Thu May 05 15:17:45 EDT 2016

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset
import _root_.play.libs.F

import Router.queryString


// @LINE:25
// @LINE:23
// @LINE:22
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:6
package controllers {

// @LINE:25
// @LINE:23
// @LINE:22
class ReverseDrinks {


// @LINE:25
def getDrinkHistory(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "drinks/history")
}
                        

// @LINE:22
def getCatalog(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "drinks")
}
                        

// @LINE:23
def addDrink(): Call = {
   import ReverseRouteContext.empty
   Call("PUT", _prefix + { _defaultPrefix } + "drinks")
}
                        

}
                          

// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:11
class ReverseUsers {


// @LINE:12
def loginUser(): Call = {
   import ReverseRouteContext.empty
   Call("PUT", _prefix + { _defaultPrefix } + "user/auth")
}
                        

// @LINE:19
def addFriend(email:String): Call = {
   import ReverseRouteContext.empty
   Call("PUT", _prefix + { _defaultPrefix } + "user/friends" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("email", email)))))
}
                        

// @LINE:11
def createUser(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "user/auth")
}
                        

// @LINE:13
def logoutUser(): Call = {
   import ReverseRouteContext.empty
   Call("DELETE", _prefix + { _defaultPrefix } + "user/auth")
}
                        

// @LINE:18
def getFriends(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "user/friends")
}
                        

// @LINE:15
def getProfile(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "user/profile")
}
                        

// @LINE:20
def setBACVisibleToFriend(id:Long, visible:Boolean): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "user/friends" + queryString(List(Some(implicitly[QueryStringBindable[Long]].unbind("id", id)), Some(implicitly[QueryStringBindable[Boolean]].unbind("visible", visible)))))
}
                        

// @LINE:16
def updateProfile(): Call = {
   import ReverseRouteContext.empty
   Call("PUT", _prefix + { _defaultPrefix } + "user/profile")
}
                        

}
                          

// @LINE:9
class ReverseAssets {


// @LINE:9
def at(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

}
                          

// @LINE:6
class ReverseApplication {


// @LINE:6
def index(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix)
}
                        

}
                          
}
                  


// @LINE:25
// @LINE:23
// @LINE:22
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:6
package controllers.javascript {
import ReverseRouteContext.empty

// @LINE:25
// @LINE:23
// @LINE:22
class ReverseDrinks {


// @LINE:25
def getDrinkHistory : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Drinks.getDrinkHistory",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "drinks/history"})
      }
   """
)
                        

// @LINE:22
def getCatalog : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Drinks.getCatalog",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "drinks"})
      }
   """
)
                        

// @LINE:23
def addDrink : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Drinks.addDrink",
   """
      function() {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "drinks"})
      }
   """
)
                        

}
              

// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:11
class ReverseUsers {


// @LINE:12
def loginUser : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Users.loginUser",
   """
      function() {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "user/auth"})
      }
   """
)
                        

// @LINE:19
def addFriend : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Users.addFriend",
   """
      function(email) {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "user/friends" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("email", email)])})
      }
   """
)
                        

// @LINE:11
def createUser : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Users.createUser",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "user/auth"})
      }
   """
)
                        

// @LINE:13
def logoutUser : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Users.logoutUser",
   """
      function() {
      return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "user/auth"})
      }
   """
)
                        

// @LINE:18
def getFriends : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Users.getFriends",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "user/friends"})
      }
   """
)
                        

// @LINE:15
def getProfile : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Users.getProfile",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "user/profile"})
      }
   """
)
                        

// @LINE:20
def setBACVisibleToFriend : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Users.setBACVisibleToFriend",
   """
      function(id,visible) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "user/friends" + _qS([(""" + implicitly[QueryStringBindable[Long]].javascriptUnbind + """)("id", id), (""" + implicitly[QueryStringBindable[Boolean]].javascriptUnbind + """)("visible", visible)])})
      }
   """
)
                        

// @LINE:16
def updateProfile : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Users.updateProfile",
   """
      function() {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "user/profile"})
      }
   """
)
                        

}
              

// @LINE:9
class ReverseAssets {


// @LINE:9
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

}
              

// @LINE:6
class ReverseApplication {


// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

}
              
}
        


// @LINE:25
// @LINE:23
// @LINE:22
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:6
package controllers.ref {


// @LINE:25
// @LINE:23
// @LINE:22
class ReverseDrinks {


// @LINE:25
def getDrinkHistory(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Drinks.getDrinkHistory(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Drinks", "getDrinkHistory", Seq(), "GET", """""", _prefix + """drinks/history""")
)
                      

// @LINE:22
def getCatalog(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Drinks.getCatalog(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Drinks", "getCatalog", Seq(), "GET", """""", _prefix + """drinks""")
)
                      

// @LINE:23
def addDrink(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Drinks.addDrink(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Drinks", "addDrink", Seq(), "PUT", """""", _prefix + """drinks""")
)
                      

}
                          

// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:16
// @LINE:15
// @LINE:13
// @LINE:12
// @LINE:11
class ReverseUsers {


// @LINE:12
def loginUser(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Users.loginUser(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "loginUser", Seq(), "PUT", """""", _prefix + """user/auth""")
)
                      

// @LINE:19
def addFriend(email:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Users.addFriend(email), HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "addFriend", Seq(classOf[String]), "PUT", """""", _prefix + """user/friends""")
)
                      

// @LINE:11
def createUser(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Users.createUser(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "createUser", Seq(), "POST", """""", _prefix + """user/auth""")
)
                      

// @LINE:13
def logoutUser(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Users.logoutUser(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "logoutUser", Seq(), "DELETE", """""", _prefix + """user/auth""")
)
                      

// @LINE:18
def getFriends(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Users.getFriends(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "getFriends", Seq(), "GET", """""", _prefix + """user/friends""")
)
                      

// @LINE:15
def getProfile(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Users.getProfile(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "getProfile", Seq(), "GET", """""", _prefix + """user/profile""")
)
                      

// @LINE:20
def setBACVisibleToFriend(id:Long, visible:Boolean): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Users.setBACVisibleToFriend(id, visible), HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "setBACVisibleToFriend", Seq(classOf[Long], classOf[Boolean]), "POST", """""", _prefix + """user/friends""")
)
                      

// @LINE:16
def updateProfile(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Users.updateProfile(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Users", "updateProfile", Seq(), "PUT", """""", _prefix + """user/profile""")
)
                      

}
                          

// @LINE:9
class ReverseAssets {


// @LINE:9
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      

}
                          

// @LINE:6
class ReverseApplication {


// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      

}
                          
}
        
    