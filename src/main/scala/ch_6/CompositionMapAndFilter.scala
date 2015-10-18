package ch_6

import rx.lang.scala.Observable
import scala.concurrent.duration._

object CompositionMapAndFilter extends App with ThreadsApp {

  val odds = Observable.interval(0.5 seconds)
    .filter(_ % 2 == 1)
    .map(n => s"num $n")
    .take(5)

  odds.subscribe(
    log _, e => log(s"unexpected $e"), () => log("no more odds")
  )

  Thread.sleep(4000)

  val evens = for (n <- Observable.from(0 until 9); if n % 2 == 0) yield s"even number $n"
  evens.subscribe(log _)
}
