package ch_6.rxos

object FileSystemModule extends ThreadsApp {
  import rx.lang.scala._
  import org.apache.commons.io.monitor.{FileAlterationListenerAdaptor, FileAlterationObserver, FileAlterationMonitor}

  val fileModifications: Observable[String] = modified(".").map(filename => s"file modification: $filename")

  def modified(directory: String) : Observable[String] = {
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

}
