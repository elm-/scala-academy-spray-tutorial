package org.elmarweber.spray.tutorial

import akka.io.IO
import spray.can._

object Boot extends App with SprayTutorialWebApp {
  IO(Http)(system) ! Http.Bind(serviceActor, interface = "localhost", port = 8080)
}
