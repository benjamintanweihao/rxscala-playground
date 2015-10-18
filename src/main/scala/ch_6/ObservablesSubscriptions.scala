package ch_6

import org.apache.commons.io.monitor.{FileAlterationListenerAdaptor, FileAlterationObserver, FileAlterationMonitor}
import rx.lang.scala.{Subscription, Observable}


object ObservablesSubscriptions extends App with ThreadsApp {

  log(s"starting to monitor files")
  val o = coldModified(".")
  o.subscribe {
    log(_)
  }

  def coldModified(directory: String) : Observable[String] = {
    Observable.create { obs =>
      val fileMonitor = new FileAlterationMonitor(1000)
      val fileObs = new FileAlterationObserver(directory)
      val fileLis = new FileAlterationListenerAdaptor {
        override def onFileChange(file: java.io.File): Unit = {
          obs.onNext(file.getName)
        }
      }

      fileObs.addListener(fileLis)
      fileMonitor.addObserver(fileObs)
      fileMonitor.start

      Subscription { fileMonitor.stop() }
    }
  }

  val fileMonitor = new FileAlterationMonitor(1000)
  fileMonitor.start

  def hotModified(directory: String) : Observable[String] = {
    val fileObs = new FileAlterationObserver(directory)
    fileMonitor.addObserver(fileObs)

    Observable.create { obs =>
      val fileLis = new FileAlterationListenerAdaptor {
        override def onFileChange(file: java.io.File): Unit = {
          obs.onNext(file.getName)
        }
      }

      fileObs.addListener(fileLis)
      Subscription { fileObs.removeListener(fileLis) }
    }
  }

  hotModified(".").subscribe(log _)

}
