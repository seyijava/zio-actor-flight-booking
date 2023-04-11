package com.digitialPlus.flight.booking.event

import com.digitialPlus.flight.booking.algebra.{FlightId, FlightInfo, Passenger}

import java.util.UUID

object FlightEvents {
  sealed trait FlightEvent
  case class FlightClosedEvent(flightId: FlightId, isClose: Boolean)
      extends FlightEvent
  case class FlightAddedEvent(
      flightId: FlightId,
      flightInfo: FlightInfo
  ) extends FlightEvent
  case class PassengerAddedEvent(
      flightId: FlightId,
      passenger: Passenger
  ) extends FlightEvent
  case class SeatAddedEvent(
      flightId: FlightId,
      passengerId: UUID,
      seatAssignment: String
  ) extends FlightEvent
  case class PassengerRemovedEvent(flightId: FlightId, passenger: UUID)
      extends FlightEvent
}
