package ch_6

import rx.lang.scala.{Subscription, Observable}

// Implementing custom Observable object
// def create(f: Observer[T] => Subscription): Observable[T]

object ObservablesCreate extends App with ThreadsApp {
  val vms = Observable.create[String] { obs =>
    obs.onNext("BEAM")
    obs.onNext("JVM")
    obs.onNext("DartVM")
    obs.onCompleted()
    Subscription()
  }

  vms.subscribe(log _, e => log(s"oops - $e"), () => log("Done!"))

}
