package ch_6

import rx.lang.scala.subjects.ReplaySubject

object ReplaySubjectRx extends App with ThreadsApp {

  var subject = ReplaySubject[String]()

  subject.onNext("one")
  subject.onNext("two")
  subject.onNext("three")

  subject.subscribe(x => log(x))
  subject.onNext("four")
  subject.subscribe(x => log(x))
  subject.onNext("five")
  subject.subscribe(x => log(x))

  subject.onNext("six")

}
