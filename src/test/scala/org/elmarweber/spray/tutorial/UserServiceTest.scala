package org.elmarweber.spray.tutorial


import spray.testkit._
import org.specs2.mutable._
import org.specs2.specification.Scope

class UserServiceTest extends Specification with Specs2RouteTest {
  "User Service" should {
    "list users" in new DefaultContext {
      Get("/user/") ~> userRoute ~> check {
        responseAs[String] mustEqual "OK"
      }
    }
  }

  trait DefaultContext extends Scope with UserService {
    override val actorRefFactory = system
  }
}
