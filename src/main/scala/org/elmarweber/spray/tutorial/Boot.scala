package org.elmarweber.spray.tutorial

import akka.actor._
import akka.io.IO
import spray.can._

object Boot extends App {
  implicit val system = ActorSystem("on-spray-can")

  val service = system.actorOf(Props[ServiceActor], "rest-service")

  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}
