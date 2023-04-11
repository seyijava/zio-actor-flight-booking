package com.digitialPlus.flight.booking.infra

import zio.{ULayer, ZIO, ZLayer}
import zio.actors.ActorSystem

import java.io.File

object AppLayer {
  val actorSystem = ZIO.scoped(
    ActorSystem(
      "flightBookingSystem",
      Some(new File("./src/main/resources/application.conf"))
    )
  )

  val actorSystemLayer = ZLayer.scoped(actorSystem)
}
