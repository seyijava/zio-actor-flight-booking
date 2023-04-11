package com.digitialPlus.flight.booking.actor

import com.digitialPlus.flight.booking.command.FlightState
import zio.actors.persistence._
import com.digitialPlus.flight.booking.command.FlightCommands.{
  AddFlight,
  AddPassenger,
  CloseFlight,
  FlightCommand,
  GetFlightDetails,
  RemovePassenger,
  SelectSeat
}
import com.digitialPlus.flight.booking.event.FlightEvents.FlightEvent
import zio.{UIO, ZIO}
import zio.actors.Context
import com.digitialPlus.flight.booking.event.FlightEvents.{
  FlightAddedEvent,
  FlightClosedEvent,
  PassengerAddedEvent,
  PassengerRemovedEvent,
  SeatAddedEvent
}
import org.slf4j.LoggerFactory

object FlightEntity {
  private lazy val logger = LoggerFactory.getLogger(getClass)
  def apply(
      flightId: String
  ): EventSourcedStateful[Any, FlightState, FlightCommand, FlightEvent] =
    new EventSourcedStateful[Any, FlightState, FlightCommand, FlightEvent](
      PersistenceId(flightId)
    ) {

      override def receive[A](
          state: FlightState,
          command: FlightCommand[A],
          context: Context
      ): UIO[(Command[FlightEvent], FlightState => A)] = {

        command match {
          case AddFlight(flightId, flightInfo) =>
            ZIO.succeed(
              (
                Command.persist(
                  FlightAddedEvent(flightId, flightInfo)
                ),
                _ => ()
              )
            )
          case AddPassenger(flightId, passenger) =>
            ZIO.succeed(
              (
                Command.persist(PassengerAddedEvent(flightId, passenger)),
                _ => ()
              )
            )
          case SelectSeat(flightId, passengerId, seatAssignment) =>
            ZIO.succeed(
              (
                Command.persist(
                  SeatAddedEvent(flightId, passengerId, seatAssignment)
                ),
                _ => ()
              )
            )
          case RemovePassenger(flightId, passenger) =>
            ZIO.succeed(
              (
                Command.persist(PassengerRemovedEvent(flightId, passenger)),
                _ => ()
              )
            )
          case CloseFlight(flightId, isClose) =>
            ZIO.succeed(
              (Command.persist(FlightClosedEvent(flightId, isClose)), _ => ())
            )
          case GetFlightDetails() =>
            ZIO.log("Process Get Flight Details Command") *>
              ZIO.succeed((Command.Ignore, _ => state))

        }
      }

      override def sourceEvent(
          state: FlightState,
          event: FlightEvent
      ): FlightState = {
        event match {
          case FlightClosedEvent(flightId, isClose) =>
            state.withDoorsClosed(flightId, isClose)
          case FlightAddedEvent(
                flightId,
                flightInfo
              ) =>
            logger.info("Sourcing Flight AddEvent")
            state.updateFlightInfo(flightId, flightInfo)

          case PassengerAddedEvent(
                flightId,
                passenger
              ) =>
            logger.info("Passenger Added Event")

            state.updatePassenger(
              flightId,
              passenger
            )
          case SeatAddedEvent(
                flightId,
                passengerId,
                seatAssignment
              ) =>
            logger.info("Seat Added Event")
            state.updateSelectedSeats(flightId, passengerId, seatAssignment)
          case PassengerRemovedEvent(flightId, passengerId) =>
            logger.info("Passenger Removed Event")
            state.removePassenger(flightId, passengerId)
        }
      }
    }
}
