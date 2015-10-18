package ch_6

object CompositionScan extends App with ThreadsApp {
  CompositionRetry.quoteMessage.retry.repeat.take(100).scan(0) {
    (n, q) => if (q == "Retrying ...") n + 1 else n
  } subscribe(n => log(s"$n / 100"))
}
