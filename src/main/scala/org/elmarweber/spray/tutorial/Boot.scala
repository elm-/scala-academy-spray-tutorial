package org.elmarweber.spray.tutorial

import akka.actor._
import akka.io.IO
import spray.can._

import reactivemongo.api._

object Boot extends App {
  implicit val system = ActorSystem("on-spray-can")
  implicit val executionContext = system.dispatcher

  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  val db = connection("spray-tutorial")

  val service = system.actorOf(Props[ServiceActor], "rest-service")

  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)

  system.registerOnTermination({
    connection.close()
  })
}
