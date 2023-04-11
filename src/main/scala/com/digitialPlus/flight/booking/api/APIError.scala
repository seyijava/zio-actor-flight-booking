package com.digitialPlus.flight.booking.api

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait APIError
case class InternalServerError(error: String = "Internal Server Error")
    extends APIError
case class NotFound(error: String = "Error Not Found") extends APIError

object APIError {
  implicit val internalServerErrorEncoder: JsonEncoder[InternalServerError] =
    DeriveJsonEncoder.gen[InternalServerError]
  implicit val internalServerErrorDecoder: JsonDecoder[InternalServerError] =
    DeriveJsonDecoder.gen[InternalServerError]

  implicit val notFoundEncoder: JsonEncoder[NotFound] =
    DeriveJsonEncoder.gen[NotFound]
  implicit val notFoundDecoder =
    DeriveJsonDecoder.gen[NotFound]

}
