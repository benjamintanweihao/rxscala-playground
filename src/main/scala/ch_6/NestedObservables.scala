package ch_6

import rx.lang.scala.{Subscription, Observable}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.parsing.json._
import scala.io.Source

object NestedObservables extends App with ThreadsApp {

  def fetchQuote(): Future[String] = Future {
    val url = "http://api.icndb.com/jokes/random"

    val jsonStr = Source.fromURL(url).getLines().mkString

    JSON.parseFull(jsonStr) match {
      case Some(map: Map[String, Map[String, String]]) => map.get("value").get("joke")
      case None => "meh"
    }

  }

  def fetchQuoteObservable(): Observable[String] = {
    Observable.from(fetchQuote())
  }

  def quotes: Observable[Observable[String]] = {
    Observable.interval(0.01 seconds).take(4).map {
      n => fetchQuoteObservable().map(txt => s"$n: $txt")
    }
  }

//  log(s"now using concat ...")
//  quotes.concat.subscribe(log _)
//  Thread.sleep(6000)
//
//  log(s"now using flatten ...")
//  quotes.flatten.subscribe(log _)
//
//  Thread.sleep(6000)

//  Observable.interval(0.01 seconds).take(5).flatMap({
//    n => fetchQuoteObservable().map(txt => s"$n) $txt")
//  }).subscribe(log _)

//  Thread.sleep(6000)

  var qs = for {
    n <- Observable.interval(0.01 seconds).take(5)
    txt <- fetchQuoteObservable()
  } yield s"$n) $txt"

  qs.subscribe(log _)

  Thread.sleep(6000)

}
