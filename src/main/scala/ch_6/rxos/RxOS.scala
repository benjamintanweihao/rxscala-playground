package ch_6.rxos

import rx.lang.scala.subjects.ReplaySubject

object RxOS extends ThreadsApp {
  import rx.lang.scala.subjects.PublishSubject

  val messageBus = PublishSubject[String]()
  val messageLog = ReplaySubject[String]()

  messageBus.subscribe(log _)
  messageBus.subscribe(messageLog)
}
