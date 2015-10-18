package ch_6.rxos

object TimeModule extends ThreadsApp {
  import rx.lang.scala._
  import Observable._
  import scala.concurrent.duration._
  val systemClock = interval(1.seconds).map(t => s"systime: $t")

}
