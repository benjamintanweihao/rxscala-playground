package ch_6

import rx.lang.scala.Observable

object ObservablesExceptions extends App with ThreadsApp {

  val exc = new RuntimeException
  val o = Observable.from(Array(1, 2)) ++ Observable.error(exc) ++ Observable.from(Array(3, 4))
  o.subscribe(
    x => log(s"number $x"),
    t => log(s"an error occurred $t")
  )
}
