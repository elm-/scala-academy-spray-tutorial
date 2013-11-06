package org.elmarweber.spray.tutorial

import spray.routing._
import spray.http._

trait UserService extends HttpService {
  val userRoute = pathPrefix("user") {
    path("") {
      get {
        complete {
          "OK"
        }
      }
    }
  }
}
