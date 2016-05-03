name := "safebac"

version := "1.0"

lazy val `safebac` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.18"
)

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )