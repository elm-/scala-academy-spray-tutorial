package org.elmarweber.spray.tutorial

import akka.actor._
import akka.io._
import spray.can._

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._

class ServiceActor extends Actor with UserService {
  def actorRefFactory = context

  def receive = runRoute(userRoute)
}
