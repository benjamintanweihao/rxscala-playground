package ch_6

import rx.lang.scala.Observable

import scala.concurrent.duration._

object ObservablesTimer extends App with ThreadsApp {
  val o = Observable.timer(1.second)
  o.subscribe(_ => log("Timeout!"))
  o.subscribe(_ => log("Another Timeout!"))
  o.subscribe(_ => log("Yet Another Timeout!"))
  Thread.sleep(2000)

}
