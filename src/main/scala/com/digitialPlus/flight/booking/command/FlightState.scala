package com.digitialPlus.flight.booking.command

import com.digitialPlus.flight.booking.algebra.{FlightId, FlightInfo, Passenger}

import java.util.UUID

case class FlightState(
    flightInfo: Option[FlightInfo] = None,
    passengers: Set[Passenger]
) {

  def withDoorsClosed(flightId: FlightId, isCloseDoor: Boolean): FlightState = {
    copy(flightInfo =
      this.flightInfo.map(_.copy(doorClosed = isCloseDoor, flightId = flightId))
    )
  }

  def updatePassenger(flightId: FlightId, passenger: Passenger): FlightState = {
    val passengerSet =
      passengers.filter(p => p.passengerId != passenger.passengerId)
    copy(
      passengers = passengerSet + passenger,
      flightInfo = this.flightInfo.map(_.copy(flightId = flightId))
    )
  }

  def updateFlightInfo(
      flightId: FlightId,
      flightInfo: FlightInfo
  ): FlightState = {
    copy(flightInfo = Some(flightInfo))
  }

  def updateSelectedSeats(
      flightId: FlightId,
      passegnerId: UUID,
      seatAssignment: String
  ): FlightState = {
    val passengers = this.passengers.filter(passager =>
      passager.passengerId.equals(passegnerId)
    )
    //ssagener.copy(seatAssignment = seatAssignment))
    ???
  }

  def removePassenger(flightId: FlightId, passId: UUID): FlightState = {
    val passengerSet =
      passengers.filter(p => p.passengerId != passId)
    copy(
      passengers = passengerSet,
      flightInfo = this.flightInfo.map(_.copy(flightId = flightId))
    )
  }
}
