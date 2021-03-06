package io.scala.lagom

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{
  KafkaProperties,
  PartitionKeyStrategy
}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

trait ReservationService extends Service {

  def requestReservation(accommodation: String): ServiceCall[Reservation, Done]

  /**
    */
  def reservationNotifications: Topic[ReservationNotification]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("airbnc")
      .withCalls(
        restCall(Method.POST, "/api/accommodation/:accommodation/reservation", requestReservation _)
      ).withTopics(
        topic("airbnc-ReservationNotifications", reservationNotifications)
      ).withAutoAcl(true)
    // @formatter:on
  }
}
