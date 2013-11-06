package org.elmarweber.spray.tutorial

import scala.concurrent._
import scala.concurrent.duration._

trait FutureTestSupport {
  import scala.language.implicitConversions

  protected[this] val duration = 15.seconds

  implicit def pimpFuture[T](future: Future[T]) = new PimpedFuture(future)

  class PimpedFuture[T](future: Future[T]) {
    def await = Await.result(future, duration)
  }
}
