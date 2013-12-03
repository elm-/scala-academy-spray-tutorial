package org.elmarweber.spray.tutorial

import akka.actor.{Props, ActorSystem}
import reactivemongo.api.MongoDriver

trait SprayTutorialWebApp {
  implicit val system = ActorSystem("spray-tutorial")
  implicit val executionContext = system.dispatcher

  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  val db = connection("spray-tutorial")

  val serviceActor = system.actorOf(Props(new ServiceActor(db)), "rest-service")

  system.registerOnTermination({
    connection.close()
  })
}
