package org.elmarweber.spray.tutorial

import spray.routing._
import spray.http._
import spray.httpx.SprayJsonSupport._
import scala.concurrent.ExecutionContext
import UserProtocol._
import reactivemongo.core.commands.LastError

trait UserService extends HttpService with UserDao {
  protected[this] implicit def exCtx: ExecutionContext

  // TODO: use JSON format that includes text error message, etc.
  private val lastErrorMapper : LastError => StatusCode = { _.ok match {
    case true => StatusCodes.OK
    case false => StatusCodes.InternalServerError
  }}

  val userRoute = pathPrefix("user") {
    path("") {
      get {
        complete {
          findAll()
        }
      } ~
      post {
        entity(as[User]) { user =>
          complete {
            insert(user) map lastErrorMapper
          }
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
            update(user) map lastErrorMapper
          }
        }
      } ~
      delete {
        complete {
          remove(username) map lastErrorMapper
        }
      }
    }
  }
}
