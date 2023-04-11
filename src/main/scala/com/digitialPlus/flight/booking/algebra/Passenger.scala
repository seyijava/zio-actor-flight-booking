package com.digitialPlus.flight.booking.algebra

import java.util.UUID

case class PassengerId(id: String) extends AnyVal

case class Passenger(
    passengerId: UUID,
    lastName: String,
    firstName: String,
    initial: String,
    seatAssignment: String
)
