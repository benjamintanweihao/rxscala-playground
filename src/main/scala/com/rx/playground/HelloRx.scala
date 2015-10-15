package com.rx.playground

import rx.lang.scala.Observable
import scala.concurrent.duration._
import scala.language.postfixOps

object HelloRx extends App with ThreadsApp {
  val o = Observable.interval(200 millis).take(5)
  o.subscribe(n => log(n toString))

  Thread.sleep(1000)
}
