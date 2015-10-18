package ch_6

import rx.lang.scala.{Subscription, Observable}

import scala.io.Source
import scala.util.parsing.json.JSON

object CompositionRetry extends App with ThreadsApp  {
  import Observable._

  def randomQuote: Observable[String] = Observable.create[String] { obs =>
    val url = "http://api.icndb.com/jokes/random"
    val jsonStr = Source.fromURL(url).getLines().mkString

    JSON.parseFull(jsonStr) match {
      case Some(map: Map[String, Map[String, String]]) =>
        val joke: String = map.get("value").get("joke")
        obs.onNext(joke); obs.onCompleted()

      case None => obs.onCompleted()
    }

    Subscription()
  }


  def errorMessage = from(Array("Retrying ...")) ++ error(new Exception)
  def quoteMessage = for {
    text <- randomQuote
    message <- if (text.size < 50) from(Array(text)) else errorMessage
  } yield message

  quoteMessage.retry.subscribe(println(_))
  Thread.sleep(2500)
}
