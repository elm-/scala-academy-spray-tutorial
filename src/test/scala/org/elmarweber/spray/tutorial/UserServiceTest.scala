package org.elmarweber.spray.tutorial


import spray.testkit._
import org.specs2.mutable._
import spray.http._
import spray.httpx.SprayJsonSupport._

import org.elmarweber.spray.tutorial.UserProtocol._

class UserServiceTest extends Specification with Specs2RouteTest {
  "User Service" should {
    "list users with no data must return empty array" in new DefaultContext {
      Get("/user/") ~> userRoute ~> check {
        responseAs[String] mustEqual "[]"
      }
    }

    "create user and retrieve user" in new DefaultContext {
      val testUser = User("foo", "Mr foo", "key")

      Post("/user/", testUser) ~> userRoute ~> check {
        status mustEqual StatusCodes.OK
      }

      Get("/user/foo/") ~> userRoute ~> check {
        responseAs[User] mustEqual testUser
      }
    }
  }

  trait DefaultContext extends MongoContext with UserService {
    override val actorRefFactory = system
    override val exCtx = system.dispatcher
    override lazy val db = getDb()
  }
}
