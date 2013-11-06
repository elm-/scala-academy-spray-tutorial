package org.elmarweber.spray.tutorial


import spray.testkit._
import org.specs2.mutable._
import org.specs2.specification.Scope

class UserServiceTest extends Specification with Specs2RouteTest {
  "User Service" should {
    "list users" in new DefaultContext {
      Get("/user/") ~> userRoute ~> check {
        responseAs[String] mustEqual "[]"
      }
    }
  }

  trait DefaultContext extends MongoContext with UserService {
    override val actorRefFactory = system
    override val exCtx = system.dispatcher
    override lazy val db = getDb()
  }
}
