package com.digitialPlus.flight.booking.service

import com.digitialPlus.flight.booking.actor.FlightEntity
import com.digitialPlus.flight.booking.algebra.{
  FlightId,
  FlightInfo,
  Passenger,
  PassengerId,
  Seat
}
import com.digitialPlus.flight.booking.api.{APIError, InternalServerError}
import com.digitialPlus.flight.booking.command.FlightCommands.{
  AddFlight,
  CloseFlight,
  GetFlightDetails,
  RemovePassenger,
  SelectSeat
}
import com.digitialPlus.flight.booking.command.FlightState
import zio.{IO, Scope, ZIO, ZLayer}
import zio.actors.{ActorSystem, Supervisor}

import java.io.File
import java.util.UUID

object FlightBookingService {

  val actorSystem: ZIO[Scope, Throwable, ActorSystem] = {
    ZIO.acquireRelease(
      ActorSystem(
        "flightBookingSystem",
        Some(new File("./src/main/resources/application.conf"))
      )
    )(_.shutdown.ignore)
  }

  val actorSystemLayer = ZLayer.scoped(actorSystem)

  val addFlight: FlightInfo => IO[APIError, FlightId] = (flightInfo) => {
    (for {
      flightId <- ZIO.attempt(UUID.fromString(flightInfo.flightId.id))
      actorSys <- ZIO.service[ActorSystem]
      actorRef <- actorSys.make(
        "FlightActor",
        Supervisor.none,
        FlightState(passengers = Set.empty),
        FlightEntity(flightId.toString)
      )
      _ <- actorRef ? AddFlight(
        FlightId(flightInfo.toString),
        flightInfo = flightInfo
      )
    } yield FlightId(flightId.toString))
      .provide(actorSystemLayer)
      .mapError(ex => InternalServerError(ex.getMessage))
  }

  val getFlightDetails: FlightId => IO[APIError, FlightState] = (flightId) => {
    (for {
      _ <- ZIO.attempt(UUID.fromString(flightId.id))
      actorSys <- ZIO.service[ActorSystem]
      actorRef <- actorSys.make(
        "FlightActor",
        Supervisor.none,
        FlightState(passengers = Set.empty),
        FlightEntity(flightId.id)
      )
      flightState <- actorRef ? GetFlightDetails()
    } yield flightState)
      .provide(actorSystemLayer)
      .mapError(ex => InternalServerError(ex.getMessage))
  }

  val removePassenger: (FlightId, PassengerId) => IO[APIError, Unit] = {
    (flightId, passengerId) =>
      (for {
        _ <- ZIO.attempt(UUID.fromString(flightId.id))
        passId <- ZIO.attempt(UUID.fromString(passengerId.id))
        actorSys <- ZIO.service[ActorSystem]
        actorRef <- actorSys.make(
          "FlightActor",
          Supervisor.none,
          FlightState(passengers = Set.empty[Passenger]),
          FlightEntity(flightId.id)
        )
        _ <- actorRef ? RemovePassenger(flightId, passId)
      } yield ())
        .provide(actorSystemLayer)
        .mapError(ex => InternalServerError(ex.getMessage))
  }

  val closeFlight: (FlightId) => IO[APIError, Unit] = { (flightId) =>
    (for {
      _ <- ZIO.attempt(UUID.fromString(flightId.id))
      actorSys <- ZIO.service[ActorSystem]
      actorRef <- actorSys.make(
        "FlightActor",
        Supervisor.none,
        FlightState(passengers = Set.empty[Passenger]),
        FlightEntity(flightId.id)
      )
      _ <- actorRef ? CloseFlight(flightId, true)
    } yield ())
      .provide(actorSystemLayer)
      .mapError(ex => InternalServerError(ex.getMessage))
  }

  val addSelectedSeat: (FlightId, PassengerId, Seat) => IO[APIError, Unit] = {
    (flightId, passengerId, seat) =>
      (for {
        _ <- ZIO.attempt(UUID.fromString(flightId.id))
        passId <- ZIO.attempt(UUID.fromString(passengerId.id))

        actorSys <- ZIO.service[ActorSystem]
        actorRef <- actorSys.make(
          "FlightActor",
          Supervisor.none,
          FlightState(passengers = Set.empty),
          FlightEntity(flightId.id)
        )
        _ <- actorRef ? SelectSeat(
          flightId,
          passId,
          seat.seatAssignment
        )
      } yield ())
        .provide(actorSystemLayer)
        .mapError(ex => InternalServerError(ex.getMessage))
  }
}
