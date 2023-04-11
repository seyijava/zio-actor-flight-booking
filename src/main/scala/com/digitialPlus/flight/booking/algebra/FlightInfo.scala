package com.digitialPlus.flight.booking.algebra

import java.util.UUID

case class FlightId(id: String) extends AnyVal

case class FlightInfo(
    flightId: FlightId,
    doorClosed: Boolean = false,
    arrivalIata: String,
    departureIata: String,
    callsign: String,
    equipment: String,
    name: String
) {
  def withDoorsClosed(isDoorClosed: Boolean): FlightInfo = {
    copy(doorClosed = isDoorClosed)
  }
}
