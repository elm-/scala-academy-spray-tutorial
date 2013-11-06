package org.elmarweber.spray.tutorial


import akka.actor.Actor
import reactivemongo.api._

class ServiceActor(override protected val db: DB) extends Actor with UserService {
  def actorRefFactory = context
  override protected val exCtx = context.dispatcher

  def receive = runRoute(userRoute)
}
