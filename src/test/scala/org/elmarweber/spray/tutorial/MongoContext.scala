package org.elmarweber.spray.tutorial

import de.flapdoodle.embed.mongo.config._
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo._
import reactivemongo.api._
import org.specs2.mutable.BeforeAfter
import scala.util.Random
import scala.concurrent.ExecutionContext

trait MongoContext extends BeforeAfter {
  val mongoHost = "localhost"
  val mongoPort = 17000 + Random.nextInt(999)

  val driver = new MongoDriver

  private lazy val runtime = MongodStarter.getDefaultInstance
  private lazy val mongodExe = runtime.prepare(new MongodConfigBuilder()
      .version(Version.V2_4_5)
      .net(new Net(mongoHost, mongoPort, false))
      .build())
  private var mongod: MongodProcess = _

  var mongoConnection : MongoConnection = _

  def getDb(dbName: String = "test")(implicit exCtx: ExecutionContext) = mongoConnection(dbName)

  override def before() {
    mongod = mongodExe.start()
    mongoConnection = driver.connection(List(mongoHost + ":" + mongoPort))
  }

  override def after() {
    if (mongod != null) {
      mongoConnection.close()
      mongod.stop()
      mongodExe.stop()
    }
  }
}
