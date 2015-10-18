package ch_6.rxos

object SubjectsOS extends App with ThreadsApp {
  import rx.lang.scala._


  log(s"RxOS boot sequence starting ...")

  val modules = List(TimeModule.systemClock, FileSystemModule.fileModifications)


  val loadedModules: List[Subscription] = List(TimeModule.systemClock,
                                               FileSystemModule.fileModifications)
                                          .map(_.subscribe(RxOS.messageBus))

  log(s"RxOS boot sequence finished!")
  Thread.sleep(10000)
  for (mod <- loadedModules) mod.unsubscribe()

  log(s"RxOS dumping the complete system event log")

  RxOS.messageLog.subscribe(log _)
  log(s"RxOS going for shutdown")

}


