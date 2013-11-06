package org.elmarweber.spray.tutorial

case class User(username: String, displayName: String, apiKey: String)

import spray.json._

object UserProtocol extends DefaultJsonProtocol {
  implicit val userFormat = jsonFormat3(User)
}
