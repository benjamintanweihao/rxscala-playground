package ch_6

import rx.lang.scala.{Subscription, Observable}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

object ObservablesCreateFuture extends App with ThreadsApp {

  val f = Future { "Back ot the Futures(s)" }
  val o = Observable.create[String] { obs =>
    f foreach { case s => obs.onNext(s); obs.onCompleted() }
    f.failed foreach { case t => obs.onError(t) }
    Subscription()
  }

  o.subscribe(log _)

  // This uses the main thread
  val of = Observable.from(f)
  of.subscribe(log _)
}
