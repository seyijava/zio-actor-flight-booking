package com.digitialPlus.flight.booking.command

import com.digitialPlus.flight.booking.algebra.{FlightId, FlightInfo, Passenger}

import java.util.UUID

object FlightCommands {

  sealed trait FlightCommand[+_]

  case class AddFlight(flightId: FlightId, flightInfo: FlightInfo)
      extends FlightCommand[Unit]

  case class AddPassenger(flightId: FlightId, passenger: Passenger)
      extends FlightCommand[Unit]

  case class SelectSeat(
      flightId: FlightId,
      passengerId: UUID,
      seatAssignment: String
  ) extends FlightCommand[Unit]

  case class RemovePassenger(flightId: FlightId, passenger: UUID)
      extends FlightCommand[Unit]

  case class CloseFlight(flightId: FlightId, isClose: Boolean)
      extends FlightCommand[Unit]

  case class GetFlightDetails() extends FlightCommand[FlightState]
}
