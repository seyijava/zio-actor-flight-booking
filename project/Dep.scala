import sbt._

object Dep {

  val LogbackVersion = "1.2.11"
  val LogbackEncoderVersion = "4.11"
  val grpcVersion = "1.50.1"
  val kafkaZioV = "2.1.3"
  val zioLoggingVersion = "2.0.0"
  val circeV = "0.13.0"
  val zioV = "2.0.10"
  val doobieV = ""
  lazy val tapirVersion = "1.2.9"

  val circe = Seq(
    "io.circe" %% "circe-core" % circeV,
    "io.circe" %% "circe-parser" % circeV,
    "io.circe" %% "circe-generic" % circeV
  )

  val logback = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.11"
  )

  val zioConfig =
    Seq(
      "dev.zio" %% "zio-json" % "0.3.0-RC10",
      "dev.zio" %% "zio-config" % "3.0.1",
      "dev.zio" %% "zio-config-typesafe" % "3.0.1",
      "dev.zio" %% "zio-config-magnolia" % "3.0.1",
      "dev.zio" %% "zio-logging" % zioLoggingVersion,
      "dev.zio" %% "zio-logging-slf4j" % zioLoggingVersion
    )

  val kafkaZIO = Seq("dev.zio" %% "zio-kafka" % kafkaZioV)

  val zioTest =
    Seq(
      "dev.zio" %% "zio" % "2.0.10",
      "dev.zio" %% "zio-test" % "2.0.10" % Test
    )

  val zioActor =
    Seq(
      "dev.zio" %% "zio-actors-persistence" % "0.1.0",
      "dev.zio" %% "zio-actors" % "0.1.0",
      "dev.zio" %% "zio-actors-persistence-jdbc" % "0.1.0",
      "dev.zio" %% "zio-actors-akka-interop" % "0.1.0"
    )

  val zioTapir = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-prometheus-metrics" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % tapirVersion
  )

  val logs = Seq(
    "ch.qos.logback" % "logback-core" % LogbackVersion,
    "ch.qos.logback" % "logback-classic" % LogbackVersion
  )
}
