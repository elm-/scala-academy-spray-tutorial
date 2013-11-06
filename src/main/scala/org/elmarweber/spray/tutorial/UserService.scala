package org.elmarweber.spray.tutorial

import spray.routing._
import spray.http._
import spray.httpx.SprayJsonSupport._
import scala.concurrent.ExecutionContext
import UserProtocol._

trait UserService extends HttpService with UserDao {
  protected[this] implicit def exCtx: ExecutionContext

  val userRoute = pathPrefix("user") {
    path("") {
      get {
        complete {
          findAll()
        }
      }
    } ~
    pathPrefix(Segment) { username =>
      get {
        complete {
          findOne(username)
        }
      } ~
      put {
        entity(as[User]) { user =>
          complete {
            update(user)
          }
        }
      } ~
      delete {
        complete {
          remove(username)
        }
      }
    }
  }
}
