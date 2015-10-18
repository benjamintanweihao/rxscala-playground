package ch_6

import rx.lang.scala._

object ObservablesItems extends App with ThreadsApp {

  val o = Observable.from(Array("Pascal", "Java", "C#"))

  o.subscribe(log _)
  o.subscribe(log _)

}
