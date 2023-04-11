package com.digitialPlus.flight.booking

import zio.http.{Server, ServerConfig}
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}
import com.digitialPlus.flight.booking.api.FlightsApiLive

object FlightBookingMain extends ZIOAppDefault {

  val server = ZIO.scoped {
    for {
      httpApp <- FlightsApiLive.routes
      _ <- Server
        .serve(httpApp.withDefaultErrorResponse)
        .provide(
          ServerConfig.live(ServerConfig.default.port(9090)),
          Server.live
        )
    } yield ()
  }
  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] = {

    val program = (for {
      _ <- server
    } yield ())
    program.provide(
      FlightsApiLive.live
    )
  }
}
