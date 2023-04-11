package com.digitialPlus.flight.booking

import com.digitialPlus.flight.booking.algebra.{FlightId, FlightInfo, Passenger, PassengerId, Seat}
import com.digitialPlus.flight.booking.command.FlightState
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

package object api {

  case class AddFlightRequest(flightInfo: FlightInfo)

  case class AddFlightResponse(flightId: String)

  case class GetFlightDetailsRequest(flightId: FlightId)

  case class GetFlightDetailResponse(flightState: FlightState)

  case class CloseFlightRequest(flightId: FlightId)

  case class CloseFlightResponse()

  case class RemovePassengerRequest(flightId: FlightId,passengerId: PassengerId)

  case class RemovePassengerResponse(message: String= "Successfully Remove Passenger")

  case class AddSeatRequest(flightId: FlightId, passengerId: PassengerId,seat:Seat)

  case class AddSeatResponse(message: String = "Successfully Add Seat")

  implicit val seatEncoder: JsonEncoder[Seat] = DeriveJsonEncoder.gen[Seat]
  implicit val seatDecoder: JsonDecoder[Seat] = DeriveJsonDecoder.gen[Seat]

  implicit val passengerIdEncoder: JsonEncoder[PassengerId] =
    DeriveJsonEncoder.gen[PassengerId]
  implicit val passengerIdDecoder: JsonDecoder[PassengerId] =
    DeriveJsonDecoder.gen[PassengerId]


  implicit val flightIdEncoder: JsonEncoder[FlightId] =
    DeriveJsonEncoder.gen[FlightId]
  implicit val flightIdDecoder: JsonDecoder[FlightId] =
    DeriveJsonDecoder.gen[FlightId]

  implicit val flightInfoEncoder: JsonEncoder[FlightInfo] =
    DeriveJsonEncoder.gen[FlightInfo]
  implicit val flightInfoDecoder: JsonDecoder[FlightInfo] =
    DeriveJsonDecoder.gen[FlightInfo]

  implicit val addFlightRequestEncoder: JsonEncoder[AddFlightRequest] =
    DeriveJsonEncoder.gen[AddFlightRequest]
  implicit val addFlightRequestDecoder: JsonDecoder[AddFlightRequest] =
    DeriveJsonDecoder.gen[AddFlightRequest]

  implicit val addFlightResponseEncoder: JsonEncoder[AddFlightResponse] =
    DeriveJsonEncoder.gen[AddFlightResponse]
  implicit val addFlightResponseDecoder: JsonDecoder[AddFlightResponse] =
    DeriveJsonDecoder.gen[AddFlightResponse]

  implicit val getFlightDetailsRequestEncoder
      : JsonEncoder[GetFlightDetailsRequest] =
    DeriveJsonEncoder.gen[GetFlightDetailsRequest]
  implicit val getFlightDetailsRequestDecoder
      : JsonDecoder[GetFlightDetailsRequest] =
    DeriveJsonDecoder.gen[GetFlightDetailsRequest]

  implicit val flightStateEncoder: JsonEncoder[FlightState] =
    DeriveJsonEncoder.gen[FlightState]
  implicit val flightStateDecoder: JsonDecoder[FlightState] =
    DeriveJsonDecoder.gen[FlightState]

  implicit val passengerEncoder: JsonEncoder[Passenger] =
    DeriveJsonEncoder.gen[Passenger]
  implicit val passengerDecoder: JsonDecoder[Passenger] =
    DeriveJsonDecoder.gen[Passenger]

  implicit val getFlightDetailsResponseEncoder
      : JsonEncoder[GetFlightDetailResponse] =
    DeriveJsonEncoder.gen[GetFlightDetailResponse]
  implicit val etFlightDetailsResponseDecoder
      : JsonDecoder[GetFlightDetailResponse] =
    DeriveJsonDecoder.gen[GetFlightDetailResponse]

   implicit val closeFlightRequestEncoder: JsonEncoder[CloseFlightRequest] = DeriveJsonEncoder.gen[CloseFlightRequest]
    implicit val closeFlightRequestDecoder: JsonDecoder[CloseFlightRequest] = DeriveJsonDecoder.gen[CloseFlightRequest]

    implicit val closeFlightResponseEncoder: JsonEncoder[CloseFlightResponse] = DeriveJsonEncoder.gen[CloseFlightResponse]
    implicit val closeFlightResponseDecoder: JsonDecoder[CloseFlightResponse] = DeriveJsonDecoder.gen[CloseFlightResponse]


  implicit val removePassengerRequestEncoder: JsonEncoder[RemovePassengerRequest] = DeriveJsonEncoder.gen[RemovePassengerRequest]
  implicit val removePassengerRequestDecoder: JsonDecoder[RemovePassengerRequest] = DeriveJsonDecoder.gen[RemovePassengerRequest]

  implicit val removePassengerResponseEncoder: JsonEncoder[RemovePassengerResponse] = DeriveJsonEncoder.gen[RemovePassengerResponse]
  implicit val removePassengerResponseDecoder: JsonDecoder[RemovePassengerResponse] = DeriveJsonDecoder.gen[RemovePassengerResponse]

  implicit val addSeatRequestEncoder: JsonEncoder[AddSeatRequest] = DeriveJsonEncoder.gen[AddSeatRequest]
  implicit val addSeatRequestDecoder: JsonDecoder[AddSeatRequest] = DeriveJsonDecoder.gen[AddSeatRequest]

  implicit val addSeatResponseEncoder: JsonEncoder[AddSeatResponse] = DeriveJsonEncoder.gen[AddSeatResponse]
  implicit val addSeatResponseDecoder: JsonDecoder[AddSeatResponse] = DeriveJsonDecoder.gen[AddSeatResponse]

}
