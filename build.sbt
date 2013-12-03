name := "scala-academy-spray-tutorial"

organization := "org.elmarweber"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.2"


resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
)


libraryDependencies ++= {
  val akkaVersion = "2.1.4"
  val sprayVersion = "1.1-RC2"
  val specsVersion = "2.2.3"
  val reactiveMongoVersion = "0.9"
  Seq(
    "io.spray" % "spray-can" % sprayVersion,
    "io.spray" % "spray-routing" % sprayVersion,
    "io.spray" % "spray-servlet" % sprayVersion,
    "io.spray" %% "spray-json" % "1.2.5",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "org.reactivemongo" %% "reactivemongo" % reactiveMongoVersion,
    "io.spray" % "spray-testkit" % sprayVersion % "test",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "org.specs2" %% "specs2" % specsVersion % "test",
    "de.flapdoodle.embed" % "de.flapdoodle.embed.mongo" % "1.40" % "test",
    "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
    "org.eclipse.jetty" % "jetty-webapp" % "8.1.10.v20130312" % "container"
  )
}


seq(Revolver.settings: _*)

seq(webSettings :_*)
