package org.elmarweber.spray.tutorial

import reactivemongo.api._
import reactivemongo.bson._
import reactivemongo.core.commands.LastError

import scala.concurrent.{ExecutionContext, Future}

trait UserDao {
  protected[this] val db: DB

  private def users = db("User")

  private def identity(username: String) = BSONDocument("_id" -> username)

  private def toUser(bson: BSONDocument) = User(
    username = bson.getAs[String]("_id").get,
    displayName = bson.getAs[String]("displayName").get,
    apiKey = bson.getAs[String]("apiKey").get)


  def insert(user: User)(implicit ctx: ExecutionContext): Future[LastError] = users
    .insert(BSONDocument(
      "_id" -> user.username,
      "displayName" -> user.displayName,
      "apiKey" -> user.apiKey))

  def update(user: User)(implicit ctx: ExecutionContext): Future[LastError] = users
    .update(identity(user.username),
      BSONDocument(
        "displayName" -> user.displayName,
        "apiKey" -> user.apiKey))

  def findOne(username: String)(implicit ctx: ExecutionContext): Future[Option[User]] = users
    .find(identity(username))
    .cursor[BSONDocument].headOption
    .map(_.map(toUser))

  def findAll()(implicit ctx: ExecutionContext): Future[List[User]] = users
    .find(BSONDocument())
    .cursor[BSONDocument].toList
    .map(_.map(toUser))

  def remove(username: String)(implicit ctx: ExecutionContext): Future[LastError] = users
    .remove(identity(username))
}
