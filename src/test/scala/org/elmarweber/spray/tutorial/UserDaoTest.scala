package org.elmarweber.spray.tutorial

import org.specs2.mutable._
import reactivemongo.bson._

class UserDaoTest extends Specification with FutureTestSupport {
  private val NAME = "foouser"
  private val NAME_2 = "baruser"
  private val DISPLAY_NAME = "Mr foo"
  private val API_KEY = "3214-31u24n"
  private val USER = User(NAME, DISPLAY_NAME, API_KEY)
  private val USER_2 = USER.copy(username = NAME_2)

  import scala.concurrent.ExecutionContext.Implicits.global

  "User Dao" should {
    "insert and persist user" in new DefaultContext {
      insert(USER).await.ok mustEqual true

      val docOpt = db("User").find(BSONDocument("_id" -> NAME)).cursor.headOption.await

      docOpt must beSome
      docOpt.get.getAs[String]("_id").get mustEqual NAME
      docOpt.get.getAs[String]("displayName").get mustEqual DISPLAY_NAME
      docOpt.get.getAs[String]("apiKey").get mustEqual API_KEY
    }

    "find user" in new DefaultContext {
      insert(USER).await

      findOne(NAME).await mustEqual Some(USER)
    }

    "list users" in new DefaultContext {
      insert(USER).await
      insert(USER_2).await

      findAll().await must haveTheSameElementsAs(List(USER, USER_2))
    }

    "delete user" in new DefaultContext {
      insert(USER).await

      findOne(NAME).await must beSome

      remove(NAME).await.ok mustEqual true

      findOne(NAME).await must beNone
    }
  }

  trait DefaultContext extends MongoContext with UserDao {
    lazy val db = getDb()
  }
}
