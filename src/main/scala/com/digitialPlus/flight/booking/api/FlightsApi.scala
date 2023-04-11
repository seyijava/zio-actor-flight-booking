package com.digitialPlus.flight.booking.api

import com.digitialPlus.flight.booking.service.FlightBookingService
import zio.{Task, ULayer, ZIO, ZLayer}
import zio.http.HttpApp
import sttp.model.StatusCode
import sttp.tapir.{EndpointOutput, endpoint, oneOf, oneOfVariant, statusCode}
import sttp.tapir.json.zio.jsonBody
import zio.http.{Http, Request, Response}
import sttp.tapir.generic.auto._
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir._

trait FlightsApi {
  def httpRoutes: ZIO[Any, Nothing, HttpApp[Any, Throwable]]
}

case class FlightsApiLive() extends FlightsApi {
  private val baseEndpoint = endpoint.in("api").in("flight")

  val defaultErrorOutputs: EndpointOutput.OneOf[APIError, APIError] =
    oneOf[APIError](
      oneOfVariant(
        statusCode(StatusCode.InternalServerError)
          .and(jsonBody[InternalServerError])
      ),
      oneOfVariant(statusCode(StatusCode.NotFound).and(jsonBody[NotFound]))
    )

  val addFlightEndpoint = baseEndpoint.post
    .in("addFlight")
    .in(jsonBody[AddFlightRequest])
    .out(jsonBody[AddFlightResponse])
    .errorOut(defaultErrorOutputs)

  val getFlightStatusEndpoint = baseEndpoint.post
    .in("flightStatus")
    .in(jsonBody[GetFlightDetailsRequest])
    .out(jsonBody[GetFlightDetailResponse])
    .errorOut(defaultErrorOutputs)

  val closeFlightEndpoint = baseEndpoint.post
    .in("closeFlight")
    .in(jsonBody[CloseFlightRequest])
    .out(jsonBody[CloseFlightResponse])
    .errorOut(defaultErrorOutputs)

  val removePassengerEndpoint = baseEndpoint.post
    .in("removePassenger")
    .in(jsonBody[RemovePassengerRequest])
    .out(jsonBody[RemovePassengerResponse])
    .errorOut(defaultErrorOutputs)

  val addSeatEndpoint = baseEndpoint.post
    .in("addSeat")
    .in(jsonBody[AddSeatRequest])
    .out(jsonBody[AddSeatResponse])
    .errorOut(defaultErrorOutputs)

  val addFlightRoute = addFlightEndpoint.zServerLogic(request =>
    FlightBookingService
      .addFlight(request.flightInfo)
      .map(flightId => AddFlightResponse(flightId.id))
  )

  val getFlightStateRoute = getFlightStatusEndpoint.zServerLogic(request =>
    FlightBookingService
      .getFlightDetails(request.flightId)
      .map(flightState => GetFlightDetailResponse(flightState))
  )

  val closeFlightRoute = closeFlightEndpoint.zServerLogic(request =>
    FlightBookingService
      .closeFlight(request.flightId)
      .map(_ => CloseFlightResponse())
  )

  val removePassengerRoute = removePassengerEndpoint.zServerLogic(request =>
    FlightBookingService
      .removePassenger(request.flightId, request.passengerId)
      .map(_ => RemovePassengerResponse())
  )

  val addSeatRoute = addSeatEndpoint.zServerLogic(request =>
    FlightBookingService
      .addSelectedSeat(request.flightId, request.passengerId, request.seat)
      .map(_ => AddSeatResponse())
  )

  private val swaggerEndPoints: List[ServerEndpoint[Any, Task]] =
    SwaggerInterpreter()
      .fromEndpoints[Task](
        List(
          addFlightEndpoint,
          getFlightStatusEndpoint,
          closeFlightEndpoint,
          removePassengerEndpoint,
          addSeatEndpoint
        ),
        "Flight Booking API Gateway",
        "1.0"
      )

  private val allRoutes: Http[Any, Throwable, Request, Response] =
    ZioHttpInterpreter().toHttp(
      List(
        addFlightRoute,
        getFlightStateRoute,
        closeFlightRoute,
        removePassengerRoute,
        addSeatRoute
      )
    )

  override def httpRoutes: ZIO[Any, Nothing, HttpApp[Any, Throwable]] = for {
    routeHttp <- ZIO.succeed(allRoutes)
    endpointHttp <- ZIO.succeed(ZioHttpInterpreter().toHttp(swaggerEndPoints))
  } yield routeHttp ++ endpointHttp

}

object FlightsApiLive {
  val live: ULayer[FlightsApiLive] =
    ZLayer.succeed(FlightsApiLive())

  val routes: ZIO[FlightsApi, Nothing, HttpApp[Any, Throwable]] =
    ZIO.serviceWithZIO[FlightsApi](_.httpRoutes)
}
