package org.skyluc.babymetal_site.data

import org.skyluc.fan_resources.data as fr
import org.skyluc.fan_resources.BaseError

case class PageId(id: String) extends fr.ElementId[Page] {
  import Pages._
  override val path = fr.Path(ID_BASE_PATH, id)
}

sealed trait Page extends fr.Element[Page] {
  val id: PageId
}

case class ChronologyPage(
    id: PageId,
    chronology: fr.Chronology,
    hasError: Boolean = false,
) extends Page
    with WithProcessor {
  val linkedTo: Seq[fr.Id[?]] = Nil
  override def errored(): ChronologyPage = copy(hasError = true)
  override def withLinkedTo(id: fr.Id[?]*): ChronologyPage = this

  override def process[T](processor: Processor[T]): T =
    processor.processChronologyPage(this)

  override def process[A](processor: ProcessorWithError[A]): Either[BaseError, A] = {
    processor.processChronologyPage(this)
  }

}

object Pages {
  val ID_BASE_PATH = "page"
}
